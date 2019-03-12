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
            if (req.getFirstName() == null || req.getLastName() == null || req.getGender() == null || req.getUserName() == null || req.getPassword() == null || req.getEmail() == null || (!req.getGender().equals("m") && !req.getGender().equals("f"))) {
                throw new FamilyMapException("Request property missing or has invalid value");
            }

            Person person = new Person(req.getFirstName(), req.getLastName(), req.getGender());
            PersonDao.insertPerson(person);

            User user = new User(req.getUserName(), req.getPassword(), req.getEmail(), person);
            UserDao.insertUser(user);

            AuthToken authToken = new AuthToken(user.getUserName());
            AuthTokenDao.insertAuthToken(authToken);

            FillService.fill(new FillRequest(user.getUserName(), 4));

            result = new RegisterResult(authToken.getToken(), user.getUserName(), person.getPersonID());
        } catch (FamilyMapException e) {
            result = new RegisterResult(e.getMessage());
        } catch (Exception e) {
            String message = "Internal server error";

            if (e.getMessage().indexOf("UNIQUE constraint failed: User.UserName") > -1) {
                message = "Username already taken by another user";
            }

            result = new RegisterResult(message);
        }

        return result;
    }
}
