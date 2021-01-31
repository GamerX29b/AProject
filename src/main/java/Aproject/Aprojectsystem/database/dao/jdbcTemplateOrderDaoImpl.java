package Aproject.Aprojectsystem.database.dao;

import Aproject.Aprojectsystem.database.classes.OrderDb;
import Aproject.Aprojectsystem.database.classes.ProductDb;
import Aproject.Aprojectsystem.database.mapper.OrderDbMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

@Repository
public class jdbcTemplateOrderDaoImpl implements OrderDao{

    @Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private ProductDao productDao;

    public jdbcTemplateOrderDaoImpl(DataSource dataSource, JdbcTemplate jdbcTemplate, ProductDao productDao){
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
        this.productDao = productDao;
    }

    @Override
    public void setDataSource(DataSource dataSource) {

    }

    @Override
    public OrderDb getOrderById(int id){
        String sql = "SELECT * FROM \"order\" WHERE \"Id\" = ?";
        OrderDb orderDb = jdbcTemplate.queryForObject(sql, new OrderDbMapper(), id);
        return orderDb;
    }

    @Override
    public List<OrderDb> getOrderByClientId(int clientId){
        String sql = "SELECT * FROM \"order\" WHERE \"userId\" = ?";
        Set<Integer> idProducts = new HashSet<>();
        Map<Integer, OrderDb> orderDbMap = new HashMap<>();
        List<OrderDb> orderDb = jdbcTemplate.query(sql, new OrderDbMapper(), clientId);
        for(OrderDb order: orderDb){
            idProducts.add(order.getProductId());
            orderDbMap.put(order.getOrderGroupId(), order);
        }
        Map<Integer, ProductDb> productDbMap = productDao.getProductMapBySetId(idProducts);
        for(OrderDb order: orderDb){
            if(orderDbMap.get(order.getOrderGroupId()).getProduct() == null) {
                List<ProductDb> productDbList = new LinkedList<>();
                productDbList.add(productDbMap.get(order.getProductId()));
                orderDbMap.get(order.getOrderGroupId()).setProduct(productDbList);
            } else {
                orderDbMap.get(order.getOrderGroupId()).getProduct().add(productDbMap.get(order.getProductId()));
            }
        }
        List<OrderDb> sortOrder = new ArrayList<OrderDb>(orderDbMap.values());
        return sortOrder;
    }

    @Override
    public OrderDb getOrderFromProductByGroupId(int groupId){
        String sql = "SELECT * FROM \"order\" WHERE \"orderGroupId\" = ?";
        List<OrderDb> orderDbList = jdbcTemplate.query(sql, new OrderDbMapper(), groupId);
        Set<Integer> productSet = new HashSet<>();
        for (OrderDb orderDbToSet : orderDbList) {
            productSet.add(orderDbToSet.getProductId());
        }
        OrderDb order = new OrderDb();
        if (!orderDbList.isEmpty()) {
             order = orderDbList.get(0);
            order.setProduct(productDao.getProductBySetId(productSet));
        }
        return order;
    }

    @Override
    public void addNewOrder (List<OrderDb> orderDbs, int idClient){
        String sql = "INSERT INTO \"order\" (\"orderGroupId\", \"userId\", \"ProductId\", \"quantity\", \"date\" ) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter () {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                OrderDb orderDb = orderDbs.get(i);
                ps.setInt(1, orderDb.getOrderGroupId());
                ps.setInt(2, orderDb.getUserId());
                ps.setInt(3, orderDb.getProductId());
                ps.setInt(4, orderDb.getQuantity());
                Long date = orderDb.getDate().getTime();
                ps.setDate(5, new Date(date));
            }
            @Override
            public int getBatchSize() {
                return orderDbs.size();
            }
        });
    }
    /**
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
