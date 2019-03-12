package service;

import dao.*;
import model.Person;
import model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import request.FillRequest;
import result.EventAllResult;
import result.MessageResult;

import java.sql.SQLException;

public class FillServiceTest {

    @Before
    public void initialize() throws SQLException {
        DbConnection.setTestMode(true);
        AuthTokenDao.deleteAll();
        EventDao.deleteAll();
        UserDao.deleteAll();
        PersonDao.deleteAll();
    }

    @Test
    public void fillSuccess() throws SQLException {
        User testUser = createTestUser("tyler", "secret", "tyler@gmail.com", "Tyler", "Callaway", "m");

        FillService.fill(new FillRequest(testUser.getUserName(), 1));

        EventAllResult eventResult = EventService.getEvents(testUser.getUserName());

        Assert.assertTrue(eventResult.getData().size() == 6);
    }

    @Test
    public void fillFailure() throws SQLException {
        MessageResult result = FillService.fill(new FillRequest(null, 1));

        Assert.assertEquals("Invalid username or generations parameter", result.getMessage());
    }

    public static User createTestUser(String userName, String password, String email, String firstName, String lastName, String gender) throws SQLException {
        Person person = new Person(firstName, lastName, gender);
        PersonDao.insertPerson(person);

        User user = new User(userName, password, email, person);
        UserDao.insertUser(user);

        return user;
    }
}
