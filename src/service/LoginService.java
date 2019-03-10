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
            User user = UserDao.getUser(req.getUserName());

            if (user.getPassword().equals(req.getPassword())) {
                // AuthTokenDao.deleteAuthTokenByUser(req.getUserName());
                AuthToken authToken = new AuthToken(user);
                AuthTokenDao.insertAuthToken(authToken);
                result = new LoginResult(authToken.getToken(), user.getUserName(), user.getPerson().getPersonID());
            } else {
                result = new LoginResult("Invalid credentials.");
            }
        } catch (SQLException e) {
            result = new LoginResult(e.getMessage());
        }

        return result;
    }
}
