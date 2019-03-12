package service;

import dao.EventDao;
import model.Event;
import model.Person;
import request.EventRequest;
import result.EventAllResult;
import result.EventResult;

import java.sql.SQLException;
import java.util.List;

/**
 * Performs event operations.
 */
public class EventService {

    /**
     * Get a single event.
     *
     * @param req Event request
     * @return Event
     */
    public static EventResult getEvent(EventRequest req) {
        EventResult result;

        try {
            Event event = EventDao.getEvent(req.getEventID());
            result = new EventResult(event);
        } catch (SQLException e) {
            result = new EventResult(e.getMessage());
        }

        return result;
    }

    /**
     * Gets all events for user.
     *
     * @return All events
     */
    public static EventAllResult getEvents(String username) {

        EventAllResult result;

        try {
            List<Event> events = EventDao.getEvents(username);
            result = new EventAllResult(events);
        } catch (SQLException e) {
            result = new EventAllResult(e.getMessage());
        }

        return result;
    }

    public static Event addEvent(Person person, double latitude, double longitude, String country, String city, String eventType, int year) {
        Event event;

        try {
            event = new Event(person.getDescendant(), person.getPersonID(), latitude, longitude, country, city, eventType, year);

            EventDao.insertEvent(event);
        } catch (Exception e) {
            event = null;
        }

        return event;
    }
}
