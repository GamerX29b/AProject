package Aproject.Aprojectsystem.database.dao;

import Aproject.Aprojectsystem.database.classes.ProductDb;

import javax.sql.DataSource;
import java.util.List;
import java.util.Set;

public interface ProductDao {

    public void setDataSource(DataSource dataSource);

    public List<ProductDb> getProductById(int productId);

    public List<ProductDb> getProductBySetId(Set<Integer> productId);

}
