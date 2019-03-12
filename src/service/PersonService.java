package service;

import dao.PersonDao;
import model.Person;
import request.PersonRequest;
import result.PersonAllResult;
import result.PersonResult;

import java.sql.SQLException;
import java.util.List;

/**
 * Performs person operations.
 */
public class PersonService {

    /**
     * Get person.
     *
     * @param req Person request
     * @return Person result
     */
    public static PersonResult getPerson(PersonRequest req) {
        PersonResult result;

        try {
            Person person = PersonDao.getPerson(req.getPersonID());

            if (person == null) {
                result = new PersonResult("Request property missing or has invalid value");
            } else {
                result = new PersonResult(person);
            }
        } catch (SQLException e) {
            result = new PersonResult(e.getMessage());
        }

        return result;
    }

    /**
     * Get all persons for user.
     *
     * @return Person result
     */
    public static PersonAllResult getPersons(String username) {
        PersonAllResult result;

        try {
            List<Person> persons = PersonDao.getPersons(username);
            result = new PersonAllResult(persons);
        } catch (SQLException e) {
            result = new PersonAllResult(e.getMessage());
        }

        return result;
    }
}
