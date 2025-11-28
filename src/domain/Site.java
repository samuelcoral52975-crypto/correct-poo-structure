package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * EN: Represents a physical site or venue where events can be hosted.
 * Stores location, capacity, available hours, amenities, and a contact person.
 */
public class Site implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String location;
    private int maxCapacity;
    private ArrayList<String> availableHours;
    private ArrayList<String> amenities;
    private String contactPerson;
    private int id;

    /**
     * EN: Creates a new Site with all required information.
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

    public Site(int id, String name, String location, int capacity) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.maxCapacity = capacity;
        this.availableHours = new ArrayList<>();
        this.amenities = new ArrayList<>();
        this.contactPerson = "";
    }

    /**
     * EN: Checks whether the site has available time slots.
     */
    public boolean isAvailable() {
        return !availableHours.isEmpty();
    }

    /**
     * EN: Books a time slot (removes it from available hours).
     */
    public void bookSlot(String hour) {
        availableHours.remove(hour);
    }

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