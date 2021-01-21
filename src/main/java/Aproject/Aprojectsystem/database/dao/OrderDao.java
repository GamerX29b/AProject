package Aproject.Aprojectsystem.database.dao;

import Aproject.Aprojectsystem.database.classes.OrderDb;

import javax.sql.DataSource;

public interface OrderDao {

    public void setDataSource(DataSource dataSource);

    public OrderDb getOrderById(int id);

}
