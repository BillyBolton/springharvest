package dev.springharvest.library.domains.authors.integration.crud;

import dev.springharvest.library.config.TestComponentScanningConfig;
import dev.springharvest.library.config.TestContainerConfig;
import dev.springharvest.library.domains.authors.integration.utils.clients.AuthorsCrudClient;
import dev.springharvest.library.domains.authors.integration.utils.helpers.AuthorsModelFactoryImpl;
import dev.springharvest.shared.domains.authors.models.dtos.AuthorDTO;
import dev.springharvest.shared.domains.authors.models.entities.AuthorEntity;
import dev.springharvest.testing.integration.crud.tests.AbstractCrudIT;
import dev.springharvest.testing.integration.shared.listeners.LiquibaseTestExecutionListener;
import java.util.UUID;
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
class AuthorsCrudIT extends AbstractCrudIT<AuthorDTO, AuthorEntity, UUID> {

  @Autowired
  public AuthorsCrudIT(AuthorsCrudClient client, AuthorsModelFactoryImpl modelFactory) {
    super(client, modelFactory);
  }

}
