package Aproject.Aprojectsystem.database.dao;

import Aproject.Aprojectsystem.database.classes.ClientDb;
import Aproject.Aprojectsystem.database.classes.OrderDb;
import Aproject.Aprojectsystem.database.mapper.ClientDbMapper;
import Aproject.Aprojectsystem.database.mapper.OrderDbMaper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class jdbcTemplateOrderDaoImpl implements OrderDao{
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Override
    public void setDataSource(DataSource dataSource){
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public OrderDb getOrderById(int id){
        String sql = "SELECT * FROM \"order\" WHERE Id = ?";
        OrderDb orderDb = jdbcTemplate.queryForObject(sql, new OrderDbMaper());
        return orderDb;

    }
}
