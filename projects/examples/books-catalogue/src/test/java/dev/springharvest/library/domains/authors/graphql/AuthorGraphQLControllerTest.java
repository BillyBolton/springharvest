package dev.springharvest.library.domains.authors.graphql;

import dev.springharvest.expressions.builders.TypedQueryBuilder;
import dev.springharvest.expressions.helpers.Operation;
import dev.springharvest.library.config.LiquibaseTestExecutionListener;
import dev.springharvest.library.config.TestComponentScanningConfig;
import dev.springharvest.library.config.TestContainerConfig;
import dev.springharvest.library.domains.authors.models.entities.AuthorEntity;
import dev.springharvest.library.domains.authors.services.AuthorCrudService;
import dev.springharvest.shared.constants.DataPaging;
import dev.springharvest.shared.constants.PageData;
import dev.springharvest.shared.constants.Sort;
import dev.springharvest.shared.constants.SortDirection;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(value = {TestComponentScanningConfig.class, TestContainerConfig.class})
@TestExecutionListeners(
        listeners = {DependencyInjectionTestExecutionListener.class, LiquibaseTestExecutionListener.class},
        mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
@TestPropertySource(locations = "classpath:application.properties")
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class AuthorGraphQLControllerTest {

    @Autowired
    private AuthorGraphQLController authorGraphQLController;

    @MockBean
    private AuthorCrudService authorCrudService;

    @MockBean
    private TypedQueryBuilder typedQueryBuilder;

    @Test
    void searchAuthorsReturnsResultsForPartialNameMatch() {
        Map<String, Object> filter = Map.of("name", "John");
        Map<String, Object> clause = Map.of();
        DataPaging paging = new DataPaging(0, 10, Collections.singletonList(new Sort("name", SortDirection.ASC)));
        DataFetchingEnvironment environment = mock(DataFetchingEnvironment.class);

        DataFetchingFieldSelectionSet selectionSet = mock(DataFetchingFieldSelectionSet.class);
        when(environment.getSelectionSet()).thenReturn(selectionSet);
        when(selectionSet.getFields()).thenReturn(Collections.emptyList());

        List<AuthorEntity> authors = List.of(
                AuthorEntity.builder().name("John Doe").build(),
                AuthorEntity.builder().name("John Smith").build()
        );
        PageData<AuthorEntity> authorPage = new PageData<>(authors, 2, 1, 10, 1, 10);

        when(typedQueryBuilder.parseFilterExpression(eq(Operation.SEARCH), eq(AuthorEntity.class), eq(UUID.class), eq(filter), eq(clause), anyList(), anyMap(), isNull(), eq(paging)))
                .thenReturn(authorPage);

        PageData<AuthorEntity> result = authorGraphQLController.searchAuthors(filter, clause, paging, environment);

        assertEquals(authorPage.getTotal(), result.getTotal());
        assertEquals(authorPage.getData(), result.getData());
    }

    @Test
    void searchAuthorsReturnsEmptyResults() {
        Map<String, Object> filter = Map.of("name", "NonExistentAuthor");
        Map<String, Object> clause = Map.of();
        DataPaging paging = new DataPaging(0, 10, Collections.singletonList(new Sort("name", SortDirection.ASC)));
        DataFetchingEnvironment environment = mock(DataFetchingEnvironment.class);

        DataFetchingFieldSelectionSet selectionSet = mock(DataFetchingFieldSelectionSet.class);
        when(environment.getSelectionSet()).thenReturn(selectionSet);
        when(selectionSet.getFields()).thenReturn(Collections.emptyList());

        PageData<AuthorEntity> expectedPageData = new PageData<>(List.of(), 0, 0, 10, 1, 10);

        when(typedQueryBuilder.parseFilterExpression(eq(Operation.SEARCH), eq(AuthorEntity.class), eq(UUID.class), eq(filter), eq(clause), anyList(), anyMap(), isNull(), eq(paging)))
                .thenReturn(expectedPageData);

        PageData<AuthorEntity> result = authorGraphQLController.searchAuthors(filter, clause, paging, environment);

        assertEquals(expectedPageData, result);
    }

    @Test
    void searchAuthorsReturnsResultsForExactNameMatch() {
        Map<String, Object> filter = Map.of("name", "John Doe");
        Map<String, Object> clause = Map.of();
        DataPaging paging = new DataPaging(0, 10, Collections.singletonList(new Sort("name", SortDirection.ASC)));
        DataFetchingEnvironment environment = mock(DataFetchingEnvironment.class);

        DataFetchingFieldSelectionSet selectionSet = mock(DataFetchingFieldSelectionSet.class);
        when(environment.getSelectionSet()).thenReturn(selectionSet);
        when(selectionSet.getFields()).thenReturn(Collections.emptyList());

        List<AuthorEntity> authors = List.of(
                AuthorEntity.builder().name("John Doe").build()
        );
        PageData<AuthorEntity> authorPage = new PageData<>(authors, 1, 1, 10, 1, 10);

        when(typedQueryBuilder.parseFilterExpression(eq(Operation.SEARCH), eq(AuthorEntity.class), eq(UUID.class), eq(filter), eq(clause), anyList(), anyMap(), isNull(), eq(paging)))
                .thenReturn(authorPage);

        PageData<AuthorEntity> result = authorGraphQLController.searchAuthors(filter, clause, paging, environment);

        assertEquals(authorPage.getTotal(), result.getTotal());
        assertEquals(authorPage.getData(), result.getData());
    }

    @Test
    void countAuthorsReturnsExpectedCount() {
        Map<String, Object> filter = Map.of("name", "John Doe");
        Map<String, Object> clause = Map.of();
        List<String> fields = List.of("name");

        long expectedCount = 1L;

        when(typedQueryBuilder.parseFilterExpression(eq(Operation.COUNT), eq(AuthorEntity.class), eq(UUID.class), eq(filter), eq(clause), anyList(), isNull(), isNull(), isNull()))
                .thenReturn(expectedCount);

        long result = authorGraphQLController.countAuthors(filter, clause, fields);

        assertEquals(expectedCount, result);
    }

    @Test
    void countAuthorsReturnsZeroForNoMatches() {
        Map<String, Object> filter = Map.of("name", "NonExistentAuthor");
        Map<String, Object> clause = Map.of();
        List<String> fields = List.of("name");

        long expectedCount = 0L;

        when(typedQueryBuilder.parseFilterExpression(eq(Operation.COUNT), eq(AuthorEntity.class), eq(UUID.class), eq(filter), eq(clause), anyList(), isNull(), isNull(), isNull()))
                .thenReturn(expectedCount);

        long result = authorGraphQLController.countAuthors(filter, clause, fields);

        assertEquals(expectedCount, result);
    }

    @Test
    void countAuthorsReturnsCountForMultipleCriteria() {
        Map<String, Object> filter = Map.of("name", "John", "country", "USA");
        Map<String, Object> clause = Map.of();
        List<String> fields = List.of("name", "country");

        long expectedCount = 2L;

        when(typedQueryBuilder.parseFilterExpression(eq(Operation.COUNT), eq(AuthorEntity.class), eq(UUID.class), eq(filter), eq(clause), anyList(), isNull(), isNull(), isNull()))
                .thenReturn(expectedCount);

        long result = authorGraphQLController.countAuthors(filter, clause, fields);

        assertEquals(expectedCount, result);
    }
}