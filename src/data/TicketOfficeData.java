package data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import domain.Customer;
import domain.Event;
import domain.TicketOffice;
import domain.VipCustomer;

public class TicketOfficeData {

    public TicketOfficeData(){
        
    }
    // serializacion
    public void saveSerialized(TicketOffice office, String path) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path))) {

            out.writeObject(office); // Guarda toda la oficina con eventos y clientes
            System.out.println("TicketOffice serializado correctamente.");

        } catch (IOException e) {
            System.out.println("Error al serializar TicketOffice: " + e.getMessage());
        }
    }

    public  TicketOffice loadSerialized(String path) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(path))) {

            return (TicketOffice) in.readObject(); // Carga todo el sistema 

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar archivo serializado: " + e.getMessage());
            return null;
        }
    }
    // CSV: Exportar los eventos

    public static void exportEventsToCSV(ArrayList<Event> events, String path) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(path))) {

            writer.println("ID,Nombre,Fecha,Capacidad Disponible");

            for (Event e : events) {
                writer.println(
                        e.getId() + "," +
                                e.getName() + "," +
                                e.getDate() + "," +
                                e.getAvailableTickets());
            }

            System.out.println("Eventos exportados a CSV correctamente.");

        } catch (IOException e) {
            System.out.println("Error al exportar eventos a CSV: " + e.getMessage());
        }
    }

    // CSV: EXPORTAR los clientes
    public static void exportCustomersToCSV(List<Customer> customers, String path) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(path))) {

            writer.println("ID,Nombre,Email,Tipo");

            for (Customer c : customers) {
                String type = (c instanceof VipCustomer) ? "VIP" : "Regular";

                writer.println(
                        c.getId() + "," +
                                c.getName() + "," +
                                c.getEmail() + "," +
                                type);
            }

            System.out.println("Clientes exportados a CSV correctamente.");

        } catch (IOException e) {
            System.out.println("Error al exportar clientes a CSV: " + e.getMessage());
        }
    }
}
