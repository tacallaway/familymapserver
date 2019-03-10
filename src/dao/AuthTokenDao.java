package dao;

import model.AuthToken;
import model.User;

import java.sql.*;

/**
 * Auth token data access object for creating, getting, and deleting auth tokens from database.
 */
public class AuthTokenDao {

    /**
     * Adds a new auth token to the database.
     *
     * @param authToken Auth token to add
     */
    public static void insertAuthToken(AuthToken authToken) throws SQLException {
        Connection conn = DbConnection.getConnection();

        String sql = "INSERT INTO AuthToken(Token, Username) VALUES (?, ?)";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, authToken.getToken());
        pstmt.setString(2, authToken.getUser().getUserName());

        pstmt.executeUpdate();
    }

    /**
     * Gets an auth token from the database.
     *
     * @param token Token of auth token to get
     * @return Auth token from database
     */
    public static AuthToken getAuthToken(String token) throws SQLException {
        Connection conn = DbConnection.getConnection();
        AuthToken authToken = null;

        String sql = "SELECT * FROM AuthToken WHERE Token = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, token);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            User user = UserDao.getUser(rs.getString("Username"));
            authToken = new AuthToken(token, user);
        }

        return authToken;
    }

    /**
     * Gets an auth token from the database.
     *
     * @param username Token of auth token to get
     * @return Auth token from database
     */
    public static AuthToken getAuthTokenByUser(String username) throws SQLException {
        Connection conn = DbConnection.getConnection();
        AuthToken authToken = null;

        String sql = "SELECT * FROM AuthToken WHERE Username = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, username);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            User user = UserDao.getUser(username);
            authToken = new AuthToken(rs.getString("Token"), user);
        }

        return authToken;
    }

    /**
     * Deletes an auth token from the database.
     *
     * @param token Token of auth token to delete
     */
    public static void deleteAuthToken(String token) throws SQLException {
        Connection conn = DbConnection.getConnection();

        String sql = "DELETE FROM AuthToken WHERE Token = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, token);

        pstmt.executeUpdate();
    }

    /**
     * Deletes an auth token from the database.
     *
     * @param username Name of user
     */
    public static void deleteAuthTokenByUser(String username) throws SQLException {
        Connection conn = DbConnection.getConnection();

        String sql = "DELETE FROM AuthToken WHERE Username = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, username);

        pstmt.executeUpdate();
    }

    /**
     * Deletes an auth token from the database.
     *
     * @param token Token of auth token to delete
     */
    public static void deleteAll() throws SQLException {
        Connection conn = DbConnection.getConnection();

        String sql = "DELETE FROM AuthToken";

        Statement stmt = conn.createStatement();
        stmt.executeUpdate(sql);
    }
}
