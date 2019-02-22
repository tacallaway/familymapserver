import dao.PersonDao;
import dao.UserDao;
import model.Person;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

public class PersonDaoTest {

    @Before
    public void initialize() throws SQLException {
        UserDao.deleteAll();
        PersonDao.deleteAll();
    }

    @Test
    public void insertSuccess() throws SQLException {
        Person person = createTestUser("Tyler", "Callaway", "m");

        Person personFromDatabase = PersonDao.getPerson(person.getPersonID());
        Assert.assertNotNull(personFromDatabase);
        Assert.assertEquals("Tyler", personFromDatabase.getFirstName());
        Assert.assertEquals("Callaway", personFromDatabase.getLastName());
        Assert.assertEquals("m", personFromDatabase.getGender());
    }

    @Test(expected = SQLException.class)
    public void insertNullName() throws SQLException {
        Person person = createTestUser(null, null, "m");

        PersonDao.insertPerson(person);
    }

    @Test(expected = SQLException.class)
    public void insertNullGender() throws SQLException {
        Person person = createTestUser("Tyler", "Callaway", null);

        PersonDao.insertPerson(person);
    }

    @Test
    public void getSuccess() throws SQLException {
        Person person = createTestUser("Tyler", "Callaway", "m");

        Person personFromDatabase = PersonDao.getPerson(person.getPersonID());

        Assert.assertEquals(person.getPersonID(), personFromDatabase.getPersonID());
        Assert.assertEquals(person.getFirstName(), personFromDatabase.getFirstName());
        Assert.assertEquals(person.getLastName(), personFromDatabase.getLastName());
        Assert.assertEquals(person.getGender(), personFromDatabase.getGender());
    }

    @Test
    public void getInvalidID() throws SQLException {
        Person person = createTestUser("Tyler", "Callaway", "m");

        Person personFromDatabase = PersonDao.getPerson(person.getPersonID() + "1");

        Assert.assertNull(personFromDatabase);
    }

    @Test
    public void deleteSuccess() throws SQLException {
        Person person = createTestUser("Tyler", "Callaway", "m");

        Person personFromDatabase = PersonDao.getPerson(person.getPersonID());

        Assert.assertNotNull(personFromDatabase);
        Assert.assertEquals(person.getPersonID(), personFromDatabase.getPersonID());

        PersonDao.deletePerson(person.getPersonID());

        personFromDatabase = PersonDao.getPerson(person.getPersonID());

        Assert.assertNull(personFromDatabase);
    }

    @Test
    public void deleteInvalidID() throws SQLException {
        Person person = createTestUser("Tyler", "Callaway", "m");

        PersonDao.deletePerson(null);

        Assert.assertNotNull(PersonDao.getPerson(person.getPersonID()));
    }

    private Person createTestUser(String firstName, String lastName, String gender) throws SQLException {
        Person person = new Person("Tyler", "Callaway", "m");
        PersonDao.insertPerson(person);

        return person;
    }
}
