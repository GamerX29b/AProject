package Aproject.Aprojectsystem.database.dao;

import Aproject.Aprojectsystem.database.classes.ProductDb;
import Aproject.Aprojectsystem.database.mapper.ProductDbMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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


    /**
     * Специальный метод для защищщённого добавления данных в базу
     * PreparedStatement это метод который не позволяет подложить в String дополнительный SQL код
     * В целом SQL инъекция это передача в БД дополнительного кода, внутри SQL запроса. Например если данные из
     * текстового поля напрямую уходят в БД, то вписав туда SQL код, мы выполним его.
     *
     * KeyHolder - Дополнительный уровень защиты транзакций, возвращает сгенерированный ключ в случае успешной
     * транзакции. Но поддерживается не всеми базами. Оракл и Постгрес поддерживают.
     *
     */
    @Override
    public void setNewProduct (ProductDb productDb){
        String sql = "INSERT INTO \"product\" (\"nameProduct\", \"Quantity\") VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
                        ps.setString(1, productDb.getNameProduct());
                        ps.setInt(2, productDb.getQuantity());
                        return ps;
                    }
                },
                keyHolder);
    }


}
