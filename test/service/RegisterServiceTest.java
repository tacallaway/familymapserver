package service;

import dao.*;
import model.AuthToken;
import model.Person;
import model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import request.RegisterRequest;
import result.RegisterResult;

import java.sql.SQLException;

public class RegisterServiceTest {

    @Before
    public void initialize() throws SQLException {
        DbConnection.setTestMode(true);
        AuthTokenDao.deleteAll();
        EventDao.deleteAll();
        UserDao.deleteAll();
        PersonDao.deleteAll();
    }

    @Test
    public void registerSuccess() throws SQLException {
        RegisterResult result = RegisterService.register(new RegisterRequest("tyler", "secret", "tyler@gmail.com", "Tyler", "Callaway", "m"));

        AuthToken dbToken = AuthTokenDao.getAuthToken(result.getAuthToken());

        Assert.assertNotNull(result.getAuthToken());
        Assert.assertNotNull(result.getPersonID());
        Assert.assertNotNull(result.getUserName());
        Assert.assertEquals(result.getUserName(), dbToken.getUserName());
        Assert.assertEquals(result.getAuthToken(), dbToken.getToken());
    }

    @Test
    public void registerNullUsername() throws SQLException {
        RegisterResult result = RegisterService.register(new RegisterRequest(null, "secret", "tyler@gmail.com", "Tyler", "Callaway", "m"));

        Assert.assertEquals("Request property missing or has invalid value", result.getMessage());
    }

    public static User createTestUser(String userName, String password, String email, String firstName, String lastName, String gender) throws SQLException {
        Person person = new Person(firstName, lastName, gender);
        PersonDao.insertPerson(person);

        User user = new User(userName, password, email, person);
        UserDao.insertUser(user);

        return user;
    }
}
