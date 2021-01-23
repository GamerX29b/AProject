package Aproject.Aprojectsystem.database.mapper;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IdReturnMapper implements RowMapper<Integer> {

    @Override
    public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Integer integer = rs.getInt(1);
        return integer;
    }
}
