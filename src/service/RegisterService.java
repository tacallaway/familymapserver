package service;

import dao.AuthTokenDao;
import dao.PersonDao;
import dao.UserDao;
import model.AuthToken;
import model.Person;
import model.User;
import request.FillRequest;
import request.RegisterRequest;
import result.RegisterResult;

import java.sql.SQLException;

/**
 * Performs register operations.
 */
public class RegisterService {

    /**
     * Registers a new user.
     *
     * @param req Register new user request
     * @return Register result
     */
    public static RegisterResult register(RegisterRequest req) {

        RegisterResult result;

        try {
            Person person = new Person(req.getFirstName(), req.getLastName(), req.getGender());
            PersonDao.insertPerson(person);

            User user = new User(req.getUserName(), req.getPassword(), req.getEmail(), person);
            UserDao.insertUser(user);

            AuthToken authToken = new AuthToken(user);
            AuthTokenDao.insertAuthToken(authToken);

            FillService.fill(new FillRequest(user.getUserName(), 4));

            result = new RegisterResult(authToken.getToken(), user.getUserName(), person.getPersonID());
        } catch (SQLException e) {
            result = new RegisterResult(e.getMessage());
        }

        return result;
    }
}
