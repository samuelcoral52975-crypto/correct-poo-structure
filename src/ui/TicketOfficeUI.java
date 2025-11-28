package ui;

import java.io.File;
import java.time.LocalDateTime;

import data.TicketOfficeData;
import domain.Customer;
import domain.Event;
import domain.Site;
import domain.TicketOffice;
import domain.VipCustomer;

/**
 * EN: Console user interface for the TicketOffice system. Displays menu, reads
 * input,
 * and delegates actions to the TicketOffice model.
 *
 * ES: Interfaz de consola para el sistema TicketOffice. Muestra el menú, lee
 * entradas
 * y delega acciones al modelo TicketOffice.
 */
public class TicketOfficeUI {

    // EN: Main model controller
    // ES: Controlador principal del modelo
    private TicketOffice ticketOffice;
    // cree oficce data
    private TicketOfficeData tOfficeData;
    private File path = new File("src/data/newData.dat");

    // EN: Scanner used to read user input from console
    // ES: Scanner usado para leer la entrada del usuario desde consola
    private Console console;

    // EN: Constructor initializes the model and the scanner
    // ES: Constructor que inicializa el modelo y el scanner
    public TicketOfficeUI() {
        this.ticketOffice = new TicketOffice();
        this.console = new Console();
        this.tOfficeData = new TicketOfficeData();

        if (path.exists()) {

            this.ticketOffice = tOfficeData.loadSerialized(path.getPath());

        }

    }

