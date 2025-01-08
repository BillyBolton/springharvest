package dev.springharvest.library.domains.books.graphql;

import dev.springharvest.library.config.LiquibaseTestExecutionListener;
import dev.springharvest.library.config.TestComponentScanningConfig;
import dev.springharvest.library.config.TestContainerConfig;
import dev.springharvest.library.domains.authors.models.entities.AuthorEntity;
import dev.springharvest.library.domains.books.services.BookCrudService;
import dev.springharvest.library.domains.pet.models.entities.PetEntity;
import dev.springharvest.shared.constants.*;
import jakarta.persistence.criteria.JoinType;
import org.jetbrains.annotations.NotNull;
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

import graphql.schema.DataFetchingEnvironment;
import graphql.schema.DataFetchingFieldSelectionSet;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.junit.jupiter.api.Assertions.assertEquals;

import dev.springharvest.library.domains.books.models.entities.BookEntity;
import dev.springharvest.expressions.helpers.Operation;
import dev.springharvest.expressions.builders.TypedQueryBuilder;

import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(value = {TestComponentScanningConfig.class, TestContainerConfig.class})
@TestExecutionListeners(
        listeners = {DependencyInjectionTestExecutionListener.class, LiquibaseTestExecutionListener.class},
        mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
@TestPropertySource(locations = "classpath:application.properties")
@ActiveProfiles("test")
@AutoConfigureMockMvc
class BookGraphQLControllerTest {

    @Autowired
    private BookGraphQLController bookGraphQLController;

    @MockBean
    private BookCrudService booksCrudService;

    @MockBean
    private TypedQueryBuilder typedQueryBuilder;

    @Test
    void searchBooksReturnsExpectedResults() {
        Map<String, Object> filter = new LinkedHashMap<>();
        Map<String, Object> equals = new LinkedHashMap<>();
        equals.put("containsic", "java");
        filter.put("title", equals);
        Map<String, Object> clause = Map.of("distinct", "true");
        Map<String, JoinType> join = Map.of("Book.author", JoinType.LEFT);
        DataPaging paging = new DataPaging(0, 10, Collections.singletonList(new Sort("title", SortDirection.ASC)));
        DataFetchingEnvironment environment = mock(DataFetchingEnvironment.class);

        DataFetchingFieldSelectionSet selectionSet = mock(DataFetchingFieldSelectionSet.class);
        when(environment.getSelectionSet()).thenReturn(selectionSet);
        when(selectionSet.getFields()).thenReturn(Collections.emptyList());

        PetEntity pet = PetEntity.builder().name("Jojo").build();
        AuthorEntity author = AuthorEntity.builder().name("Joshua Bloch").pet(pet).build();
        List<BookEntity> books = List.of(BookEntity.builder().title("Effective Java").genre("Science").author(author).build());
        PageData<BookEntity> expectedPageData = new PageData<>(books, 1, 1, 10, 1, 10);

        when(typedQueryBuilder.parseFilterExpression(eq(Operation.SEARCH), eq(BookEntity.class), eq(UUID.class), eq(filter), eq(clause), anyList(), anyMap(), isNull(), eq(paging)))
                .thenReturn(expectedPageData);

        PageData<BookEntity> result = bookGraphQLController.searchBooks(filter, clause, paging, environment);
        System.out.println(result);

        assertEquals(expectedPageData, result);
    }

    @Test
    void searchBooksReturnsEmptyResults() {
        Map<String, Object> filter = Map.of("title", "NonExistentBook");
        Map<String, Object> clause = Map.of();
        DataPaging paging = new DataPaging(0, 10, Collections.singletonList(new Sort("title", SortDirection.ASC)));
        DataFetchingEnvironment environment = mock(DataFetchingEnvironment.class);

        DataFetchingFieldSelectionSet selectionSet = mock(DataFetchingFieldSelectionSet.class);
        when(environment.getSelectionSet()).thenReturn(selectionSet);
        when(selectionSet.getFields()).thenReturn(Collections.emptyList());

        PageData<BookEntity> expectedPageData = new PageData<>(List.of(), 0, 0, 10, 1, 10);

        when(typedQueryBuilder.parseFilterExpression(eq(Operation.SEARCH), eq(BookEntity.class), eq(UUID.class), eq(filter), eq(clause), anyList(), anyMap(), isNull(), eq(paging)))
                .thenReturn(expectedPageData);

        PageData<BookEntity> result = bookGraphQLController.searchBooks(filter, clause, paging, environment);

        assertEquals(expectedPageData, result);
    }

    @Test
    void complexBooksSearchReturnsExpectedResults() {
        Map<String, Object> filter = new LinkedHashMap<>();
        Map<String, Object> equals = new LinkedHashMap<>();
        equals.put("containsic", "java");
        filter.put("title", equals);
        Map<String, Object> clause = new LinkedHashMap<>();
        List<String> fields = new ArrayList<>();
        fields.add("Book_title");
        fields.add("Book_genre");
        List<String> formattedFields = new ArrayList<>();
        formattedFields.add("Book.title");
        formattedFields.add("Book.genre");
        DataPaging paging = new DataPaging(1, 10, Collections.singletonList(new Sort("Book.title", SortDirection.ASC)));
        List<String> countFields = new ArrayList<>();
        countFields.add("Book_genre");
        List<String> formattedCountFields = new ArrayList<>();
        formattedCountFields.add("Book.genre");
        List<String> formattedGroupByFields = new ArrayList<>();
        formattedGroupByFields.add("Book.title");
        formattedGroupByFields.add("Book.genre");
        Aggregates aggregates = new Aggregates(countFields, null, null, null, null, null);
        Aggregates formattedAggregates = new Aggregates(formattedCountFields, null, null, null, null, formattedGroupByFields);

        Object resultsList = getObjects();

        when(typedQueryBuilder.parseFilterExpression(eq(Operation.SEARCH), eq(BookEntity.class), eq(UUID.class), eq(filter), eq(clause), eq(formattedFields), eq(null), eq(formattedAggregates), eq(paging)))
                .thenReturn(resultsList);

        Object result = bookGraphQLController.complexBooksSearch(filter, clause, fields, paging, aggregates);
        System.out.println(result);

        assertEquals(resultsList, result);
    }

    @NotNull
    private static List<Object> getObjects() {
        Map<String, Object> bookDetails = new HashMap<>();
        bookDetails.put("title", "Effective Java");
        bookDetails.put("genre", "Science");
        bookDetails.put("count_genre", 1);

        // Second map with nested map
        Map<String, Object> pagingDetails = new HashMap<>();
        Map<String, Object> _paging = new HashMap<>();
        _paging.put("size", 10);
        _paging.put("currentPageCount", 1);
        _paging.put("totalPages", 1);
        _paging.put("page", 1);
        _paging.put("totalCount", 1);

        pagingDetails.put("paging", _paging);

        // Add maps to the list
        List<Object> resultsList = new ArrayList<>();
        resultsList.add(bookDetails);
        resultsList.add(pagingDetails);
        return resultsList;
    }

    @Test
    void complexBooksSearchReturnsEmptyResults() {
        Map<String, Object> filter = new LinkedHashMap<>();
        Map<String, Object> equals = new LinkedHashMap<>();
        equals.put("containsic", "java");
        filter.put("title", equals);
        Map<String, Object> clause = Map.of();
        List<String> fields = List.of("Book_id", "Book_title");
        List<String> formattedFields = new ArrayList<>();
        formattedFields.add("Book.id");
        formattedFields.add("Book.title");
        DataPaging paging = new DataPaging(1, 10, Collections.singletonList(new Sort("Book.title", SortDirection.ASC)));
        Aggregates aggregates = new Aggregates(List.of("Book_genre"), null, null, null, null, null);
        List<String> formattedCountFields = new ArrayList<>();
        formattedCountFields.add("Book.genre");
        List<String> formattedGroupByFields = new ArrayList<>();
        formattedGroupByFields.add("Book.id");
        formattedGroupByFields.add("Book.title");
        Aggregates formattedAggregates = new Aggregates(formattedCountFields, null, null, null, null, formattedGroupByFields);

        Object expectedPageData = List.of();

        when(typedQueryBuilder.parseFilterExpression(eq(Operation.SEARCH), eq(BookEntity.class), eq(UUID.class), eq(filter), eq(clause), eq(formattedFields), eq(null), eq(formattedAggregates), eq(paging)))
                .thenReturn(expectedPageData);

        Object result = bookGraphQLController.complexBooksSearch(filter, clause, fields, paging, aggregates);

        assertEquals(expectedPageData, result);
    }

    @Test
    void countBooksReturnsExpectedCount() {
        Map<String, Object> filter = Map.of("author", "Joshua Bloch");
        Map<String, Object> clause = Map.of();
        List<String> fields = List.of("author");

        long expectedCount = 3L;

        when(typedQueryBuilder.parseFilterExpression(eq(Operation.COUNT), eq(BookEntity.class), eq(UUID.class), eq(filter), eq(clause), anyList(), isNull(), isNull(), isNull()))
                .thenReturn(expectedCount);

        long result = bookGraphQLController.countBooks(filter, clause, fields);

        assertEquals(expectedCount, result);
    }

    @Test
    void countBooksReturnsZeroForNoMatches() {
        Map<String, Object> filter = Map.of("author", "NonExistentAuthor");
        Map<String, Object> clause = Map.of();
        List<String> fields = List.of("author");

        long expectedCount = 0L;

        when(typedQueryBuilder.parseFilterExpression(eq(Operation.COUNT), eq(BookEntity.class), eq(UUID.class), eq(filter), eq(clause), anyList(), isNull(), isNull(), isNull()))
                .thenReturn(expectedCount);

        long result = bookGraphQLController.countBooks(filter, clause, fields);

        assertEquals(expectedCount, result);
    }
}
