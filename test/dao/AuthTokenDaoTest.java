package dao;

import model.AuthToken;
import model.Person;
import model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

public class AuthTokenDaoTest {

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

        AuthToken authToken = new AuthToken(testUser.getUserName());

        AuthTokenDao.insertAuthToken(authToken);

        AuthToken tokenFromDatabase = AuthTokenDao.getAuthToken(authToken.getToken());

        Assert.assertNotNull(tokenFromDatabase);
        Assert.assertEquals("tyler", authToken.getUserName());
    }

    @Test(expected = SQLException.class)
    public void insertNullUsername() throws SQLException {
        AuthToken authToken = new AuthToken(null);

        AuthTokenDao.insertAuthToken(authToken);
    }

    public static User createTestUser(String userName, String password, String email, String firstName, String lastName, String gender) throws SQLException {
        Person person = new Person(firstName, lastName, gender);
        PersonDao.insertPerson(person);

        User user = new User(userName, password, email, person);
        UserDao.insertUser(user);

        return user;
    }
}
