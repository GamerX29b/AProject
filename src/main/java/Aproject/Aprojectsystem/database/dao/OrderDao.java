package Aproject.Aprojectsystem.database.dao;

import Aproject.Aprojectsystem.database.classes.OrderDb;

import javax.sql.DataSource;
import java.util.List;

public interface OrderDao {

    public void setDataSource(DataSource dataSource);

    public List<OrderDb> getOrderByClientId(int clientId);

    public void addNewOrder (List<OrderDb> orderDbs, int idClient);

    public OrderDb getOrderById(int id);

    public OrderDb getOrderFromProductByGroupId(int groupId);

}
