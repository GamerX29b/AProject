package Aproject.Aprojectsystem.database;

import javax.xml.crypto.Data;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.*;
import java.util.Date;
import java.util.GregorianCalendar;


public abstract class GenerateConnection {

    private static final String url = "jdbc:postgresql://localhost:5432/AProject";
    private static final String user = "postgres";
    private static final String password = "SUPERUSER10";

    protected Connection getConnect() {

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    protected XMLGregorianCalendar dataToCalendar (Date date) throws DatatypeConfigurationException {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
    }

}
