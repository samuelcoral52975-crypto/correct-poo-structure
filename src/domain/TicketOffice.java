package domain;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class TicketOffice implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Event> events;
    private List<Customer> customers;
    private List<Ticket> tickets;
    private List<Site> sites;

    public TicketOffice() {
        this.events = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.tickets = new ArrayList<>();
        this.sites = new ArrayList<>();
    }

    public List<Event> getEvents() {
        return events;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public List<Site> getSites() {
        return sites;
    }

    public boolean registerEvent(Event event) {
        if (event == null) {
            return false;
        }

        for (Event existingEvent : events) {
            if (existingEvent.equals(event)) {
                return false;
            }
        }

        events.add(event);
        return true;
    }

    public boolean registerCustomer(Customer customer) {
        if (customer == null) {
            return false;
        }

        for (Customer existingCustomer : customers) {
            if (existingCustomer.equals(customer)) {
                return false;
            }
        }

        customers.add(customer);
        return true;
    }

    public boolean registerSite(Site site) {
        if (site == null) {
            return false;
        }

        for (Site existingSite : sites) {
            if (existingSite.equals(site)) {
                return false;
            }
        }

        sites.add(site);
        return true;
    }

    public Ticket sellTicket(Event event, Customer customer) {
        if (event == null || customer == null) {
            return null;
        }

        if (!events.contains(event)) {
            return null;
        }

        if (!customers.contains(customer)) {
            return null;
        }

        if (event.isSoldOut()) {
            return null;
        }

        String seat = event.assignSeat();
        event.sellTicket();

        Ticket ticket = new Ticket(
                generateTicketId(),
                event,
                customer,
                event.getTicketPrice(),
                seat);

        tickets.add(ticket);
        return ticket;
    }

    private int generateTicketId() {
        return tickets.size() + 1;
    }

    public Event getEventByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }

        for (Event event : events) {
            if (event.getName().equalsIgnoreCase(name.trim())) {
                return event;
            }
        }
        return null;
    }

    public Site getSiteByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }

        for (Site site : sites) {
            if (site.getName().equalsIgnoreCase(name.trim())) {
                return site;
            }
        }
        return null;
    }

    public boolean removeTicket(String seatNumber) {
        for (Ticket t : tickets) {
            if (t.getSeatNumber().equalsIgnoreCase(seatNumber)) {
                t.getEvent().cancelTicket();
                tickets.remove(t);
                return true;
            }
        }
        return false;
    }

    public void generateReport() {
        System.out.println("=== TICKET OFFICE REPORT ===");

        System.out.println("--- SITES (" + sites.size() + ") ---");
        for (Site site : sites) {
            System.out.println(site);
        }

        System.out.println("--- EVENTS (" + events.size() + ") ---");
        for (Event event : events) {
            System.out.println(event);
            System.out.println("  Available tickets: " + event.getAvailableTickets());
            System.out.println("  Sold out: " + event.isSoldOut());
        }

        System.out.println("--- CUSTOMERS (" + customers.size() + ") ---");
        for (Customer customer : customers) {
            System.out.println(customer);
        }

        System.out.println("--- TICKETS SOLD (" + tickets.size() + ") ---");
        for (Ticket ticket : tickets) {
            System.out.println(ticket);
        }

        double totalRevenue = 0;
        for (Ticket ticket : tickets) {
            totalRevenue += ticket.getFinalPrice();
        }

        System.out.println("--- STATISTICS ---");
        System.out.println("Total revenue: $" + totalRevenue);
        System.out.println("Total sites: " + sites.size());
        System.out.println("Total events: " + events.size());
        System.out.println("Total customers: " + customers.size());
        System.out.println("Total tickets sold: " + tickets.size());
    }

    public Customer findCustomerById(int id) {
        for (Customer customer : customers) {
            if (customer.getId() == id) {
                return customer;
            }
        }
        return null;
    }

    public Event findEventById(int id) {
        for (Event event : events) {
            if (event.getId() == id) {
                return event;
            }
        }
        return null;
    }

    public Site findSiteById(int id) {
        for (Site site : sites) {
            if (site.getId() == id) {
                return site;
            }
        }
        return null;
    }

    public boolean deleteCustomer(int id) {
        Customer customer = findCustomerById(id);
        if (customer == null) {
            return false;
        }

        for (Ticket t : tickets) {
            if (t.getCustomer().getId() == id) {
                System.out.println("No se puede eliminar: el cliente tiene tickets vendidos.");
                return false;
            }
        }
        return customers.remove(customer);
    }

    public boolean deleteEvent(int id) {
        Event toRemove = findEventById(id);
        if (toRemove != null) {
            return events.remove(toRemove);
        }
        return false;
    }

    public boolean deleteSite(int id) {
        Site toRemove = findSiteById(id);
        if (toRemove != null) {
            return sites.remove(toRemove);
        }
        return false;
    }

    public boolean updateEvent(int id, String newName, int newCapacity) {
        Event event = findEventById(id);
        if (event == null)
            return false;

        event.setName(newName);
        event.setTotalCapacity(newCapacity);
        return true;
    }

    public boolean updateCustomer(int id, String newName, String newEmail) {
        Customer customer = findCustomerById(id);
        if (customer == null) {
            return false;
        }

        if (newName != null && !newName.trim().isEmpty()) {
            customer.setName(newName);
        }

        if (newEmail != null && !newEmail.trim().isEmpty()) {
            customer.setEmail(newEmail);
        }
        return true;
    }

    public boolean assignSiteToEvent(int eventId, int siteId) {
        Event event = findEventById(eventId);
        Site site = findSiteById(siteId);

        if (event == null || site == null) {
            return false;
        }

        return event.setSite(site);
    }
}