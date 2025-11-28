package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * EN: Represents a physical site or venue where events can be hosted.
 * Stores location, capacity, available hours, amenities, and a contact person.
 *
 * ES: Representa un sitio o lugar físico donde se pueden realizar eventos.
 * Almacena ubicación, capacidad, horarios disponibles, servicios y un contacto.
 */
public class Site implements Serializable {

    // EN: Ensures compatibility during serialization.
    // ES: Garantiza compatibilidad durante la serialización.
    private static final long serialVersionUID = 1L;
    // ATTRIBUTES / ATRIBUTOS
    private String name; // EN: Site name / ES: Nombre del sitio
    private String location; // EN: Site location / ES: Ubicación
    private int maxCapacity; // EN: Maximum capacity / ES: Capacidad máxima
    private ArrayList<String> availableHours; // EN: Available hours / ES: Horarios disponibles
    private ArrayList<String> amenities; // EN: Amenities / ES: Servicios o comodidades
    private String contactPerson; // EN: Contact person / ES: Persona de contacto
    private int id;
    // CONSTRUCTOR

    /**
     * EN: Creates a new Site with all required information.
     * ES: Crea un nuevo Site con toda la información requerida.
     */
    public Site(String name, String location, int maxCapacity, int id,
            ArrayList<String> availableHours, ArrayList<String> amenities,
            String contactPerson) {

        this.name = name;
        this.location = location;
        this.maxCapacity = maxCapacity;
        this.availableHours = availableHours;
        this.amenities = amenities;
        this.contactPerson = contactPerson;
    }

    // secon CONSTRUCTOR
    public Site(int id, String name, String location, int capacity) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.maxCapacity = capacity;
        this.availableHours = new ArrayList<>();
        this.amenities = new ArrayList<>();
        this.contactPerson = "";
    }

    // BUSINESS LOGIC
    // LÓGICA DEL NEGOCIO

    /**
     * EN: Checks whether the site has available time slots.
     * ES: Verifica si el sitio tiene horarios disponibles.
     */
    public boolean isAvailable() {
        return !availableHours.isEmpty();
    }

    /**
     * EN: Books a time slot (removes it from available hours).
     * ES: Reserva un horario (lo elimina de los disponibles).
     */
    public void bookSlot(String hour) {
        availableHours.remove(hour);
    }

    // GETTERS

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public int getId() {
        return id;
    }

    /**
     * EN: Returns available hours (list can still be modified externally).
     * ES: Devuelve los horarios disponibles (la lista puede modificarse
     * externamente).
     */
    public List<String> getAvailableHours() {
        return availableHours;
    }

    public ArrayList<String> getAmenities() {
        return amenities;
    }

    public String getContactPerson() {
        return contactPerson;
    }
}