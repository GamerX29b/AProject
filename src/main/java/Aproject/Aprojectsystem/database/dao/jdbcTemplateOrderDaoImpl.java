package Aproject.Aprojectsystem.database.dao;

import Aproject.Aprojectsystem.database.classes.OrderDb;
import Aproject.Aprojectsystem.database.mapper.OrderDbMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class jdbcTemplateOrderDaoImpl implements OrderDao{
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public OrderDb getOrderById(int id){
        String sql = "SELECT * FROM \"order\" WHERE Id = ?";
        OrderDb orderDb = jdbcTemplate.queryForObject(sql, new OrderDbMapper(), id);
        return orderDb;
    }

    @Override
    public List<OrderDb> getOrderByClientId(int clientId){
        String sql = "SELECT * FROM \"order\" WHERE userId = ?";
        List<OrderDb> orderDb = jdbcTemplate.query(sql, new OrderDbMapper(), clientId);
        return orderDb;
    }
}
