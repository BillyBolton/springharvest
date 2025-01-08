package dev.springharvest.library.domains.publishers.graphql;

import dev.springharvest.library.config.LiquibaseTestExecutionListener;
import dev.springharvest.library.config.TestComponentScanningConfig;
import dev.springharvest.library.config.TestContainerConfig;
import dev.springharvest.shared.constants.DataPaging;
import dev.springharvest.shared.constants.Sort;
import dev.springharvest.shared.constants.SortDirection;
import dev.springharvest.library.domains.publishers.models.entities.PublisherEntity;
import dev.springharvest.shared.constants.PageData;
import dev.springharvest.library.domains.publishers.services.PublisherCrudService;
import dev.springharvest.expressions.helpers.Operation;
import dev.springharvest.expressions.builders.TypedQueryBuilder;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.DataFetchingFieldSelectionSet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.*;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.isNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(value = {TestComponentScanningConfig.class, TestContainerConfig.class})
@TestExecutionListeners(
        listeners = {DependencyInjectionTestExecutionListener.class, LiquibaseTestExecutionListener.class},
        mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
@TestPropertySource(locations = "classpath:application.properties")
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class PublisherGraphQLControllerTest {
    @Autowired
    private PublisherGraphQLController publisherGraphQLController;

    @MockBean
    private PublisherCrudService publisherQueryCrudService;

    @MockBean
    private TypedQueryBuilder typedQueryBuilder;

    @Test
    void searchPublishersReturnsResultsForPartialNameMatch() {
        Map<String, Object> filter = Map.of("name", "O'");
        Map<String, Object> clause = Map.of();
        DataPaging paging = new DataPaging(0, 10, Collections.singletonList(new Sort("name", SortDirection.ASC)));
        DataFetchingEnvironment environment = mock(DataFetchingEnvironment.class);

        DataFetchingFieldSelectionSet selectionSet = mock(DataFetchingFieldSelectionSet.class);
        when(environment.getSelectionSet()).thenReturn(selectionSet);
        when(selectionSet.getFields()).thenReturn(Collections.emptyList());

        List<PublisherEntity> publishers = List.of(new PublisherEntity("O'Reilly"), new PublisherEntity("O'Neil"));
        PageData<PublisherEntity> publisherPage = new PageData<>(publishers, 2, 1, 10, 1, 10);

        when(typedQueryBuilder.parseFilterExpression(eq(Operation.SEARCH), eq(PublisherEntity.class), eq(UUID.class), eq(filter), eq(clause), anyList(), anyMap(), isNull(), eq(paging)))
                .thenReturn(publisherPage);

        PageData<PublisherEntity> result = publisherGraphQLController.searchPublishers(filter, clause, paging, environment);

        assertEquals(publisherPage.getTotal(), result.getTotal());
        assertEquals(publisherPage.getData(), result.getData());
    }
    @Test
    void searchPublishersReturnsResultsForEmptyFilter() {
        Map<String, Object> filter = Map.of();
        Map<String, Object> clause = Map.of();
        DataPaging paging = new DataPaging(0, 10, Collections.singletonList(new Sort("name", SortDirection.ASC)));
        DataFetchingEnvironment environment = mock(DataFetchingEnvironment.class);

        DataFetchingFieldSelectionSet selectionSet = mock(DataFetchingFieldSelectionSet.class);
        when(environment.getSelectionSet()).thenReturn(selectionSet);
        when(selectionSet.getFields()).thenReturn(Collections.emptyList());

        List<PublisherEntity> publishers = List.of(new PublisherEntity("O'Reilly"), new PublisherEntity("Penguin"));
        PageData<PublisherEntity> publisherPage = new PageData<>(publishers, 2, 1, 10, 1, 10);

        when(typedQueryBuilder.parseFilterExpression(eq(Operation.SEARCH), eq(PublisherEntity.class), eq(UUID.class), eq(filter), eq(clause), anyList(), anyMap(), isNull(), eq(paging)))
                .thenReturn(publisherPage);

        PageData<PublisherEntity> result = publisherGraphQLController.searchPublishers(filter, clause, paging, environment);

        assertEquals(publisherPage.getTotal(), result.getTotal());
        assertEquals(publisherPage.getData(), result.getData());
    }

    @Test
    void searchPublishersReturnsResultsForSpecificLocation() {
        Map<String, Object> filter = Map.of("location", "USA");
        Map<String, Object> clause = Map.of();
        DataPaging paging = new DataPaging(0, 10, Collections.singletonList(new Sort("name", SortDirection.ASC)));
        DataFetchingEnvironment environment = mock(DataFetchingEnvironment.class);

        DataFetchingFieldSelectionSet selectionSet = mock(DataFetchingFieldSelectionSet.class);
        when(environment.getSelectionSet()).thenReturn(selectionSet);
        when(selectionSet.getFields()).thenReturn(Collections.emptyList());

        List<PublisherEntity> publishers = List.of(new PublisherEntity("O'Reilly"), new PublisherEntity("Penguin USA"));
        PageData<PublisherEntity> publisherPage = new PageData<>(publishers, 2, 1, 10, 1, 10);

        when(typedQueryBuilder.parseFilterExpression(eq(Operation.SEARCH), eq(PublisherEntity.class), eq(UUID.class), eq(filter), eq(clause), anyList(), anyMap(), isNull(), eq(paging)))
                .thenReturn(publisherPage);

        PageData<PublisherEntity> result = publisherGraphQLController.searchPublishers(filter, clause, paging, environment);

        assertEquals(publisherPage.getTotal(), result.getTotal());
        assertEquals(publisherPage.getData(), result.getData());
    }

    @Test
    void searchPublishersReturnsResultsForSpecificType() {
        Map<String, Object> filter = Map.of("type", "Tech");
        Map<String, Object> clause = Map.of();
        DataPaging paging = new DataPaging(0, 10, Collections.singletonList(new Sort("name", SortDirection.ASC)));
        DataFetchingEnvironment environment = mock(DataFetchingEnvironment.class);

        DataFetchingFieldSelectionSet selectionSet = mock(DataFetchingFieldSelectionSet.class);
        when(environment.getSelectionSet()).thenReturn(selectionSet);
        when(selectionSet.getFields()).thenReturn(Collections.emptyList());

        List<PublisherEntity> publishers = List.of(new PublisherEntity("O'Reilly"), new PublisherEntity("TechBooks"));
        PageData<PublisherEntity> publisherPage = new PageData<>(publishers, 2, 1, 10, 1, 10);

        when(typedQueryBuilder.parseFilterExpression(eq(Operation.SEARCH), eq(PublisherEntity.class), eq(UUID.class), eq(filter), eq(clause), anyList(), anyMap(), isNull(), eq(paging)))
                .thenReturn(publisherPage);

        PageData<PublisherEntity> result = publisherGraphQLController.searchPublishers(filter, clause, paging, environment);

        assertEquals(publisherPage.getTotal(), result.getTotal());
        assertEquals(publisherPage.getData(), result.getData());
    }

    @Test
    void countPublishersReturnsCountForMultipleFields() {
        Map<String, Object> filter = Map.of("type", "Tech", "location", "USA");
        Map<String, Object> clause = Map.of();
        List<String> fields = List.of("type", "location");

        when(typedQueryBuilder.parseFilterExpression(eq(Operation.COUNT), eq(PublisherEntity.class), eq(UUID.class), eq(filter), eq(clause), anyList(), isNull(), isNull(), isNull()))
                .thenReturn(3L);

        long result = publisherGraphQLController.countPublishers(filter, clause, fields);

        assertEquals(3L, result);
    }

    @Test
    void countPublishersReturnsZeroForEmptyFilter() {
        Map<String, Object> filter = Map.of();
        Map<String, Object> clause = Map.of();
        List<String> fields = List.of();

        when(typedQueryBuilder.parseFilterExpression(eq(Operation.COUNT), eq(PublisherEntity.class), eq(UUID.class), eq(filter), eq(clause), anyList(), isNull(), isNull(), isNull()))
                .thenReturn(0L);

        long result = publisherGraphQLController.countPublishers(filter, clause, fields);

        assertEquals(0L, result);
    }

    @Test
    void countPublishersReturnsExpectedCount() {
        Map<String, Object> filter = Map.of("name", "O'Reilly");
        Map<String, Object> clause = Map.of();
        List<String> fields = List.of("name");

        long expectedCount = 1L;

        when(typedQueryBuilder.parseFilterExpression(eq(Operation.COUNT), eq(PublisherEntity.class), eq(UUID.class), eq(filter), eq(clause), anyList(), isNull(), isNull(), isNull()))
                .thenReturn(expectedCount);

        long result = publisherGraphQLController.countPublishers(filter, clause, fields);

        assertEquals(expectedCount, result);
    }

    @Test
    void countPublishersReturnsZeroForNoMatches() {
        Map<String, Object> filter = Map.of("name", "NonExistentPublisher");
        Map<String, Object> clause = Map.of();
        List<String> fields = List.of("name");

        long expectedCount = 0L;

        when(typedQueryBuilder.parseFilterExpression(eq(Operation.COUNT), eq(PublisherEntity.class), eq(UUID.class), eq(filter), eq(clause), anyList(), isNull(), isNull(), isNull()))
                .thenReturn(expectedCount);

        long result = publisherGraphQLController.countPublishers(filter, clause, fields);

        assertEquals(expectedCount, result);
    }

}