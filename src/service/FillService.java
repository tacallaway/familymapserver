package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.PersonDao;
import dao.UserDao;
import model.Person;
import model.User;
import request.FillRequest;
import result.MessageResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Performs fill operations.
 */
public class FillService {

    private static Random rand = new Random();
    private static List<String> firstNames;
    private static List<String> lastNames;
    private static List<Map<String, String>> locations;

    private static class ObjectCount {
        public int personCount;
        public int eventCount;
    };

    static {
        ObjectMapper mapper = new ObjectMapper();

        try {
            byte[] encoded = Files.readAllBytes(Paths.get("json/fnames.json"));
            Map<String, List<String>> json = mapper.readValue(new String(encoded, "utf-8"), Map.class);
            firstNames = json.get("data");

            encoded = Files.readAllBytes(Paths.get("json/snames.json"));
            json = mapper.readValue(new String(encoded, "utf-8"), Map.class);
            lastNames = json.get("data");

            encoded = Files.readAllBytes(Paths.get("json/locations.json"));
            Map<String, List<Map<String, String>>> locationJson = mapper.readValue(new String(encoded, "utf-8"), Map.class);
            locations = locationJson.get("data");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Populate database with family information for a given user.
     *
     * @param req Fill request
     * @return Fill result
     */
    public static MessageResult fill(FillRequest req) {
        MessageResult result;

        String username = req.getUserName();

        try {
            int numGenerations = req.getNumGenerations();

            if (numGenerations < 0 || numGenerations > 20) {
                throw new FamilyMapException("Invalid username or generations parameter");
            }

            User user = UserDao.getUser(username);

            if (user == null) {
                result = new MessageResult("Invalid username or generations parameter");
            } else {
                Person person = user.getPerson();

                ObjectCount objectCount = new ObjectCount();

                populate(person, user, numGenerations, Calendar.getInstance().get(Calendar.YEAR)-28, objectCount);

                result = new MessageResult("Successfully added " + objectCount.personCount + " persons and " + objectCount.eventCount + " events to the database.");
            }
        } catch (FamilyMapException fme) {
            result = new MessageResult(fme.getMessage());
        } catch (NumberFormatException nfe) {
            result = new MessageResult("Invalid username or generations parameter");
        } catch (Exception e) {
            result = new MessageResult("Internal server error");
        }

        return result;
    }

    private static void populate(Person person, User descendant, int count, int birthYear, ObjectCount objectCount) throws SQLException {
        Map<String, String> location = locations.get(rand.nextInt(locations.size()));
        EventService.addEvent(person, Double.parseDouble(String.valueOf(location.get("latitude"))), Double.parseDouble(String.valueOf(location.get("longitude"))), location.get("country"), location.get("city"), "birth", birthYear);
        location = locations.get(rand.nextInt(locations.size()));
        EventService.addEvent(person, Double.parseDouble(String.valueOf(location.get("latitude"))), Double.parseDouble(String.valueOf(location.get("longitude"))), location.get("country"), location.get("city"), "baptism", birthYear + 8);
        location = locations.get(rand.nextInt(locations.size()));
        EventService.addEvent(person, Double.parseDouble(String.valueOf(location.get("latitude"))), Double.parseDouble(String.valueOf(location.get("longitude"))), location.get("country"), location.get("city"), "marriage", randomizeYear(birthYear, 23));
        objectCount.eventCount += 3;

        if (person.getSpouse() == null) {
            Person spouse = createPerson(descendant, person.getGender().equals("m") ? "f" : "m");
            insertPerson(spouse, person);
            objectCount.personCount++;
            person.setSpouse(spouse.getPersonID());
        }

        if (count > 0) {
            count--;

            Person father = createPerson(descendant, "m");
            Person mother = createPerson(descendant, "f");

            insertPerson(father, mother);
            insertPerson(mother, father);
            objectCount.personCount += 2;

            person.setFather(father.getPersonID());
            person.setMother(mother.getPersonID());

            PersonDao.updatePerson(person);

            populate(father, descendant,  count, randomizeYear(birthYear, -23), objectCount);
            populate(mother, descendant, count, randomizeYear(birthYear, -23), objectCount);
        }
    }

    private static int randomizeYear(int year, int range) {

        return year - rand.nextInt(5) + range;
    }

    private static Person createPerson(User descendant, String gender) throws SQLException {
        String firstName = firstNames.get(rand.nextInt(firstNames.size()));
        String lastName = lastNames.get(rand.nextInt(lastNames.size()));

        Person person = new Person(firstName, lastName, gender);
        person.setDescendant(descendant.getUserName());

        return person;
    }

    private static Person insertPerson(Person person, Person spouse) throws SQLException {
        person.setSpouse(spouse.getPersonID());
        PersonDao.insertPerson(person);

        return person;
    }
}
