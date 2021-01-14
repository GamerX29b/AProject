package Aproject.Aprojectsystem.database;



import Aproject.Aprojectsystem.XSDSchema.Client;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AddToBase extends GenerateConnection {

    public boolean createClientGetSuccess(Client client){
        String insert = new StringBuffer().append("INSERT INTO \"client\" (\"clientName\", \"clientAddress\") VALUES ('")
                .append(client.getClientName()).append("','").append(client.getClientAddress()).append("');").toString();
        Connection conn = getConnect();
        try {
            Statement stmt  = conn.createStatement();
            stmt.executeUpdate(insert);
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    public int createClientGetId(Client client){
        String insert = new StringBuffer().append("INSERT INTO \"client\" (\"clientName\", \"clientAddress\") VALUES ('")
                .append(client.getClientName()).append("','").append(client.getClientAddress()).append("') RETURNING id ;").toString();
        int id = 0;
        Connection conn = getConnect();
        try {
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(insert);
            while (resultSet.next()) {
                id = resultSet.getInt(1);
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }

    public int createOrderGetId(Client client){
        String insert = new StringBuffer().append("INSERT INTO \"order\" (\"orderGroupId\", \"userId\", \"ProductId\", \"quantity\", \"date\" ) " +
                "VALUES ('").append(client.getClientName()).append("','").append(client.getClientAddress()).append("');").toString(); //todo дописать
        String select = "SELECT MAX(ID) FROM \"client\";"; //Узнать какой айди
        Connection conn = getConnect();
        int id = 0;
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(insert);
            ResultSet resultSet = stmt.executeQuery(select);
            while (resultSet.next()) {
                id = resultSet.getInt(1);
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }
}
