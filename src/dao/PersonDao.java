package dao;

import model.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Person data access object for creating, getting, and deleting persons from the database.
 */
public class PersonDao {

    /**
     * Adds a new person to the database.
     *
     * @param person Person to add
     */
    public static void insertPerson(Person person) throws SQLException {
        Connection conn = DbConnection.getConnection();

        String sql = "INSERT INTO Person(PersonID, Descendant, FirstName, LastName, Gender, Father, Mother, Spouse) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, person.getPersonID());
        pstmt.setString(2, person.getDescendant());
        pstmt.setString(3, person.getFirstName());
        pstmt.setString(4, person.getLastName());
        pstmt.setString(5, person.getGender());
        pstmt.setString(6, person.getFather());
        pstmt.setString(7, person.getMother());
        pstmt.setString(8, person.getSpouse());

        pstmt.executeUpdate();
    }

    public static void updatePerson(Person person) throws SQLException {
        Connection conn = DbConnection.getConnection();

        String sql = "UPDATE Person SET Descendant=?, FirstName=?, LastName=?, Gender=?, Father=?, Mother=?, Spouse=? where PersonID=?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, person.getDescendant());
        pstmt.setString(2, person.getFirstName());
        pstmt.setString(3, person.getLastName());
        pstmt.setString(4, person.getGender());
        pstmt.setString(5, person.getFather());
        pstmt.setString(6, person.getMother());
        pstmt.setString(7, person.getSpouse());
        pstmt.setString(8, person.getPersonID());

        pstmt.executeUpdate();
    }

    /**
     * Gets a person from the database.
     *
     * @param personID ID of person to get
     * @return Person from database
     */
    public static Person getPerson(String personID) throws SQLException {
        Connection conn = DbConnection.getConnection();
        Person person = null;

        String sql = "SELECT * FROM Person WHERE PersonID = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, personID);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            person = new Person(rs.getString("PersonID"), rs.getString("FirstName"), rs.getString("LastName"), rs.getString("Gender"));
            person.setDescendant(rs.getString("Descendant"));
            person.setFather(rs.getString("Father"));
            person.setMother(rs.getString("Mother"));
            person.setSpouse(rs.getString("Spouse"));
        }

        return person;
    }

    public static List<Person> getPersons(String username) throws SQLException {
        List<Person> persons = new ArrayList<Person>();

        Connection conn = DbConnection.getConnection();

        String sql = "SELECT * FROM Person WHERE Descendant = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, username);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            Person person = new Person(rs.getString("PersonID"), rs.getString("FirstName"), rs.getString("LastName"), rs.getString("Gender"));
            person.setDescendant(rs.getString("Descendant"));
            person.setFather(rs.getString("Father"));
            person.setMother(rs.getString("Mother"));
            person.setSpouse(rs.getString("Spouse"));

            persons.add(person);
        }

        return persons;
    }

    /**
     * Deletes a person from the database.
     *
     * @param personID ID of person to delete
     */
    public static void deletePerson(String personID) throws SQLException {
        Connection conn = DbConnection.getConnection();

        String sql = "DELETE FROM Person WHERE PersonID = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, personID);

        pstmt.executeUpdate();
    }

    public static void deletePersons(String username) throws SQLException {
        Connection conn = DbConnection.getConnection();

        String sql = "DELETE FROM Person WHERE Descendant = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, username);

        pstmt.executeUpdate();
    }

    /**
     * Deletes all records from the Person table.
     */
    public static void deleteAll() throws SQLException {
        Connection conn = DbConnection.getConnection();

        String sql = "DELETE FROM Person";

        Statement stmt = conn.createStatement();
        stmt.executeUpdate(sql);
    }
}
