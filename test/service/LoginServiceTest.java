package service;

import dao.*;
import model.AuthToken;
import model.Person;
import model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import request.LoginRequest;
import result.LoginResult;

import java.sql.SQLException;

public class LoginServiceTest {

    @Before
    public void initialize() throws SQLException {
        DbConnection.setTestMode(true);
        AuthTokenDao.deleteAll();
        EventDao.deleteAll();
        UserDao.deleteAll();
        PersonDao.deleteAll();
    }

    @Test
    public void loginSuccess() throws SQLException {
        User testUser = createTestUser("tyler", "secret", "tyler@gmail.com", "Tyler", "Callaway", "m");

        LoginResult result = LoginService.login(new LoginRequest(testUser.getUserName(), testUser.getPassword()));

        AuthToken dbToken = AuthTokenDao.getAuthToken(result.getAuthToken());

        Assert.assertNotNull(dbToken);
        Assert.assertEquals(result.getAuthToken(), dbToken.getToken());
    }

    @Test
    public void loginFailure() throws SQLException {
        User testUser = createTestUser("tyler", "secret", "tyler@gmail.com", "Tyler", "Callaway", "m");

        LoginResult result = LoginService.login(new LoginRequest(testUser.getUserName(), "bad_password"));

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
