package Aproject.Aprojectsystem.database;



import Aproject.Aprojectsystem.XSDSchema.Client;
import Aproject.Aprojectsystem.XSDSchema.Order;
import Aproject.Aprojectsystem.XSDSchema.Product;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddToBase extends GenerateConnection {

    static Logger LOGGER = Logger.getLogger(AddToBase.class.getName());
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
            if(!client.getOrder().isEmpty()){
                if(!createOrders(new LinkedList<>(client.getOrder()), BigInteger.valueOf(id))){ //Добавляем клиента сразу с заказами
                    conn.close();
                    return 0;
                }
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }

    public boolean createOrders(LinkedList<Order> orders, BigInteger clientId) {
        StringBuffer insert = new StringBuffer().append("INSERT INTO \"order\" (\"orderGroupId\", \"userId\", \"ProductId\", \"quantity\", \"date\" ) VALUES ");

        int cycleOrder = 0;
        for(Order order : orders){
            if (order.getProduct() == null) {
                LOGGER.log(Level.WARNING, "Попытка добавления заказа без указания продукта");
                return false;
            }
            BigInteger valueProducts = BigInteger.ZERO;
            for (Product product : order.getProduct()) {
                if (product.getId() == null) {
                    LOGGER.log(Level.WARNING, "Попытка добавления продукта без указания айди продукта");
                    return false;
                }
                valueProducts = valueProducts.add(product.getQuantity());
            }
            order.setQuantity(valueProducts);
        }
        Connection conn = getConnect();
        for (Order order : orders) {
            int cycleProduct = 0;
            cycleOrder++;
            for (Product product : order.getProduct()) {
                cycleProduct++;
                insert.append("('").append(order.getOrderGroupId()).append("','").append(clientId).append("','")
                        .append(product.getId()).append("','").append(order.getQuantity()).append("','")
                        .append(order.getDate()).append("')");
                if (order.getProduct().size() == cycleProduct && orders.size() == cycleOrder) {
                    insert.append(";");
                } else {
                    insert.append(",");
                }
            }
        }
        try {
            Statement stmt  = conn.createStatement();
            stmt.executeUpdate(insert.toString());
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public int createProduct(Product product) {
        StringBuffer insert = new StringBuffer().append("INSERT INTO \"product\" (\"nameProduct\", \"Quantity\") VALUES ")
            .append("('").append(product.getNameProduct()).append("','").append(product.getQuantity()).append("') RETURNING id ;");
        Connection conn = getConnect();
        int id = 0;
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(insert.toString());
            ResultSet resultSet = stmt.executeQuery(insert.toString());
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
