package request;

/**
 * Request for the event operation.
 */
public class EventRequest {
    /** Event ID */
    private String eventID;

    public EventRequest(String eventID) {
        this.eventID = eventID;
    }

    public String getEventID() {
        return eventID;
    }
}
