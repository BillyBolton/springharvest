package dev.springharvest.library.domains.authors.integration;

import dev.springharvest.library.config.TestComponentScanningConfig;
import dev.springharvest.library.config.TestContainerConfig;
import dev.springharvest.library.domains.authors.integration.utils.clients.AuthorsSearchClient;
import dev.springharvest.library.domains.authors.integration.utils.helpers.AuthorsSearchModelTestFactory;
import dev.springharvest.library.domains.authors.models.dtos.AuthorDTO;
import dev.springharvest.library.domains.authors.models.queries.AuthorFilterRequestDTO;
import dev.springharvest.testing.constants.TestConstants;
import dev.springharvest.testing.integration.search.tests.AbstractSearchIT;
import dev.springharvest.testing.integration.shared.listeners.LiquibaseTestExecutionListener;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Import(value = {TestComponentScanningConfig.class, TestContainerConfig.class})
@TestExecutionListeners(
    listeners = {DependencyInjectionTestExecutionListener.class, LiquibaseTestExecutionListener.class},
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
@TestPropertySource(locations = "classpath:application.properties")
class AuthorsSearchIT extends AbstractSearchIT<AuthorDTO, UUID, AuthorFilterRequestDTO> {

  @Autowired
  public AuthorsSearchIT(AuthorsSearchClient searchClient, AuthorsSearchModelTestFactory testHelper) {
    super(searchClient, testHelper);
  }

  @Test
  void contextLoads() {
    Assertions.assertTrue(true, TestConstants.Messages.CONTEXT_LOADS);
  }

}
