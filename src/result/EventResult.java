package result;

/**
 * Result for the event operation.
 */
public class EventResult {
    /** Event ID */
    private String eventID;
    /** Descendant name */
    private String descendant;
    /** Person ID */
    private String personID;
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

    public EventResult(String eventID, String descendant, String personID, float latitude,
                       float longitude, String country, String city, String eventType, int year) {
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

    public String getEventID() {
        return eventID;
    }

    public String getDescendant() {
        return descendant;
    }

    public String getPersonID() {
        return personID;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getEventType() {
        return eventType;
    }

    public int getYear() {
        return year;
    }
}
