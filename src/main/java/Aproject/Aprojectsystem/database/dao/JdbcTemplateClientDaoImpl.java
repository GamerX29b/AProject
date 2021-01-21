package Aproject.Aprojectsystem.database.dao;

import Aproject.Aprojectsystem.database.AddToBase;
import Aproject.Aprojectsystem.database.classes.ClientDb;
import Aproject.Aprojectsystem.database.classes.OrderDb;
import Aproject.Aprojectsystem.database.mapper.ClientDbMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;
import java.util.logging.Logger;

@Repository
public class JdbcTemplateClientDaoImpl implements ClientDao {

    static Logger LOGGER = Logger.getLogger(AddToBase.class.getName());

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private OrderDao orderDao;


    @Autowired
    public void setDataSource(DataSource dataSource){
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public ClientDb getClientById(int id){
        String sql = "SELECT * FROM \"client\" WHERE id = ?";
        ClientDb clientDb = jdbcTemplate.queryForObject(sql, new ClientDbMapper(), id);

        List<OrderDb> orderDbList = orderDao.getOrderByClientId(id);
        clientDb.setOrder(orderDbList);

        for (OrderDb orderDb : orderDbList){
            Map<Integer, OrderDb> mapOrder = new HashMap<>();
            Set<Integer> productIdCollection = new HashSet<>() {
            };

            productIdCollection.add(orderDb.getProductId()); //Коллекция айди продуктов

            orderDb.getId();
        }

        return clientDb;
    }

    @Override
    public List<ClientDb> getAllClientsNoOrder(int id){
        String sql = "SELECT * FROM \"client\" WHERE Id = ?";
        List<ClientDb> clientDb = jdbcTemplate.query(sql, new ClientDbMapper());
        return clientDb;
    }




    @Override
    public int createClientGetId(ClientDb client) {
        return 0;
    }


        /**
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
     */
}
