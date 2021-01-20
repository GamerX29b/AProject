package Aproject.Aprojectsystem.database.dao;

import Aproject.Aprojectsystem.database.classes.ClientDb;

import javax.sql.DataSource;

public interface ClientDao {

    public void setDataSource(DataSource dataSource);

    public int createClientGetId(ClientDb client);

    public ClientDb getClientsById(int id);
}