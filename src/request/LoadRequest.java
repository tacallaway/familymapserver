package request;

import model.Event;
import model.Person;
import model.User;

/**
 * Request for the load operation.
 */
public class LoadRequest {
    /** Users */
    private User[] users;
    /** Persons */
    private Person[] persons;
    /** Events */
    private Event[] events;

    public LoadRequest(User[] users, Person[] persons, Event[] events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    public User[] getUsers() {
        return users;
    }

    public Person[] getPersons() {
        return persons;
    }

    public Event[] getEvents() {
        return events;
    }
}
