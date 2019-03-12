package service;

import dao.AuthTokenDao;
import dao.UserDao;
import model.AuthToken;
import model.User;
import request.LoginRequest;
import result.LoginResult;

import java.sql.SQLException;

/**
 * Performs login operations.
 */
public class LoginService {

    /**
     * Login a user and return an auth token.
     *
     * @param req Request to login a user
     * @return Login result
     */
    public static LoginResult login(LoginRequest req) {
        LoginResult result;

        try {
            if (req.getUserName() == null || req.getPassword() == null) {
                throw new FamilyMapException("Request property missing or has invalid value");
            }

            User user = UserDao.getUser(req.getUserName());

            if (user.getPassword().equals(req.getPassword())) {
                // AuthTokenDao.deleteAuthTokenByUser(req.getUserName());
                AuthToken authToken = new AuthToken(user.getUserName());
                AuthTokenDao.insertAuthToken(authToken);
                result = new LoginResult(authToken.getToken(), user.getUserName(), user.getPerson().getPersonID());
            } else {
                result = new LoginResult("Request property missing or has invalid value");
            }
        } catch (FamilyMapException fme) {
            result = new LoginResult(fme.getMessage());
        }  catch (Exception e) {
            result = new LoginResult("Internal server error");
        }

        return result;
    }
}
