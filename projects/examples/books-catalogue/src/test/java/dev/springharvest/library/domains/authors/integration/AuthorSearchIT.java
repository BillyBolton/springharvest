package dev.springharvest.library.domains.authors.integration;

import dev.springharvest.library.config.LiquibaseTestExecutionListener;
import dev.springharvest.library.config.TestComponentScanningConfig;
import dev.springharvest.library.config.TestContainerConfig;
import dev.springharvest.library.domains.authors.integration.utils.clients.AuthorSearchClient;
import dev.springharvest.library.domains.authors.integration.utils.factories.AuthorSearchModelFactory;
import dev.springharvest.library.domains.authors.models.dtos.AuthorDTO;
import dev.springharvest.library.domains.authors.models.queries.AuthorFilterRequestDTO;
import dev.springharvest.testing.constants.TestConstants;
import dev.springharvest.testing.domains.integration.search.tests.AbstractSearchIT;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(value = {TestComponentScanningConfig.class, TestContainerConfig.class})
@TestExecutionListeners(
    listeners = {DependencyInjectionTestExecutionListener.class, LiquibaseTestExecutionListener.class},
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
@TestPropertySource(locations = "classpath:application.properties")
@ActiveProfiles("test")
class AuthorSearchIT extends AbstractSearchIT<AuthorDTO, UUID, AuthorFilterRequestDTO> {

  @Autowired
  public AuthorSearchIT(AuthorSearchClient searchClient, AuthorSearchModelFactory modelFactory) {
    super(searchClient, modelFactory);
  }

  @Test
  void contextLoads() {
    Assertions.assertTrue(true, TestConstants.Messages.CONTEXT_LOADS);
  }

}
