package domain;

import java.util.Date;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.Duration;

public class Event implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private Date date;
    private double ticketPrice;
    private int totalCapacity;
    private int ticketsSold;
    private String eventType;

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;

    private Site site;

    public Event(int id, String name, LocalDateTime dateTime, double price, int capacity, String eventType) {
        this.id = id;
        this.name = name;
        this.ticketPrice = price;
        this.totalCapacity = capacity;
        this.ticketsSold = 0;
        this.eventType = eventType;

        this.startDateTime = dateTime;
        this.endDateTime = dateTime.plusHours(2);
        this.entryTime = dateTime.minusMinutes(30);
        this.exitTime = this.endDateTime.plusMinutes(30);

        this.date = java.sql.Timestamp.valueOf(dateTime);
    }

    public Event(int id, String name, LocalDateTime startDateTime, LocalDateTime endDateTime,
            LocalDateTime entryTime, LocalDateTime exitTime, double price, int capacity, String eventType) {
        this.id = id;
        this.name = name;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.ticketPrice = price;
        this.totalCapacity = capacity;
        this.ticketsSold = 0;
        this.eventType = eventType;

        this.date = java.sql.Timestamp.valueOf(startDateTime);
    }

    public Site getSite() {
        return site;
    }

    public boolean setSite(Site site) {
        if (site == null || !site.isAvailable()) {
            return false;
        }

        if (!site.getAvailableHours().isEmpty()) {
            String hour = site.getAvailableHours().get(0);
            site.bookSlot(hour);
        }

        this.site = site;
        return true;
    }

    public Duration getEventDuration() {
        if (startDateTime != null && endDateTime != null) {
            return Duration.between(startDateTime, endDateTime);
        }
        return Duration.ZERO;
    }

    public String getFormattedDuration() {
        Duration duration = getEventDuration();
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        return String.format("%d horas %d minutos", hours, minutes);
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
        if (startDateTime != null) {
            this.date = java.sql.Timestamp.valueOf(startDateTime);
        }
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(LocalDateTime entryTime) {
        this.entryTime = entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public void setExitTime(LocalDateTime exitTime) {
        this.exitTime = exitTime;
    }

    public boolean isEntryAllowed() {
        return LocalDateTime.now().isAfter(entryTime) || LocalDateTime.now().equals(entryTime);
    }

    public boolean isEventOver() {
        return LocalDateTime.now().isAfter(endDateTime);
    }

    public String getTimeAsString() {
        return startDateTime != null ? startDateTime.toLocalTime().toString() : "";
    }

    public String assignSeat() {
        if (eventType.equalsIgnoreCase("SEATED")) {
            return String.valueOf(ticketsSold + 1);
        }
        if (eventType.equalsIgnoreCase("FREE")) {
            return "FREE-SEAT";
        }
        if (eventType.equalsIgnoreCase("OPEN_AIR")) {
            return "NO-SEAT";
        }
        return "UNDEFINED";
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public int getTotalCapacity() {
        return totalCapacity;
    }

    public int getTicketsSold() {
        return ticketsSold;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public void setTotalCapacity(int totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    public int getAvailableTickets() {
        return totalCapacity - ticketsSold;
    }

    public boolean sellTicket() {
        if (isSoldOut()) {
            return false;
        }
        ticketsSold++;
        return true;
    }

    public boolean isSoldOut() {
        return ticketsSold >= totalCapacity;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public void cancelTicket() {
        if (ticketsSold > 0) {
            ticketsSold--;
        }
    }

    @Override
    public String toString() {
        return "Event ID: " + id + " | " + name +
                " | Date: " + (startDateTime != null ? startDateTime.toLocalDate() : "") +
                " | Start: " + (startDateTime != null ? startDateTime.toLocalTime() : "") +
                " | Duration: " + getFormattedDuration() +
                " | Price: $" + ticketPrice +
                " | Available: " + getAvailableTickets() + "/" + totalCapacity +
                (site != null ? " | Site: " + site.getName() : " | Site: No asignado");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Event event = (Event) obj;
        return id == event.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}