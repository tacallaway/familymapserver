package result;

import model.Person;

import java.util.List;

/**
 * Result for the "all persons" operation.
 */
public class PersonAllResult {
    /** List of persons */
    private List<Person> data;
    /** Error message */
    private String message;

    public PersonAllResult(List<Person> data) {
        this.data = data;
    }

    public PersonAllResult(String message) {
        this.message = message;
    }

    public List<Person> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
