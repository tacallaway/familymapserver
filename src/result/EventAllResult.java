package result;

/**
 * Result for the "all events" operation.
 */
public class EventAllResult {
    /** List of results */
    private EventResult[] data;
    /** Error message */
    private String message;

    public EventAllResult(EventResult[] data) {
        this.data = data;
    }

    public EventAllResult(String message) {
        this.message = message;
    }

    public EventResult[] getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
