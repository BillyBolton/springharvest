package dev.springharvest.testing.domains.integration.shared.tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.web.server.LocalServerPort;

public abstract class AbstractBaseIT implements IBaseIT {

  @LocalServerPort
  private Integer port;

  @BeforeEach
  void setUp() {
    RestAssured.baseURI = "http://localhost:" + port;
  }

}
