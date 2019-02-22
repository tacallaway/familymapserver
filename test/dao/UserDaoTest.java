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

//    @Test(expected = SQLException.class)
//    public void insertNullName() throws SQLException {
//        Person person = createTestUser(null, null, "m");
//
//        PersonDao.insertPerson(person);
//    }
//
//    @Test(expected = SQLException.class)
//    public void insertNullGender() throws SQLException {
//        Person person = createTestUser("Tyler", "Callaway", null);
//
//        PersonDao.insertPerson(person);
//    }
//
//    @Test
//    public void getSuccess() throws SQLException {
//        Person person = createTestUser("Tyler", "Callaway", "m");
//
//        Person personFromDatabase = PersonDao.getPerson(person.getPersonID());
//
//        Assert.assertNotNull(personFromDatabase);
//        Assert.assertEquals(person.getPersonID(), personFromDatabase.getPersonID());
//        Assert.assertEquals(person.getFirstName(), personFromDatabase.getFirstName());
//        Assert.assertEquals(person.getLastName(), personFromDatabase.getLastName());
//        Assert.assertEquals(person.getGender(), personFromDatabase.getGender());
//    }
//
//    @Test
//    public void getInvalidID() throws SQLException {
//        createTestUser("Tyler", "Callaway", "m");
//
//        Person personFromDatabase = PersonDao.getPerson("12345");
//
//        Assert.assertNull(personFromDatabase);
//    }
//
//    @Test
//    public void deleteSuccess() throws SQLException {
//        Person person = createTestUser("Tyler", "Callaway", "m");
//
//        Person personFromDatabase = PersonDao.getPerson(person.getPersonID());
//
//        Assert.assertNotNull(personFromDatabase);
//        Assert.assertEquals(person.getPersonID(), personFromDatabase.getPersonID());
//
//        PersonDao.deletePerson(person.getPersonID());
//
//        personFromDatabase = PersonDao.getPerson(person.getPersonID());
//
//        Assert.assertNull(personFromDatabase);
//    }
//
//    @Test
//    public void deleteInvalidID() throws SQLException {
//        Person person = createTestUser("Tyler", "Callaway", "m");
//
//        PersonDao.deletePerson("12345");
//
//        Assert.assertNotNull(PersonDao.getPerson(person.getPersonID()));
//    }

    private User createTestUser(String userName, String password, String email, String firstName, String lastName, String gender) throws SQLException {
        Person person = new Person(firstName, lastName, gender);
        PersonDao.insertPerson(person);

        User user = new User(userName, password, email, person);
        UserDao.insertUser(user);

        return user;
    }

}
