package Aproject.Aprojectsystem.database.mapper;

import Aproject.Aprojectsystem.database.classes.OrderDb;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDbMapper implements RowMapper<OrderDb> {



    @Override
    public OrderDb mapRow(ResultSet rs, int rowNum) throws SQLException {
        OrderDb orderDb = new OrderDb();

        orderDb.setId(rs.getInt(1));
        orderDb.setOrderGroupId(rs.getInt(2));
        orderDb.setUserId(rs.getInt(3));
        orderDb.setProductId(rs.getInt(4));
        orderDb.setQuantity(rs.getInt(5));
        orderDb.setDate(rs.getDate(6));

        return orderDb;
    }
}