    /**
     * EN: Starts the UI loop. Shows the menu and executes selected options until
     * exit.
     * ES: Inicia el bucle de la UI. Muestra el menú y ejecuta las opciones
     * seleccionadas hasta salir.
     */
    public void start() {
        int option = -1;

        do {
            showMenu();
            option = console.readInt("Choose an option: ");

            switch (option) {
                case 1:
                    registerEvent();
                    break;
                case 2:
                    registerCustomer();
                    break;
                case 3:
                    sellTicket();
                    break;
                case 4:
                    registerSite();
                    break;
                case 5:
                    listEvents();
                    break;
                case 6:
                    listCustomers();
                    break;
                case 7:
                    listTickets();
                    break;
                case 8:
                    searchEventByName();
                    break;
                case 9:
                    searchEventById();
                    break;
                case 10:
                    searchCustomerById();
                    break;
                case 11:
                    generateReport();
                    break;
                case 12:
                    saveData();
                    break;
                case 13:
                    loadData();
                    break;
                case 14:
                    modifyCustomer();
                    break;
                case 15:
                    deleteCustomer();
                    break;
                case 16:
                    modifyEvent();
                    break;
                case 17:
                    deleteEvent();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    tOfficeData.saveSerialized(ticketOffice, path.getPath());
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        } while (option != 0);
    }

    /**
     * EN: Displays the main menu.
     * ES: Muestra el menú principal.
     */
    private void showMenu() {

        System.out.println("------TICKET SYSTEM------");
        System.out.println("1. Register event");
        System.out.println("2. Register customer");
        System.out.println("3. Sell ticket");
        System.out.println("4 Register site");
        System.out.println("5. List events");
        System.out.println("6. List customers");
        System.out.println("7. List sold tickets");
        System.out.println("8. Search event by name");
        System.out.println("9. Search event by ID");// listar eventos antes
        System.out.println("10. Search customer by ID");
        System.out.println("11. Generate report");
        System.out.println("12. Save data");
        System.out.println("13. Load data");// corregir
        System.out.println("14. Modify customer");
        System.out.println("15. Delete customer");
        System.out.println("16. Modify event");
        System.out.println("17. Delete event");
        System.out.println("0. Exit");
        System.out.println("=========================");
    }

    private void registerSite() {
        System.out.println("--- Register Site ---");

        int id = console.readInt("Site ID: ");
        String name = console.readString("Site name: ");
        String location = console.readString("Location: ");
        int capacity = console.readInt("Max capacity: ");

        Site site = new Site(id, name, location, capacity);

        if (ticketOffice.registerSite(site)) {
            System.out.println("Site registered successfully.");
        } else {
            System.out.println("Could not register site.");
        }
    }

    // Event-related methods

    /**
     * EN: Modify an existing event selected by ID.
     * ES: Modifica un evento existente seleccionado por ID.
     */
    private void modifyEvent() {
        System.out.println("--- Modify Event ---");

        int id = console.readInt("Event ID to modify: ");

        Event event = ticketOffice.findEventById(id);

        if (event == null) {
            System.out.println("Event NOT found.");
            return;
        }

        System.out.println("Editing event: " + event.getName());

        String newName = console.readString("New name (ENTER = keep same): ");
        if (!newName.trim().isEmpty()) {
            event.setName(newName);
        }

        String changeDate = console.readString("Change date? (y/n): ");
        if (changeDate.equalsIgnoreCase("y")) {
            LocalDateTime newDate = readValidDateTime();
            event.setStartDateTime(newDate);
            event.setEndDateTime(newDate.plusHours(2));
            event.setEntryTime(newDate.minusMinutes(30));
            event.setExitTime(newDate.plusMinutes(30));
        }

        String newPriceStr = console.readString("New price (ENTER = keep same): ");
        if (!newPriceStr.trim().isEmpty()) {
            double newPrice = Double.parseDouble(newPriceStr);
            event.setTicketPrice(newPrice);
        }

        String newCapStr = console.readString("New capacity (ENTER = keep same): ");
        if (!newCapStr.trim().isEmpty()) {
            int newCap = Integer.parseInt(newCapStr);
            event.setTotalCapacity(newCap);
        }

        String newType = console.readString("New type (SEATED / FREE / OPEN_AIR) (ENTER = keep same): ");
        if (!newType.trim().isEmpty()) {
            event.setEventType(newType);
        }

        System.out.println("Event modified successfully.");
    }

    /**
     * EN: Delete an event by ID.
     * ES: Elimina un evento por ID.
     */
    private void deleteEvent() {
        System.out.println("--- Delete Event ---");

        int id = console.readInt("Event ID to delete: ");
        Event event = ticketOffice.findEventById(id);

        if (event == null) {
            System.out.println("Event not found.");
            return;
        }

        ticketOffice.getEvents().remove(event);
        System.out.println("Event deleted successfully.");
    }

    /**
     * EN: Register a new event reading required fields from the user.
     * ES: Registra un nuevo evento leyendo campos requeridos del usuario.
     */
    private void registerEvent() {
        System.out.println("--- Register Event ---");

        int id = console.readInt("ID: ");
        String name = console.readString("Name: ");

        // EN: Use validated date/time input
        // ES: Usar entrada de fecha/hora validada
        LocalDateTime dateTime = readValidDateTime();

        double price = console.readDouble("Ticket price: ");
        int capacity = console.readInt("Total capacity: ");
        String eventType = console.readString("Event type (SEATED / FREE / OPEN_AIR): ");

        Event event = new Event(id, name, dateTime, price, capacity, eventType);

        if (ticketOffice.registerEvent(event)) {
            System.out.println("Event registered.");
        } else {
            System.out.println("Could not register the event.");
        }
    }

    // Customer-related methods
    /**
     * EN: Modify an existing customer.
     * ES: Modifica un cliente existente.
     */
    private void modifyCustomer() {
        System.out.println("--- Modify Customer ---");

        int id = console.readInt("Customer ID to modify: ");
        Customer customer = ticketOffice.findCustomerById(id);

        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }

        System.out.println("Customer found: " + customer);

        String newName = console.readString("New name (enter to keep same): ");
        String newEmail = console.readString("New email (enter to keep same): ");

        if (!newName.isEmpty())
            customer.setName(newName);
        if (!newEmail.isEmpty())
            customer.setEmail(newEmail);

        System.out.println("Customer modified successfully.");
    }

    /**
     * EN: Delete a customer by ID.
     * ES: Elimina un cliente por ID.
     */
    private void deleteCustomer() {
        System.out.println("\n--- Delete Customer ---");

        int id = console.readInt("Customer ID to delete: ");
        Customer customer = ticketOffice.findCustomerById(id);

        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }

