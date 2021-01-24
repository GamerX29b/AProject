package Aproject.Aprojectsystem.database.dao;

import Aproject.Aprojectsystem.database.classes.ProductDb;
import Aproject.Aprojectsystem.database.mapper.ProductDbMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class JdbcTemplateProductDaoImpl implements ProductDao {

    @Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplateProductDaoImpl(DataSource dataSource, JdbcTemplate jdbcTemplate){
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void setDataSource(DataSource dataSource){
    }

    @Override
    public List<ProductDb> getProductById(int productId){
        String sql = "SELECT * FROM \"product\" WHERE id = ?";
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

    @Override
    public Map<Integer, ProductDb> getProductMapBySetId(Set<Integer> productId){
        List<ProductDb> productDbList = new ArrayList<>();
        for(Integer idProduct : productId){
            String sql = "SELECT * FROM \"product\" WHERE id = ?";
            productDbList.add(jdbcTemplate.queryForObject(sql, new ProductDbMapper(), idProduct));
        }
        Map<Integer, ProductDb> productDbMap = new HashMap<>();
        for (ProductDb productDb : productDbList){
            productDbMap.put(productDb.getId(), productDb);
        }
        return productDbMap;
    }


}
