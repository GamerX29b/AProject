package Aproject.Aprojectsystem.database;

import Aproject.Aprojectsystem.XSDSchema.Client;
import Aproject.Aprojectsystem.XSDSchema.Order;
import Aproject.Aprojectsystem.XSDSchema.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;


public class GetFromBase extends GenerateConnection {

    @Autowired
    public Client getClientById(int idClient) {
        Client client = new Client();

        String select = new StringBuilder().append("SELECT * FROM \"client\" WHERE id = ").append(idClient).append(";").toString();
        System.out.println(select);
        Connection conn = getConnect();
        try {

            Statement stmt  = conn.createStatement();

            ResultSet resultSet = stmt.executeQuery(select);
            while (resultSet.next()) {
                client.setId(BigInteger.valueOf(resultSet.getInt(1)));
                client.setClientName(resultSet.getString(2));
                client.setClientAddress(resultSet.getString(3));
            }

            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return client;
    }

    public Product getProductById(int idProduct) {
        Product product = new Product();

        String select = new StringBuilder().append("SELECT * FROM \"product\" WHERE id =").append(idProduct).append(";").toString();
        Connection conn = getConnect();
        try {

            Statement stmt  = conn.createStatement();

            ResultSet resultSet = stmt.executeQuery(select);
            while (resultSet.next()) {
                product.setId(BigInteger.valueOf(resultSet.getInt(1)));
                product.setNameProduct(resultSet.getString(2));
                product.setQuantity(BigInteger.valueOf(resultSet.getInt(3)));
            }

            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return product;
    }

    public Order getOrderById(int idOrder) {
        Order order = new Order();

        String select = new StringBuilder().append("SELECT * FROM \"order\" WHERE id =").append(idOrder).append(";").toString();
        Connection conn = getConnect();
        try {

            Statement stmt  = conn.createStatement();

            ResultSet resultSet = stmt.executeQuery(select);
            while (resultSet.next()) {
                order.setId(BigInteger.valueOf(resultSet.getInt(1)));
                order.setOrderGroupId(resultSet.getString(2));
                order.setQuantity(BigInteger.valueOf(resultSet.getInt(4)));
                order.setDate(dataToCalendar(resultSet.getDate(5)));
            }

            conn.close();
        } catch (SQLException | DatatypeConfigurationException e) {
            System.out.println(e.getMessage());
        }
        return order;
    }
    public Client getClientFromAllOrders(int idClient){
        Client client = new Client();
        String select =  new StringBuilder().append("SELECT \"client\".\"id\", \"client\".\"clientName\", \"client\".\"clientAddress\", " +
                "\"order\".\"orderGroupId\", \"order\".\"ProductId\", \"order\".\"date\",\n" +
                "\"order\".\"quantity\", \"product\".\"nameProduct\", \"order\".\"id\"" +
                "FROM \"client\", \"order\", \"product\"" +
                "WHERE \"client\".\"id\" = '").append(idClient).append("' and \"order\".\"ProductId\" = \"product\".\"id\"").toString();
        Map<BigInteger, Order> orderMap = new HashMap<>();
        Connection conn = getConnect();
        try {
            Statement stmt  = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(select);
        while (resultSet.next()) {
            Order order = new Order();
            Product product = new Product();
            client.setId(BigInteger.valueOf(resultSet.getInt(1)));
            client.setClientName(resultSet.getString(2));
            client.setClientAddress(resultSet.getString(3));
            order.setOrderGroupId(resultSet.getString(4));
            product.setId(BigInteger.valueOf(resultSet.getInt(5))); //У продукта и у ордера одинаковый айди
            order.setDate(dataToCalendar(resultSet.getDate(6)));
            order.setQuantity(BigInteger.valueOf(resultSet.getInt(7)));
            product.setNameProduct(resultSet.getString(8));
            order.setId(BigInteger.valueOf(resultSet.getInt(9)));
            orderMap.put(order.getId(), order);
            orderMap.get(order.getId()).getProduct().add(product);
        }
            List<Order> orderList = orderMap.values().stream()
                    .collect(Collectors.toList());
            client.setOrder(orderList);

            conn.close();
        } catch (SQLException | DatatypeConfigurationException e) {
            System.out.println(e.getMessage());
        }
        return client;
    }
}
