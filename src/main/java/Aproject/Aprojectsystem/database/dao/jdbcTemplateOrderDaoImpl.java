package Aproject.Aprojectsystem.database.dao;

import Aproject.Aprojectsystem.database.classes.ClientDb;
import Aproject.Aprojectsystem.database.classes.OrderDb;
import Aproject.Aprojectsystem.database.mapper.ClientDbMapper;

public class jdbcTemplateOrderDaoImpl implements OrderDao{

    @Override
    public OrderDb getOrderById(int id){
        String sql = "SELECT * FROM \"order\" WHERE Id = ?";
        OrderDb orderDb = jdbcTemplate.queryForObject(sql, new Object[]{id}, new ClientDbMapper());
        return orderDb;

        return
    }
}
