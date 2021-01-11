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
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;


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
                GregorianCalendar calendar = new GregorianCalendar();
                calendar.setTime(resultSet.getDate(5));
                XMLGregorianCalendar xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
                order.setDate(xmlGregorianCalendar);
            }

            conn.close();
        } catch (SQLException | DatatypeConfigurationException e) {
            System.out.println(e.getMessage());
        }
        return order;
    }
    public Client getClientFromAllOrders(int idClient){
        Client client = new Client();
        List<Order> listOrder = new ArrayList<>();
        String select = new StringBuilder().append("SELECT (1) * FROM \"user\" WHERE id =").append(idClient).append(";").toString();
        "SELECT client.id, client.name, client.adress, order.groupId, order.productId, order.date, product.id, product.name" +
                "FROM client, order, product " +
                "WHERE client.id = order.owner_id and order.ProductId = product.id";

        Connection conn = getConnect();
        try {


            Statement stmt  = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(select);

        while (resultSet.next()) {
            List<Product> productList = new ArrayList<>();
            client.setId(BigInteger.valueOf(resultSet.getInt(1)));
            client.setClientName(resultSet.getString(2));
            client.setClientAddress(resultSet.getString(3));

            productList


        }
            client.setOrder();

            conn.close();
        } catch (SQLException | DatatypeConfigurationException e) {
            System.out.println(e.getMessage());
        }
        return client;
    }
}
