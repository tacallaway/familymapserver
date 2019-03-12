package service;

import dao.*;
import model.Person;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import request.LoadRequest;
import result.MessageResult;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoadServiceTest {

    @Before
    public void initialize() throws SQLException {
        DbConnection.setTestMode(true);
        AuthTokenDao.deleteAll();
        EventDao.deleteAll();
        UserDao.deleteAll();
        PersonDao.deleteAll();
    }

    @Test
    public void loadSuccess() throws SQLException {
        List<Map<String, String>> persons = new ArrayList<>();

        Map<String, String> person = new HashMap<>();
        person.put("firstName", "Sheila");
        person.put("lastName", "Parker");
        person.put("gender", "f");
        person.put("personID", "Sheila_Parker");
        person.put("father", "Patrick_Spencer");
        person.put("mother", "Im_really_good_at_names");
        person.put("descendant", "sheila");

        persons.add(person);

        LoadRequest request = new LoadRequest(new ArrayList<Map<String, String>>(), persons, new ArrayList<Map<String, String>>());

        LoadService.load(request);

        Person dbPerson = PersonDao.getPerson("Sheila_Parker");

        Assert.assertEquals(person.get("firstName"), dbPerson.getFirstName());
        Assert.assertEquals(person.get("lastName"), dbPerson.getLastName());
        Assert.assertEquals(person.get("gender"), dbPerson.getGender());
        Assert.assertEquals(person.get("personID"), dbPerson.getPersonID());
        Assert.assertEquals(person.get("father"), dbPerson.getFather());
        Assert.assertEquals(person.get("mother"), dbPerson.getMother());
        Assert.assertEquals(person.get("descendant"), dbPerson.getDescendant());
    }

    @Test
    public void fillNullFirstName() throws SQLException {
        List<Map<String, String>> persons = new ArrayList<>();

        Map<String, String> person = new HashMap<>();
        person.put("firstName", null);
        person.put("lastName", "Parker");
        person.put("gender", "f");
        person.put("personID", "Sheila_Parker");
        person.put("father", "Patrick_Spencer");
        person.put("mother", "Im_really_good_at_names");
        person.put("descendant", "sheila");

        persons.add(person);

        LoadRequest request = new LoadRequest(new ArrayList<Map<String, String>>(), persons, new ArrayList<Map<String, String>>());

        MessageResult result = LoadService.load(request);

        Assert.assertEquals("Invalid request data", result.getMessage());
    }
}
