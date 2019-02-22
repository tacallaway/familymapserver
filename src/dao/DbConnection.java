package dao;

import org.sqlite.SQLiteConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnection {
    private static Connection connection;

    private static Connection createConnection() {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:data/data.db";

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

    public static synchronized Connection getConnection() {
        if (connection == null) {
            connection = createConnection();
        }

        return connection;
    }
}
