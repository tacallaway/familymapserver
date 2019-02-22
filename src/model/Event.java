package model;

/**
 * Represents a life event like birth, baptism, marriage, death, etc.
 */
public class Event {
    /** Event ID */
    private String eventID;
    /** Desendant */
    private User descendant;
    /** Person object */
    private Person person;
    /** Location latitude */
    private float latitude;
    /** Location longitude */
    private float longitude;
    /** Location country */
    private String country;
    /** Location city */
    private String city;
    /** Event type */
    private String eventType;
    /** Event year */
    private int year;

    public Event(String eventID, User descendant, Person person, float latitude, float longitude,
                 String country, String city, String eventType, int year) {
        this.eventID = eventID;
        this.descendant = descendant;
        this.person = person;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public User getDescendant() {
        return descendant;
    }

    public void setDescendant(User descendant) {
        this.descendant = descendant;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
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
