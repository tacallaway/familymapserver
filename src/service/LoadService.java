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

        } catch (SQLException e) {
            result = new MessageResult(e.getMessage());
        }

        return result;
    }

    private static void addUsers(LoadRequest req) throws SQLException {

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
                Person objectPerson = new Person(userPerson.get("personID"), userPerson.get("firstName"), userPerson.get("lastName"), userPerson.get("gender"));
                objectPerson.setFather(userPerson.get("father"));
                objectPerson.setMother(userPerson.get("mother"));
                objectPerson.setSpouse(userPerson.get("spouse"));
                objectPerson.setDescendant(userPerson.get("descendant"));

                PersonDao.insertPerson(objectPerson);
                req.getPersons().remove(userPerson); // remove person associated with this user since we're adding the user's person object here

                User objectUser = new User(user.get("userName"), user.get("password"), user.get("email"), objectPerson);
                UserDao.insertUser(objectUser);
            }
        }
    }

    private static void addPersons(LoadRequest req) throws SQLException {
        for (Map<String, String> person : req.getPersons()) {
            Person objectPerson = new Person(person.get("personID"), person.get("firstName"), person.get("lastName"), person.get("gender"));
            objectPerson.setFather(person.get("father"));
            objectPerson.setMother(person.get("mother"));
            objectPerson.setSpouse(person.get("spouse"));
            objectPerson.setDescendant(person.get("descendant"));

            PersonDao.insertPerson(objectPerson);
        }
    }

    private static void addEvents(LoadRequest req) throws SQLException {
        for (Map event : req.getEvents()) {
            Event objectEvent = new Event((String)event.get("descendant"), (String)event.get("personID"), (double)event.get("latitude"), (double)event.get("longitude"),
                    (String)event.get("country"), (String)event.get("city"), (String)event.get("eventType"), (int)event.get("year"));

            EventDao.insertEvent(objectEvent);
        }
    }
}
