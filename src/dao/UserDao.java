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
    public static void insertUser(User user) {
        Connection conn = DbConnection.getConnection();

        String sql = "INSERT INTO User(UserName, Password, Email, FirstName, LastName, Gender, PersonID) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getFirstName());
            pstmt.setString(5, user.getLastName());
            pstmt.setString(6, user.getGender());
            pstmt.setString(7, user.getPerson() != null ? user.getPerson().getPersonID() : null);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets a user from the database.
     *
     * @param userName Username of user to retrieve
     * @return User model
     */
    public static User getUser(String userName) {
        Connection conn = DbConnection.getConnection();
        User user = null;

        String sql = "SELECT * FROM User WHERE UserName = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Person person = PersonDao.getPerson(rs.getString("PersonID"));
                user = new User(rs.getString("UserName"), rs.getString("Password"), rs.getString("Email"), rs.getString("FirstName"), rs.getString("LastName"), rs.getString("Gender"), person);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return user;
    }

    /**
     * Deletes a user from the database.
     *
     * @param userName Username of user to delete
     */
    public static void deleteUser(String userName) {
        Connection conn = DbConnection.getConnection();

        String sql = "DELETE FROM User WHERE UserName = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userName);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes all records from the User table.
     */
    public static void deleteAll() {
        Connection conn = DbConnection.getConnection();

        String sql = "DELETE FROM User";

        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
