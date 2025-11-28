package domain;

import java.util.Objects;
import java.io.Serializable;

public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private String email;

    public Customer(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Customer ID: " + id + " | Name: " + name + " | Email: " + email;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) obj;
        return this.id == other.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public double computePrice(Event event) {
        return event.getTicketPrice();
    }

    public void buyTicket(Event event) {
        double price = computePrice(event);

        System.out.println(name + " quiere comprar ticket para: " + event.getName());
        System.out.println("Precio calculado: " + price);
        System.out.println("Cupos disponibles: " + event.getAvailableTickets());
    }
}