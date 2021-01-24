package Aproject.Aprojectsystem.database.dao;

import Aproject.Aprojectsystem.database.AddToBase;
import Aproject.Aprojectsystem.database.classes.ClientDb;
import Aproject.Aprojectsystem.database.classes.OrderDb;
import Aproject.Aprojectsystem.database.mapper.ClientDbMapper;
import Aproject.Aprojectsystem.database.mapper.IdReturnMapper;
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
    private OrderDao orderDao;

    @Override
    public void setDataSource(DataSource dataSource) {

    }

    @Autowired
    public JdbcTemplateClientDaoImpl(DataSource dataSource, JdbcTemplate jdbcTemplate, OrderDao orderDao){
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
        this.orderDao = orderDao;
    }

    @Override
    public ClientDb getClientById(int id){
        String sql = "SELECT * FROM \"client\" WHERE id = ?";
        ClientDb clientDb = jdbcTemplate.queryForObject(sql, new ClientDbMapper(), id);

        List<OrderDb> orderDbList = orderDao.getOrderByClientId(id);
        if (orderDbList != null) {
            clientDb.setOrder(orderDbList);
        }

        return clientDb;
    }

    @Override
    public List<ClientDb> getAllClientsNoOrder(int id){
        String sql = "SELECT * FROM \"client\" WHERE id = ? ;";
        List<ClientDb> clientDb = jdbcTemplate.query(sql, new ClientDbMapper(), id);
        return clientDb;
    }


    @Override
    public int createClientGetId(ClientDb client) {
        String sql = "INSERT INTO \"client\" (\"clientName\", \"clientAddress\") VALUES (? , ?) RETURNING \"id\"";
        Integer id = jdbcTemplate.queryForObject(sql, new IdReturnMapper(), client.getClientName(), client.getClientAddress());
        if (id == null) {
            return 0;
        }
        return id;
    }
}
