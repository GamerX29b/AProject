package Aproject.Aprojectsystem.database.mapper;

import Aproject.Aprojectsystem.database.classes.ClientDb;
import Aproject.Aprojectsystem.database.classes.OrderDb;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDbMaper implements RowMapper<OrderDb> {



    @Override
    public OrderDb mapRow(ResultSet rs, int rowNum) throws SQLException {
        OrderDb orderDb = new OrderDb();

        orderDb.setId(rs.getInt(1));
        orderDb.set(rs.getString(2));
        //todo дописать
        orderDb.setClientAddress(rs.getString(3));

        return orderDb;
    }
}
