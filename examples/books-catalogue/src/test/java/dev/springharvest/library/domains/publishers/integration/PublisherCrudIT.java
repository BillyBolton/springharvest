package dev.springharvest.library.domains.publishers.integration;


import static dev.springharvest.testing.constants.TestConstants.Messages.CONTEXT_LOADS;

import dev.springharvest.library.config.TestComponentScanningConfig;
import dev.springharvest.library.config.TestContainerConfig;
import dev.springharvest.library.domains.publishers.integration.utils.clients.PublisherCrudClient;
import dev.springharvest.library.domains.publishers.integration.utils.factories.PublisherModelFactory;
import dev.springharvest.library.domains.publishers.models.dtos.PublisherDTO;
import dev.springharvest.testing.integration.crud.tests.AbstractCrudIT;
import dev.springharvest.testing.integration.shared.listeners.LiquibaseTestExecutionListener;
import java.util.UUID;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(value = {TestComponentScanningConfig.class, TestContainerConfig.class})
@TestExecutionListeners(
    listeners = {DependencyInjectionTestExecutionListener.class, LiquibaseTestExecutionListener.class},
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
@TestPropertySource(locations = "classpath:application.properties")
class PublisherCrudIT extends AbstractCrudIT<PublisherDTO, UUID> {

  @Autowired
  public PublisherCrudIT(PublisherCrudClient client, PublisherModelFactory modelFactory) {
    super(client, modelFactory);
  }

  @Test
  void contextLoads() {
    Assertions.assertTrue(true, CONTEXT_LOADS);
  }

  @Override
  public void softlyAssert(SoftAssertions softly, PublisherDTO actual, PublisherDTO expected) {
    super.softlyAssert(softly, actual, expected);
    softly.assertThat(actual.getId()).isEqualTo(expected.getId());
    softly.assertThat(actual.getName()).isEqualToIgnoringCase(expected.getName());
  }

}
