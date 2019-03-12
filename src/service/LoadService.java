package service;

import dao.EventDao;
import dao.PersonDao;
import dao.UserDao;
import model.Event;
import model.Person;
import model.User;
import request.LoadRequest;
import result.MessageResult;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Performs load operations.
 */
public class LoadService {

    /**
     * Clears the database and then loads the given family information.
     *
     * @param req Load request
     * @return Load result
     */
    public static MessageResult load(LoadRequest req) {
        MessageResult result = null;

        try {
            ClearService.clear();

            String message = "Successfully added " + req.getUsers().size() + " users, " + req.getPersons().size() + " persons, and " + req.getEvents().size() + " events to the database.";

            addUsers(req);

            addPersons(req);

            addEvents(req);

            result = new MessageResult(message);

        } catch (FamilyMapException fme) {
          result = new MessageResult("Invalid request data");
        } catch (Exception e) {
            result = new MessageResult("Internal server error");
        }

        return result;
    }

    private static void addUsers(LoadRequest req) throws SQLException, FamilyMapException {

        for (Map<String, String> user : req.getUsers()) {

            String username = user.get("userName");
            Map<String, String> userPerson = null;
            for (Map<String, String> person : req.getPersons()) {
                if (username.equals(person.get("descendant"))) {
                    userPerson = person;
                    break;
                }
            }

            if (userPerson != null) {
                if (user.get("userName") == null || user.get("password") == null || user.get("email") == null || user.get("personID") == null || userPerson.get("personID") == null || userPerson.get("firstName") == null || userPerson.get("lastName") == null || userPerson.get("gender") == null || (!userPerson.get("gender").equals("m") && !userPerson.get("gender").equals("f"))) {
                    throw new FamilyMapException();
                }

                Person objectPerson = new Person(userPerson.get("personID"), userPerson.get("firstName"), userPerson.get("lastName"), userPerson.get("gender"));
                objectPerson.setFather(userPerson.get("father"));
                objectPerson.setMother(userPerson.get("mother"));
                objectPerson.setSpouse(userPerson.get("spouse"));
                objectPerson.setDescendant(userPerson.get("descendant"));

                PersonDao.insertPerson(objectPerson);
                req.getPersons().remove(userPerson); // remove person associated with this user since we're adding the user's person object here

                User objectUser = new User(user.get("userName"), user.get("password"), user.get("email"), objectPerson);
                UserDao.insertUser(objectUser);
            } else {
                throw new FamilyMapException();
            }
        }
    }

    private static void addPersons(LoadRequest req) throws SQLException, FamilyMapException {
        for (Map<String, String> person : req.getPersons()) {
            if (person.get("personID") == null || person.get("firstName") == null || person.get("lastName") == null || person.get("gender") == null || (!person.get("gender").equals("m") && !person.get("gender").equals("f"))) {
                throw new FamilyMapException();
            }

            Person objectPerson = new Person(person.get("personID"), person.get("firstName"), person.get("lastName"), person.get("gender"));
            objectPerson.setFather(person.get("father"));
            objectPerson.setMother(person.get("mother"));
            objectPerson.setSpouse(person.get("spouse"));
            objectPerson.setDescendant(person.get("descendant"));

            PersonDao.insertPerson(objectPerson);
        }
    }

    private static void addEvents(LoadRequest req) throws SQLException, FamilyMapException {
        for (Map event : req.getEvents()) {
            if (event.get("descendant") == null || event.get("personID") == null || event.get("latitude") == null || event.get("longitude") == null || event.get("country") == null || event.get("city") == null || event.get("eventType") == null || event.get("year") == null) {
                throw new FamilyMapException();
            }

            String descendant = (String)event.get("descendant");
            String personID = (String)event.get("personID");
            double latitude = (double)event.get("latitude");
            double longitude = (double)event.get("longitude");
            String country = (String)event.get("country");
            String city = (String)event.get("city");
            String eventType = (String)event.get("eventType");
            int year = (int)event.get("year");

            if (latitude > 180 || latitude < -180 || longitude > 180 || longitude < -180 || year > Calendar.getInstance().get(Calendar.YEAR)) {
                throw new FamilyMapException();
            }

            Event objectEvent = new Event(descendant, personID, latitude, longitude, country, city, eventType, year);

            EventDao.insertEvent(objectEvent);
        }
    }
}
