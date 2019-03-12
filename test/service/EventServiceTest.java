package service;

import dao.*;
import model.Event;
import model.Person;
import model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import request.EventRequest;
import request.FillRequest;
import result.EventAllResult;
import result.EventResult;

import java.sql.SQLException;

public class EventServiceTest {

    @Before
    public void initialize() throws SQLException {
        DbConnection.setTestMode(true);
        AuthTokenDao.deleteAll();
        EventDao.deleteAll();
        UserDao.deleteAll();
        PersonDao.deleteAll();
    }

    @Test
    public void addEventSuccess() throws SQLException {
        User testUser = createTestUser("tyler", "secret", "tyler@gmail.com", "Tyler", "Callaway", "m");

        Event newEvent = EventService.addEvent(testUser.getPerson(), 100, 100, "USA", "Chicago", "birth", 1980);

        EventResult eventResult = EventService.getEvent(new EventRequest(newEvent.getEventID()));

        Assert.assertEquals(newEvent.getPersonID(), eventResult.getEvent().getPersonID());
        Assert.assertEquals(newEvent.getLatitude(), eventResult.getEvent().getLatitude(), .1);
        Assert.assertEquals(newEvent.getLongitude(), eventResult.getEvent().getLongitude(), .1);
        Assert.assertEquals(newEvent.getCountry(), eventResult.getEvent().getCountry());
        Assert.assertEquals(newEvent.getCity(), eventResult.getEvent().getCity());
        Assert.assertEquals(newEvent.getEventType(), eventResult.getEvent().getEventType());
        Assert.assertEquals(newEvent.getYear(), eventResult.getEvent().getYear());
    }

    @Test
    public void addEventNullPersonID() {
        Event event = EventService.addEvent(new Person(null, "Tyler", "Callaway", "m"), 100, 100, "USA", "Chicago", "birth", 1980);

        Assert.assertNull(event);
    }

    @Test
    public void getEventSuccess() throws SQLException {
        User testUser = createTestUser("tyler", "secret", "tyler@gmail.com", "Tyler", "Callaway", "m");

        Event newEvent = EventService.addEvent(testUser.getPerson(), 100, 100, "USA", "Chicago", "birth", 1980);

        EventResult eventResult = EventService.getEvent(new EventRequest(newEvent.getEventID()));

        Assert.assertEquals(newEvent.getPersonID(), eventResult.getEvent().getPersonID());
        Assert.assertEquals(newEvent.getLatitude(), eventResult.getEvent().getLatitude(), .1);
        Assert.assertEquals(newEvent.getLongitude(), eventResult.getEvent().getLongitude(), .1);
        Assert.assertEquals(newEvent.getCountry(), eventResult.getEvent().getCountry());
        Assert.assertEquals(newEvent.getCity(), eventResult.getEvent().getCity());
        Assert.assertEquals(newEvent.getEventType(), eventResult.getEvent().getEventType());
        Assert.assertEquals(newEvent.getYear(), eventResult.getEvent().getYear());
    }

    @Test
    public void getEventFailure() throws SQLException {
        User testUser = createTestUser("tyler", "secret", "tyler@gmail.com", "Tyler", "Callaway", "m");

        EventService.addEvent(testUser.getPerson(), 100, 100, "USA", "Chicago", "birth", 1980);

        EventResult eventResult = EventService.getEvent(new EventRequest("1234"));

        Assert.assertNull(eventResult.getEvent());
    }

    @Test
    public void getEventsSuccess() throws SQLException {
        User testUser = createTestUser("tyler", "secret", "tyler@gmail.com", "Tyler", "Callaway", "m");

        FillService.fill(new FillRequest(testUser.getUserName(), 1));

        EventAllResult eventResult = EventService.getEvents(testUser.getUserName());

        Assert.assertTrue(eventResult.getData().size() == 6);
    }

    @Test
    public void getEventsFailure() throws SQLException {
        User testUser = createTestUser("tyler", "secret", "tyler@gmail.com", "Tyler", "Callaway", "m");

        FillService.fill(new FillRequest(testUser.getUserName(), 1));

        EventAllResult eventResult = EventService.getEvents("dummy");

        Assert.assertTrue(eventResult.getData().size() == 0);
    }

    public static User createTestUser(String userName, String password, String email, String firstName, String lastName, String gender) throws SQLException {
        Person person = new Person(firstName, lastName, gender);
        PersonDao.insertPerson(person);

        User user = new User(userName, password, email, person);
        UserDao.insertUser(user);

        return user;
    }
}
