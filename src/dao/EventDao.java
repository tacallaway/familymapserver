package dao;

import model.Event;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Event data access object for creating, getting, and deleting events from the database.
 */
public class EventDao {

    /**
     * Adds a new event to the database.
     *
     * @param event Event object to add
     */
    public static void insertEvent(Event event) throws SQLException {
        Connection conn = DbConnection.getConnection();

        String sql = "INSERT INTO Event(EventID, Descendant, PersonID, Latitude, Longitude, Country, City, EventType, Year) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, event.getEventID());
        pstmt.setString(2, event.getDescendant() != null ? event.getDescendant() : null);
        pstmt.setString(3, event.getPersonID());
        pstmt.setDouble(4, event.getLatitude());
        pstmt.setDouble(5, event.getLongitude());
        pstmt.setString(6, event.getCountry());
        pstmt.setString(7, event.getCity());
        pstmt.setString(8, event.getEventType());
        pstmt.setInt(9, event.getYear());

        pstmt.executeUpdate();
    }

    /**
     * Gets an event object from the database.
     *
     * @param eventID ID of event to retrieve
     * @return Event from database
     */
    public static Event getEvent(String eventID) throws SQLException {
        Connection conn = DbConnection.getConnection();
        Event event = null;

        String sql = "SELECT * FROM Event WHERE EventID = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, eventID);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            event = new Event(rs.getString("EventID"), rs.getString("Descendant"), rs.getString("PersonID"),
                    rs.getDouble("Latitude"), rs.getDouble("Longitude"), rs.getString("Country"), rs.getString("City"),
                    rs.getString("EventType"), rs.getInt("Year"));
        }

        return event;
    }

    /**
     * Gets all events for a user.
     *
     * @param username Name of the user
     * @return List of events
     * @throws SQLException
     */
    public static List<Event> getEvents(String username) throws SQLException {

        List<Event> events = new ArrayList<Event>();

        Connection conn = DbConnection.getConnection();

        String sql = "SELECT * FROM Event WHERE Descendant = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, username);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            Event event = new Event(rs.getString("EventID"), rs.getString("Descendant"), rs.getString("PersonID"),
                    rs.getDouble("Latitude"), rs.getDouble("Longitude"), rs.getString("Country"), rs.getString("City"),
                    rs.getString("EventType"), rs.getInt("Year"));

            events.add(event);
        }

        return events;
    }


    /**
     * Deletes an event from the database.
     *
     * @param eventID ID of event to delete
     */
    public static void deleteEvent(String eventID) {

    }

    public static void deleteEvents(String username) throws SQLException {
        Connection conn = DbConnection.getConnection();

        String sql = "DELETE FROM Event WHERE Descendant = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, username);

        pstmt.executeUpdate();
    }

    /**
     * Deletes all records from the Event table.
     */
    public static void deleteAll() throws SQLException {
        Connection conn = DbConnection.getConnection();

        String sql = "DELETE FROM Event";

        Statement stmt = conn.createStatement();
        stmt.executeUpdate(sql);
    }
}
