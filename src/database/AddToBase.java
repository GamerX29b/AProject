package database;

import XSDSchema.Client;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AddToBase extends GenerateConnection {

    public boolean createClient(Client client){


        String insert = new StringBuffer().append("INSERT INTO \"client\" (\"clientName\", \"clientAddress\") VALUES (")
                .append(client.getClientName()).append(",").append(client.getClientAddress()).append(");").toString();

        String select = "SELECT MAX(ID) FROM \"client\";";

        Connection conn = getConnect();
        try {

            Statement stmt  = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(select);
            int id = resultSet.getInt(1) + 1;

            stmt = conn.createStatement();

            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


        return true;
    }
}