        ticketOffice.getCustomers().remove(customer);
        System.out.println("Customer deleted successfully.");
    }

    /**
     * EN: Register a new customer (normal or VIP).
     * ES: Registra un nuevo cliente (normal o VIP).
     */
    private void registerCustomer() {
        System.out.println("Register Customer");

        int id = console.readInt("ID: ");
        String name = console.readString("Name: ");
        String email = console.readString("Email: ");
        String vipOption = console.readString("Is VIP? (y/n): ");

        Customer customer;

        if (vipOption.equalsIgnoreCase("y")) {
            customer = new VipCustomer(id, name, email);
            System.out.println("VIP customer registered.");
        } else {
            customer = new Customer(id, name, email);
            System.out.println("Regular customer registered.");
        }

        if (ticketOffice.registerCustomer(customer)) {
            System.out.println("Customer registered.");
        } else {
            System.out.println("Could not register the customer.");
        }
    }

    // Ticket-related methods

    /**
     * EN: Sell a ticket for a given event and customer.
     * ES: Vende un ticket para un evento y cliente dados.
     */
    private void sellTicket() {
        System.out.println("Sell Ticket");

        int eventId = console.readInt("Event ID: ");
        int customerId = console.readInt("Customer ID: ");

        Event event = ticketOffice.findEventById(eventId);
        Customer customer = ticketOffice.findCustomerById(customerId);

        if (event == null || customer == null) {
            System.out.println("Event or customer not found.");
            return;
        }

        Object ticket = ticketOffice.sellTicket(event, customer);

        if (ticket != null) {
            System.out.println("Ticket sold.");
        } else {
            System.out.println("Could not sell the ticket.");
        }
    }

    /**
     * EN: List all events printing their toString representation.
     * ES: Lista todos los eventos imprimiendo su representación toString.
     */
    private void listEvents() {
        System.out.println("Events List");
        for (Event e : ticketOffice.getEvents()) {
            System.out.println(e);
        }
    }

    /**
     * EN: List all customers.
     * ES: Lista todos los clientes.
     */
    private void listCustomers() {
        System.out.println("Customers List");
        for (Customer c : ticketOffice.getCustomers()) {
            System.out.println(c);
        }
    }

    /**
     * EN: List all tickets sold.
     * ES: Lista todos los tickets vendidos.
     */
    private void listTickets() {
        System.out.println("Sold Tickets");
        for (Object t : ticketOffice.getTickets()) {
            System.out.println(t);
        }
    }

    /**
     * EN: Search for an event by name and print it.
     * ES: Busca un evento por nombre e imprime el resultado.
     */
    private void searchEventByName() {
        String name = console.readString("Event name: ");
        Event event = ticketOffice.getEventByName(name);

        if (event != null)
            System.out.println(event);
        else
            System.out.println("Event not found.");
    }

    /**
     * EN: Search for an event by ID and print it.
     * ES: Busca un evento por ID e imprime el resultado.
     */
    private void searchEventById() {
        int id = console.readInt("Event ID: ");
        Event event = ticketOffice.findEventById(id);

        if (event != null)
            System.out.println(event);
        else
            System.out.println("Event not found.");
    }

    /**
     * EN: Search for a customer by ID and print it.
     * ES: Busca un cliente por ID e imprime el resultado.
     */
    private void searchCustomerById() {
        int id = console.readInt("Customer ID: ");
        Customer customer = ticketOffice.findCustomerById(id);

        if (customer != null)
            System.out.println(customer);
        else
            System.out.println("Customer not found.");
    }

    /**
     * EN: Reads a validated LocalDateTime from the user.
     * Keeps asking until a parsable date+time is provided.
     * ES: Lee un LocalDateTime validado del usuario.
     * Repite hasta que se proporcione una fecha+hora parseable.
     */
    private LocalDateTime readValidDateTime() {
        LocalDateTime dateTime = null;
        boolean valid = false;

        while (!valid) {
            String dateInput = console.readString("Date (YYYY-MM-DD): ");
            String timeInput = console.readString("Time (HH:MM): ");

            try {
                dateTime = LocalDateTime.parse(dateInput + "T" + timeInput);
                valid = true; // only true if parsing succeeded
            } catch (Exception e) {
                System.out.println("  Invalid date or time. Correct example: 2025-12-24 and 14:30");
            }
        }

        return dateTime;
    }

    /**
     * EN: Generate report by delegating to the model.
     * ES: Genera el reporte delegando al modelo.
     */
    private void generateReport() {
        System.out.println("Report");
        ticketOffice.generateReport();
    }

    /**
     * EN: Placeholder for saving data (not implemented yet).
     * ES: Marcador para guardar datos (no implementado aún).
     */
    private void saveData() {
        System.out.println("(Save data not implemented yet)");
    }

    /**
     * EN: Placeholder for loading data (not implemented yet).
     * ES: Marcador para cargar datos (no implementado aún).
     */
    private void loadData() {
        System.out.println("(Load data not implemented yet)");
    }

}