package request;

import model.Event;
import model.Person;
import model.User;

import java.util.List;
import java.util.Map;

/**
 * Request for the load operation.
 */
public class LoadRequest {
    /** Users */
    private List<Map<String, String>> users;
    /** Persons */
    private List<Map<String, String>> persons;
    /** Events */
    private List<Map<String, String>> events;

    public LoadRequest(List<Map<String, String>> users, List<Map<String, String>> persons, List<Map<String, String>> events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    public List<Map<String, String>> getUsers() {
        return users;
    }

    public List<Map<String, String>> getPersons() {
        return persons;
    }

    public List<Map<String, String>> getEvents() {
        return events;
    }
}
