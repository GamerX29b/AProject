package Aproject.Aprojectsystem.database.dao;

import Aproject.Aprojectsystem.XSDSchema.Order;
import Aproject.Aprojectsystem.XSDSchema.Product;
import Aproject.Aprojectsystem.database.classes.ClientDb;
import Aproject.Aprojectsystem.database.mapper.ClientDbMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

public class JdbcTemplateClientDaoImpl implements ClientDao {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;


    @Override
    public void setDataSource(DataSource dataSource){
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public ClientDb getClientsById(int id){
        String sql = "SELECT * FROM \"client\" WHERE Id = ?";
        ClientDb clientDb = jdbcTemplate.queryForObject(sql, new ClientDbMapper());
        return clientDb;
    }

    @Override
    public int createClientGetId(ClientDb client) {

        //todo переделать под SPRING JDBC
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

    //todo тоже переделать под SJDBC
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
}
