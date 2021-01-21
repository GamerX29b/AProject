package Aproject.Aprojectsystem.database.dao;

import Aproject.Aprojectsystem.database.classes.ProductDb;
import Aproject.Aprojectsystem.database.mapper.ProductDbMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
public class JdbcTemplateProductDaoImpl implements ProductDao {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;


    @Autowired
    public void setDataSource(DataSource dataSource){
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<ProductDb> getProductById(int productId){
        String sql = "SELECT * FROM \"order\" WHERE userId = ?";
        List<ProductDb> productDb = jdbcTemplate.query(sql, new ProductDbMapper(), productId);
        return productDb;
    }

    @Override
    public List<ProductDb> getProductBySetId(Set<Integer> productId){
        List<ProductDb> productDbList = new ArrayList<>();
        for(Integer idProduct : productId){
            String sql = "SELECT * FROM \"product\" WHERE id = ?";
            productDbList.add(jdbcTemplate.queryForObject(sql, new ProductDbMapper(), idProduct));
        }
        return productDbList;
    }

}
