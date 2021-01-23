package Aproject.Aprojectsystem;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DriverManagerDataSourceExample {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
/*        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/AProject");
        dataSource.setUsername("postgres");
        dataSource.setPassword("SUPERUSER10");*/
}
