package result;

import model.Event;

import java.util.List;

/**
 * Result for the "all events" operation.
 */
public class EventAllResult {
    /** List of results */
    private List<Event> data;
    /** Error message */
    private String message;

    public EventAllResult(List<Event> data) {
        this.data = data;
    }

    public EventAllResult(String message) {
        this.message = message;
    }

    public List<Event> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
