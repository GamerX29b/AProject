package Aproject.Aprojectsystem.database;

import java.sql.*;


public class GenerateConnection {

    private static final String url = "jdbc:postgresql://localhost:5432/AProject";
    private static final String user = "postgres";
    private static final String password = "SUPERUSER10";

    protected Connection getConnect() {

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

}
