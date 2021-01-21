package Aproject.Aprojectsystem.database.dao;

import Aproject.Aprojectsystem.database.classes.ClientDb;

import javax.sql.DataSource;
import java.util.List;

public interface ClientDao {

    public void setDataSource(DataSource dataSource);

    public int createClientGetId(ClientDb client);

    public List<ClientDb> getAllClientsNoOrder(int id);

    public ClientDb getClientById(int id);
}