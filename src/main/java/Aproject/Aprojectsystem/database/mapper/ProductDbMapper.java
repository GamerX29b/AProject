package Aproject.Aprojectsystem.database.mapper;

import Aproject.Aprojectsystem.database.classes.ProductDb;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDbMapper implements RowMapper<ProductDb> {


    @Override
    public ProductDb mapRow(ResultSet rs, int rowNum) throws SQLException {
        ProductDb productDb = new ProductDb();
        productDb.setId(rs.getInt(1));
        productDb.setNameProduct(rs.getString(2));
        productDb.setQuantity(rs.getInt(3));
        return productDb;
    }
}
