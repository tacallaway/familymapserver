package result;

/**
 * Result for the "all persons" operation.
 */
public class PersonAllResult {
    /** List of persons */
    private PersonResult[] data;
    /** Error message */
    private String message;

    public PersonAllResult(PersonResult[] data) {
        this.data = data;
    }

    public PersonAllResult(String message) {
        this.message = message;
    }

    public PersonResult[] getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
