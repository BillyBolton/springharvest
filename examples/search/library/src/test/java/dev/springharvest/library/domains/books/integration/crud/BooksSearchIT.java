package dev.springharvest.library.domains.books.integration.crud;

import dev.springharvest.library.config.TestComponentScanningConfig;
import dev.springharvest.library.config.TestContainerConfig;
import dev.springharvest.library.domains.books.integration.utils.helpers.BooksSearchTestHelper;
import dev.springharvest.library.domains.books.models.queries.BookFilterRequestDTO;
import dev.springharvest.shared.domains.books.models.dtos.BookDTO;
import dev.springharvest.shared.domains.books.models.entities.BookEntity;
import dev.springharvest.testing.constants.TestConstants;
import dev.springharvest.testing.integration.search.AbstractSearchIT;
import dev.springharvest.testing.integration.shared.clients.RestClientImpl;
import dev.springharvest.testing.integration.shared.listeners.LiquibaseTestExecutionListener;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Import(value = {TestComponentScanningConfig.class, TestContainerConfig.class})
@TestExecutionListeners(
        listeners = {DependencyInjectionTestExecutionListener.class, LiquibaseTestExecutionListener.class},
        mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
@TestPropertySource(locations = "classpath:application.properties")
class BooksSearchIT extends AbstractSearchIT<BookDTO, BookEntity, UUID, BookFilterRequestDTO> {

    @Autowired
    public BooksSearchIT(RestClientImpl clientHelper, BooksSearchTestHelper testHelper) {
        super(clientHelper, testHelper);
    }

    @Test
    void contextLoads() {
        Assertions.assertTrue(true, TestConstants.Messages.CONTEXT_LOADS);
    }

}
