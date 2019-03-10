package result;

import model.Event;

/**
 * Result for the event operation.
 */
public class EventResult {
    /** Event ID */
    private Event event;
    /** Descendant name */
    private String message;

    public EventResult(Event event) {
        this.event = event;
    }

    public EventResult(String message) {
        this.message = message;
    }

    public Event getEvent() {
        return event;
    }

    public String getMessage() {
        return message;
    }
}
