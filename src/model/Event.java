package model;

import java.util.UUID;

/**
 * Represents a life event like birth, baptism, marriage, death, etc.
 */
public class Event {
    /** Event ID */
    private String eventID;
    /** Desendant */
    private String descendant;
    /** Person object */
    private String personID;
    /** Location latitude */
    private double latitude;
    /** Location longitude */
    private double longitude;
    /** Location country */
    private String country;
    /** Location city */
    private String city;
    /** Event type */
    private String eventType;
    /** Event year */
    private int year;

    public Event(String eventID, String descendant, String personID, double latitude, double longitude,
                 String country, String city, String eventType, int year) {
        this.eventID = eventID;
        this.descendant = descendant;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    public Event(String descendant, String personID, double latitude, double longitude,
                 String country, String city, String eventType, int year) {
        this(UUID.randomUUID().toString(), descendant, personID, latitude, longitude, country, city, eventType, year);
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(Person person) {
        this.personID = personID;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
