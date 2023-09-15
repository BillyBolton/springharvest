package dev.springharvest.library.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;

@TestConfiguration
@Lazy
public class TestContainerConfig {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public PostgreSQLContainer<?> postgreSQLContainer() {
        PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:13")
                .withDatabaseName("test")
                .withUsername("test")
                .withPassword("test");
        container.start();
        return container;
    }

    @Bean
    @Primary
    public DataSource dataSource(PostgreSQLContainer<?> container) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(container.getJdbcUrl());
        dataSource.setUsername(container.getUsername());
        dataSource.setPassword(container.getPassword());
        return dataSource;
    }

    //    @Bean
    //    public Liquibase liquibase(DataSource dataSource) throws Exception {
    //        try (Connection connection = dataSource.getConnection()) {
    //            DatabaseConnection databaseConnection = new JdbcConnection(connection);
    //            Database database = DatabaseFactory
    //                    .getInstance()
    //                    .findCorrectDatabaseImplementation(databaseConnection);
    //
    //            Liquibase liquibase =
    //                    new liquibase.Liquibase("db.changelog/db.changelog-master.yaml", new ClassLoaderResourceAccessor(),
    //                                            database);
    //
    //            // Drop all tables and re-run all changesets
    //            liquibase.dropAll();
    //            liquibase.update(new Contexts());
    //            return liquibase;
    //        }
    //    }

}
