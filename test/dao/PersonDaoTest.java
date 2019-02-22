package dao;

import model.Person;
import model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

public class PersonDaoTest {

    @Before
    public void initialize() throws SQLException {
        DbConnection.setTestMode(true);
        UserDao.deleteAll();
        PersonDao.deleteAll();
    }

    @Test
    public void insertSuccess() throws SQLException {
        Person person = createTestPerson("Tyler", "Callaway", "m");

        Person personFromDatabase = PersonDao.getPerson(person.getPersonID());

        Assert.assertNotNull(personFromDatabase);
        Assert.assertEquals(person.getPersonID(), personFromDatabase.getPersonID());
        Assert.assertEquals("Tyler", personFromDatabase.getFirstName());
        Assert.assertEquals("Callaway", personFromDatabase.getLastName());
        Assert.assertEquals("m", personFromDatabase.getGender());
    }

    @Test(expected = SQLException.class)
    public void insertNullName() throws SQLException {
        Person person = createTestPerson(null, null, "m");

        PersonDao.insertPerson(person);
    }

    @Test(expected = SQLException.class)
    public void insertNullGender() throws SQLException {
        Person person = createTestPerson("Tyler", "Callaway", null);

        PersonDao.insertPerson(person);
    }

    @Test
    public void getSuccess() throws SQLException {
        Person person = createTestPerson("Tyler", "Callaway", "m");

        Person personFromDatabase = PersonDao.getPerson(person.getPersonID());

        Assert.assertNotNull(personFromDatabase);
        Assert.assertEquals(person.getPersonID(), personFromDatabase.getPersonID());
        Assert.assertEquals(person.getFirstName(), personFromDatabase.getFirstName());
        Assert.assertEquals(person.getLastName(), personFromDatabase.getLastName());
        Assert.assertEquals(person.getGender(), personFromDatabase.getGender());
    }

    @Test
    public void getInvalidID() throws SQLException {
        createTestPerson("Tyler", "Callaway", "m");

        Person personFromDatabase = PersonDao.getPerson("12345");

        Assert.assertNull(personFromDatabase);
    }

    @Test
    public void deleteSuccess() throws SQLException {
        Person person = createTestPerson("Tyler", "Callaway", "m");

        Person personFromDatabase = PersonDao.getPerson(person.getPersonID());

        Assert.assertNotNull(personFromDatabase);
        Assert.assertEquals(person.getPersonID(), personFromDatabase.getPersonID());

        PersonDao.deletePerson(person.getPersonID());

        personFromDatabase = PersonDao.getPerson(person.getPersonID());

        Assert.assertNull(personFromDatabase);
    }

    @Test
    public void deleteInvalidID() throws SQLException {
        Person person = createTestPerson("Tyler", "Callaway", "m");

        PersonDao.deletePerson("12345");

        Assert.assertNotNull(PersonDao.getPerson(person.getPersonID()));
        Assert.assertNull(PersonDao.getPerson("12345"));
    }

    @Test(expected = SQLException.class)
    public void deletePersonForeignKeyViolation() throws SQLException {
        // creating new user creates a new person
        User user = UserDaoTest.createTestUser("tyler123", "secret", "tacallaway@gmail.com", "Tyler", "Callaway", "m");

        // try to delete the person record while user record references it
        PersonDao.deletePerson(user.getPerson().getPersonID());
    }

    private Person createTestPerson(String firstName, String lastName, String gender) throws SQLException {
        Person person = new Person(firstName, lastName, gender);
        PersonDao.insertPerson(person);

        return person;
    }
}
