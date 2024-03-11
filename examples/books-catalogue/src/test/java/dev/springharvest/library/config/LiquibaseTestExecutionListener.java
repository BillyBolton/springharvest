package dev.springharvest.library.config;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

public class LiquibaseTestExecutionListener implements TestExecutionListener {

  @Override
  public void beforeTestMethod(TestContext testContext) throws Exception {
    JdbcTemplate jdbcTemplate = testContext.getApplicationContext().getBean(JdbcTemplate.class);
    String url = jdbcTemplate.getDataSource().getConnection().getMetaData().getURL();
    String user = jdbcTemplate.getDataSource().getConnection().getMetaData().getUserName();
    String password = "test";  // Replace with actual password or get it dynamically

    try (var connection = jdbcTemplate.getDataSource().getConnection()) {
      DatabaseConnection databaseConnection = new JdbcConnection(connection);
      Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(databaseConnection);

      Liquibase liquibase =
          new Liquibase("db.changelog/db.changelog-master.yaml", new ClassLoaderResourceAccessor(), database);

      // Drop all tables and re-run all changesets
      liquibase.dropAll();
      liquibase.update("test");
    }
  }

}
