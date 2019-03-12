package service;

import dao.AuthTokenDao;
import dao.EventDao;
import dao.PersonDao;
import dao.UserDao;
import model.Person;
import model.User;
import result.MessageResult;

import java.sql.SQLException;

/**
 * Performs clear operations.
 */
public class ClearService {

    /**
     * Clears the database;
     *
     * @return Clear result
     */
    public static MessageResult clear() {
        MessageResult result = new MessageResult("Clear succeeded.");

        try {
            AuthTokenDao.deleteAll();
            EventDao.deleteAll();
            UserDao.deleteAll();
            PersonDao.deleteAll();
        } catch (Exception e) {
            result.setMessage("Internal server error");
        }

        return result;
    }

    public static void clearUserData(String username) {
        try {
            User user = UserDao.getUser(username);

            if (user != null) {
                Person person = user.getPerson();
                person.setSpouse(null);
                person.setFather(null);
                person.setMother(null);
                PersonDao.updatePerson(person);

                EventDao.deleteEvents(username);
                PersonDao.deletePersons(username);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
