package result;

import model.Person;

/**
 * Result for the person operation.
 */
public class PersonResult {
    /** Person info */
    private Person person;
    /** Error message */
    private String message;

    public PersonResult(Person person) {
        this.person = person;
    }

    public PersonResult(String message) {
        this.message = message;
    }

    public Person getPerson() {
        return person;
    }

    public String getMessage() {
        return message;
    }
}
