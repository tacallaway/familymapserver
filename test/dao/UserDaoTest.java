package dao;

import model.Person;
import model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

public class UserDaoTest {

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
        User user = createTestUser("tyler123", "secret", "tacallaway@gmail.com", "Tyler", "Callaway", "m");

        User userFromDatabase = UserDao.getUser(user.getUserName());

        Assert.assertNotNull(userFromDatabase);
        Assert.assertEquals(user.getUserName(), userFromDatabase.getUserName());
        Assert.assertEquals(user.getPassword(), userFromDatabase.getPassword());
        Assert.assertEquals(user.getEmail(), userFromDatabase.getEmail());
        Assert.assertEquals("Tyler", userFromDatabase.getFirstName());
        Assert.assertEquals("Callaway", userFromDatabase.getLastName());
        Assert.assertEquals("m", userFromDatabase.getGender());
    }

    @Test(expected = SQLException.class)
    public void insertNullUserName() throws SQLException {
        User user = createTestUser(null, "secret", "tacallaway@gmail.com", "Tyler", "Callaway", "m");

        UserDao.insertUser(user);
    }

    @Test(expected = SQLException.class)
    public void insertNullPasswordAndEmail() throws SQLException {
        User user = createTestUser("tyler123", null, null, "Tyler", "Callaway", "m");

        UserDao.insertUser(user);
    }

    @Test
    public void getSuccess() throws SQLException {
        User user = createTestUser("tyler123", "secret", "tacallaway@gmail.com", "Tyler", "Callaway", "m");

        User userFromDatabase = UserDao.getUser(user.getUserName());

        Assert.assertNotNull(userFromDatabase);
        Assert.assertEquals(user.getUserName(), userFromDatabase.getUserName());
        Assert.assertEquals(user.getPassword(), userFromDatabase.getPassword());
        Assert.assertEquals(user.getEmail(), userFromDatabase.getEmail());
        Assert.assertEquals("Tyler", userFromDatabase.getFirstName());
        Assert.assertEquals("Callaway", userFromDatabase.getLastName());
        Assert.assertEquals("m", userFromDatabase.getGender());
    }

    @Test
    public void getInvalidID() throws SQLException {
        User user = createTestUser("tyler123", "secret", "tacallaway@gmail.com", "Tyler", "Callaway", "m");

        User userFromDatabase = UserDao.getUser("12345");

        Assert.assertNull(userFromDatabase);
    }

    @Test
    public void deleteSuccess() throws SQLException {
        User user = createTestUser("tyler123", "secret", "tacallaway@gmail.com", "Tyler", "Callaway", "m");

        User userFromDatabase = UserDao.getUser(user.getUserName());

        Assert.assertNotNull(userFromDatabase);
        Assert.assertEquals(user.getUserName(), userFromDatabase.getUserName());

        UserDao.deleteUser(user.getUserName());

        userFromDatabase = UserDao.getUser(user.getUserName());

        Assert.assertNull(userFromDatabase);
    }

    @Test
    public void deleteInvalidID() throws SQLException {
        User user = createTestUser("tyler123", "secret", "tacallaway@gmail.com", "Tyler", "Callaway", "m");

        UserDao.deleteUser("12345");

        Assert.assertNotNull(UserDao.getUser(user.getUserName()));
        Assert.assertNull(UserDao.getUser("12345"));
    }

    public static User createTestUser(String userName, String password, String email, String firstName, String lastName, String gender) throws SQLException {
        Person person = new Person(firstName, lastName, gender);
        PersonDao.insertPerson(person);

        User user = new User(userName, password, email, person);
        UserDao.insertUser(user);

        return user;
    }
}
