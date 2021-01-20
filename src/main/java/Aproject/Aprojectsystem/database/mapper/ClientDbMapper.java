package Aproject.Aprojectsystem.database.mapper;

import Aproject.Aprojectsystem.database.classes.ClientDb;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientDbMapper implements RowMapper<ClientDb> {

    @Override
    public ClientDb mapRow(ResultSet rs, int rowNum) throws SQLException{
        ClientDb clientDb = new ClientDb();

        clientDb.setId(rs.getInt(1));
        clientDb.setClientName(rs.getString(2));
        clientDb.setClientAddress(rs.getString(3));

        return clientDb;
    }
}
