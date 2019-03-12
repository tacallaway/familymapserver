package service;

import dao.*;
import model.Person;
import model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import request.FillRequest;
import request.PersonRequest;
import result.PersonAllResult;
import result.PersonResult;

import java.sql.SQLException;

public class PersonServiceTest {

    @Before
    public void initialize() throws SQLException {
        DbConnection.setTestMode(true);
        AuthTokenDao.deleteAll();
        EventDao.deleteAll();
        UserDao.deleteAll();
        PersonDao.deleteAll();
    }

    @Test
    public void getPersonSuccess() throws SQLException {
        User testUser = createTestUser("tyler", "secret", "tyler@gmail.com", "Tyler", "Callaway", "m");

        PersonResult result = PersonService.getPerson(new PersonRequest(testUser.getPerson().getPersonID()));

        Person dbPerson = PersonDao.getPerson(testUser.getPerson().getPersonID());

        Assert.assertEquals(testUser.getPerson().getPersonID(), dbPerson.getPersonID());
        Assert.assertEquals(testUser.getPerson().getFirstName(), dbPerson.getFirstName());
        Assert.assertEquals(testUser.getPerson().getLastName(), dbPerson.getLastName());
    }

    @Test
    public void getPersonFailure() throws SQLException {
        User testUser = createTestUser("tyler", "secret", "tyler@gmail.com", "Tyler", "Callaway", "m");

        PersonResult result = PersonService.getPerson(new PersonRequest("dummy"));

        Assert.assertEquals("Request property missing or has invalid value", result.getMessage());
        Assert.assertNull(result.getPerson());
    }

    @Test
    public void getPersonsSuccess() throws SQLException {
        User testUser = createTestUser("tyler", "secret", "tyler@gmail.com", "Tyler", "Callaway", "m");

        FillService.fill(new FillRequest(testUser.getUserName(), 1));

        PersonAllResult result = PersonService.getPersons(testUser.getUserName());

        Assert.assertEquals(result.getData().size(), 3);
    }

    @Test
    public void getPersonsFailure() throws SQLException {
        User testUser = createTestUser("tyler", "secret", "tyler@gmail.com", "Tyler", "Callaway", "m");

        FillService.fill(new FillRequest(testUser.getUserName(), 1));

        PersonAllResult result = PersonService.getPersons("dummy");

        Assert.assertEquals(result.getData().size(), 0);
    }

    public static User createTestUser(String userName, String password, String email, String firstName, String lastName, String gender) throws SQLException {
        Person person = new Person(firstName, lastName, gender);
        PersonDao.insertPerson(person);

        User user = new User(userName, password, email, person);
        UserDao.insertUser(user);

        return user;
    }
}
