package dao;

import model.Person;
import model.User;

import java.sql.*;

/**
 * User data access object for creating, getting, and deleting users from the database.
 */
public class UserDao {

    /**
     * Adds a new user to the databaase.
     *
     * @param user User model to add
     */
    public static void insertUser(User user) throws SQLException {
        Connection conn = DbConnection.getConnection();

        String sql = "INSERT INTO User(UserName, Password, Email, PersonID) VALUES (?, ?, ?, ?)";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, user.getUserName());
        pstmt.setString(2, user.getPassword());
        pstmt.setString(3, user.getEmail());
        pstmt.setString(4, user.getPerson() != null ? user.getPerson().getPersonID() : null);

        pstmt.executeUpdate();
    }

    /**
     * Gets a user from the database.
     *
     * @param userName Username of user to retrieve
     * @return User model
     */
    public static User getUser(String userName) throws SQLException {
        Connection conn = DbConnection.getConnection();
        User user = null;

        String sql = "SELECT * FROM User WHERE UserName = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, userName);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            Person person = PersonDao.getPerson(rs.getString("PersonID"));
            user = new User(rs.getString("UserName"), rs.getString("Password"), rs.getString("Email"), person);
        }

        return user;
    }

    /**
     * Deletes a user from the database.
     *
     * @param userName Username of user to delete
     */
    public static void deleteUser(String userName) throws SQLException {
        Connection conn = DbConnection.getConnection();

        String sql = "DELETE FROM User WHERE UserName = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, userName);

        pstmt.executeUpdate();
    }

    /**
     * Deletes all records from the User table.
     */
    public static void deleteAll() throws SQLException {
        Connection conn = DbConnection.getConnection();

        String sql = "DELETE FROM User";

        Statement stmt = conn.createStatement();
        stmt.executeUpdate(sql);
    }
}
