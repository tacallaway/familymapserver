package dao;

import org.sqlite.SQLiteConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static Connection conn;
    private static boolean testMode = false;

    private static Connection createConnection() {
        java.sql.Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:data/" + (testMode ? "test.db" : "data.db");

            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);

            // create a connection to the database
            conn = DriverManager.getConnection(url, config.toProperties());

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }


    /**
     * Gets a database connection.
     * @return Database connection.
     */
    public static synchronized Connection getConnection() {
        if (conn == null) {
            conn = createConnection();
        }

        return conn;
    }

    public static boolean isTestMode() {
        return testMode;
    }

    public static void setTestMode(boolean testMode) {
        DbConnection.testMode = testMode;
    }
}
