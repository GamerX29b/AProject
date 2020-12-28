package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GenerateConnection {

    private final String url = "jdbc:postgresql://localhost:5432/AProject";
    private final String user = "SUPERUSER10";
    private final String password = "SUPERUSER10";


    public Connection connect() {
        String insert = "INSERT INTO";

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");

            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }


}
