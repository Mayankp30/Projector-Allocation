package databaseConnectivity;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by mayank on 6/24/17.
 */


public class DbConnectivity {

    // JDBC driver name and database URL
    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/Projector";

    //  Database credentials
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "12345";

    public static Connection createConnection() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("connection established");
        return DriverManager.getConnection(DB_CONNECTION,DB_USER, DB_PASSWORD);
    }
}
