package service;

import dao.*;
import model.Person;
import model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import request.FillRequest;
import result.PersonAllResult;

import java.sql.SQLException;

public class ClearServiceTest {

    @Before
    public void initialize() throws SQLException {
        DbConnection.setTestMode(true);
        AuthTokenDao.deleteAll();
        EventDao.deleteAll();
        UserDao.deleteAll();
        PersonDao.deleteAll();
    }

    @Test
    public void clearSuccess() throws SQLException {
        User testUser = createTestUser("tyler", "secret", "tyler@gmail.com", "Tyler", "Callaway", "m");

        Assert.assertNotNull(UserDao.getUser(testUser.getUserName()));
        Assert.assertNotNull(PersonDao.getPerson(testUser.getPerson().getPersonID()));

        ClearService.clear();

        Assert.assertNull(UserDao.getUser(testUser.getUserName()));
        Assert.assertNull(PersonDao.getPerson(testUser.getPerson().getPersonID()));
    }

    @Test
    public void clearUserSuccess() throws SQLException {
        User testUser = createTestUser("tyler", "secret", "tyler@gmail.com", "Tyler", "Callaway", "m");

        Assert.assertNotNull(UserDao.getUser(testUser.getUserName()));
        Assert.assertNotNull(PersonDao.getPerson(testUser.getPerson().getPersonID()));

        FillService.fill(new FillRequest(testUser.getUserName(), 1));

        PersonAllResult result = PersonService.getPersons(testUser.getUserName());

        Assert.assertTrue(result.getData().size() == 3);

        ClearService.clearUserData(testUser.getUserName());

        result = PersonService.getPersons(testUser.getUserName());

        Assert.assertTrue(result.getData().size() == 0);
    }

    @Test
    public void clearUserFailure() throws SQLException {
        User testUser = createTestUser("tyler", "secret", "tyler@gmail.com", "Tyler", "Callaway", "m");

        Assert.assertNotNull(UserDao.getUser(testUser.getUserName()));
        Assert.assertNotNull(PersonDao.getPerson(testUser.getPerson().getPersonID()));

        FillService.fill(new FillRequest(testUser.getUserName(), 1));

        PersonAllResult result = PersonService.getPersons(testUser.getUserName());

        Assert.assertTrue(result.getData().size() == 3);

        ClearService.clearUserData("dummy");

        result = PersonService.getPersons(testUser.getUserName());

        Assert.assertTrue(result.getData().size() == 3);
    }

    public static User createTestUser(String userName, String password, String email, String firstName, String lastName, String gender) throws SQLException {
        Person person = new Person(firstName, lastName, gender);
        PersonDao.insertPerson(person);

        User user = new User(userName, password, email, person);
        UserDao.insertUser(user);

        return user;
    }
}
