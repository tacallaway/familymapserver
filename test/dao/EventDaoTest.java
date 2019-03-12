package dao;

import model.AuthToken;
import model.Event;
import model.Person;
import model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

public class EventDaoTest {

    @Before
    public void initialize() throws SQLException {
        DbConnection.setTestMode(true);
        AuthTokenDao.deleteAll();
        EventDao.deleteAll();
        UserDao.deleteAll();
        PersonDao.deleteAll();
    }

    @Test
    public void insertSuccess() throws SQLException {
        User testUser = createTestUser("tyler", "secret", "tyler@gmail.com", "Tyler", "Callaway", "m");

        Event event = new Event(testUser.getUserName(), testUser.getPerson().getPersonID(), 100, 100, "USA", "Chicago", "birth", 2017);

        EventDao.insertEvent(event);

        Event eventFromDatabase = EventDao.getEvent(event.getEventID());

        Assert.assertNotNull(eventFromDatabase);
        Assert.assertEquals("USA", event.getCountry());
        Assert.assertEquals("Chicago", event.getCity());
        Assert.assertEquals("birth", event.getEventType());
        Assert.assertEquals(2017, event.getYear());
    }

    @Test(expected = SQLException.class)
    public void insertNullUsername() throws SQLException {
        Event event = new Event(null, null, 100, 100, "USA", "Chicago", "birth", 2017);

        EventDao.insertEvent(event);
    }

    public static User createTestUser(String userName, String password, String email, String firstName, String lastName, String gender) throws SQLException {
        Person person = new Person(firstName, lastName, gender);
        PersonDao.insertPerson(person);

        User user = new User(userName, password, email, person);
        UserDao.insertUser(user);

        return user;
    }
}
