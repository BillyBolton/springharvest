package dev.springharvest.crud.domains.base.graphql;

import dev.springharvest.shared.constants.*;
import graphql.schema.DataFetchingFieldSelectionSet;
import graphql.schema.SelectedField;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.criteria.JoinType;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import dev.springharvest.expressions.builders.TypedQueryBuilder;
import dev.springharvest.shared.domains.base.models.entities.BaseEntity;
import dev.springharvest.expressions.helpers.Operation;
import graphql.schema.DataFetchingEnvironment;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.Serializable;
import java.util.*;

public class AbstractGraphQLCrudControllerTest {

    private class Author extends BaseEntity<UUID>{
        public String name;
        public Pet pet;

        public Author(String name, Pet pet) {
            this.name = name;
            this.pet = pet;
        }
    }
    private class Pet extends BaseEntity<UUID>{
        public String name;
        public Pet(String name) {
            this.name = name;
        }
    }

    @Mock
    private TypedQueryBuilder typedQueryBuilder;
    private Class<Author> entityClass = Author.class;
    private Class<UUID> keyClass = UUID.class;


    @Autowired
    private AbstractGraphQLCrudController<Author, UUID> abstractGraphQLCrudController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        abstractGraphQLCrudController = new AbstractGraphQLCrudController<>(Author.class, UUID.class) {};
        abstractGraphQLCrudController.setTypedQueryBuilder(typedQueryBuilder);
    }

    @Test
    void searchWithValidFilter() {
        DataFetchingEnvironment environment = mock(DataFetchingEnvironment.class);
        DataFetchingFieldSelectionSet selectionSet = mock(DataFetchingFieldSelectionSet.class);
        when(environment.getSelectionSet()).thenReturn(selectionSet);
        when(selectionSet.getFields()).thenReturn(Collections.emptyList());
        Map<String, Object> filter = new HashMap<>();
        Map<String, Object> clause = new HashMap<>();
        Map<String, JoinType> joins = new HashMap<>();
        List<String> fields = new ArrayList<>();
        DataPaging paging = new DataPaging(1, 10, new ArrayList<>());
        PageData<Author> expectedPageData = new PageData<>(new ArrayList<>(), 1, 10, 0L, 1, 10);

        when(typedQueryBuilder.parseFilterExpression(
                eq(Operation.SEARCH),
                eq(entityClass),
                eq(keyClass),
                eq(filter),
                eq(clause),
                eq(fields),
                eq(joins),
                eq(null),
                eq(paging)
        )).thenReturn(expectedPageData);

        PageData<Author> result = abstractGraphQLCrudController.search(filter, clause, paging, environment);

        assertEquals(expectedPageData, result);
    }

    @Test
    void searchWithNullFilter() {
        DataFetchingEnvironment environment = mock(DataFetchingEnvironment.class);
        when(environment.getSelectionSet()).thenReturn(mock(DataFetchingFieldSelectionSet.class));
        Map<String, Object> filter = null;
        Map<String, Object> clause = new HashMap<>();
        clause.put("distinct", "true");
        DataPaging paging = new DataPaging(1, 10, new ArrayList<>());

        when(typedQueryBuilder.parseFilterExpression(eq(Operation.SEARCH), eq(entityClass), eq(keyClass), eq(filter), eq(clause), anyList(), anyMap(), any(), eq(paging)))
                .thenReturn(null);

        PageData<Author> result = abstractGraphQLCrudController.search(filter, clause, paging, environment);

        assertEquals(null, result);
    }

    @Test
    void searchWithValidJoins() {
        DataFetchingEnvironment environment = mock(DataFetchingEnvironment.class);
        DataFetchingFieldSelectionSet selectionSet = mock(DataFetchingFieldSelectionSet.class);
        when(environment.getSelectionSet()).thenReturn(selectionSet);
        when(selectionSet.getFields()).thenReturn(Collections.emptyList());
        Map<String, Object> filter = new LinkedHashMap<>();
        Map<String, Object> petFilter = new LinkedHashMap<>();
        Map<String, Object> equals = new LinkedHashMap<>();
        equals.put("containsic", "jo");
        petFilter.put("name", equals);
        filter.put("pet", petFilter);
        Map<String, Object> clause = Map.of("distinct", "true");
        Map<String, JoinType> joins = new LinkedHashMap<>();
        joins.put("pet", JoinType.RIGHT);
        DataPaging paging = new DataPaging(0, 10, new ArrayList<>());
        Pet pet = new Pet("Jojo");
        Author author = new Author("Joshua Bloch", pet);
        Pet pet2 = new Pet("Rocky");
        Author author2 = new Author("Joshua Bloch", pet2);
        PageData<Author> expectedPageData = new PageData<>(List.of(author), 1, 10, 0L, 1, 10);

        when(typedQueryBuilder.parseFilterExpression(
                eq(Operation.SEARCH),
                eq(entityClass),
                eq(keyClass),
                eq(filter),
                eq(clause),
                anyList(),
                anyMap(),
                eq(null),
                eq(paging)
        )).thenReturn(expectedPageData);

        PageData<Author> result = abstractGraphQLCrudController.search(filter, clause, paging, environment);

        assertEquals(expectedPageData, result);
    }

    @Test
    void searchWithEmptyFilter() {
        DataFetchingEnvironment environment = mock(DataFetchingEnvironment.class);
        when(environment.getSelectionSet()).thenReturn(mock(DataFetchingFieldSelectionSet.class));
        Map<String, Object> filter = new HashMap<>();
        Map<String, Object> clause = new HashMap<>();
        DataPaging paging = new DataPaging(1, 10, new ArrayList<>());
        PageData<Author> expectedPageData = new PageData<>(new ArrayList<>(), 1, 10, 0L, 1, 10);

        when(typedQueryBuilder.parseFilterExpression(eq(Operation.SEARCH), eq(entityClass), eq(keyClass), eq(filter), eq(clause), anyList(), anyMap(), any(), eq(paging)))
                .thenReturn(expectedPageData);

        PageData<Author> result = abstractGraphQLCrudController.search(filter, clause, paging, environment);

        assertNotNull(result);
        assertEquals(expectedPageData, result);
    }

    @Test
    void searchWithAggregatesFilter() {
        Map<String, Object> filter = new LinkedHashMap<>();
        Map<String, Object> petFilter = new LinkedHashMap<>();
        Map<String, Object> equals = new LinkedHashMap<>();
        equals.put("containsic", "jo");
        petFilter.put("name", equals);
        filter.put("pet", petFilter);
        Map<String, Object> clause = Map.of("distinct", "false");
        List<String> fields = new ArrayList<>();
        fields.add("Author.name");
        fields.add("Author.pet.name");
        DataPaging paging = new DataPaging(1, 10, Collections.singletonList(new Sort("Author.pet.name", SortDirection.DESC)));
        List<String> countFields = new ArrayList<>();
        countFields.add("Author_name");
        List<String> formattedCountFields = new ArrayList<>();
        formattedCountFields.add("Author.name");
        List<String> formattedGroupByFields = new ArrayList<>();
        formattedGroupByFields.add("Author.name");
        formattedGroupByFields.add("Author.pet.name");
        Aggregates aggregates = new Aggregates(countFields, null, null, null, null, null);
        Aggregates formattedAggregates = new Aggregates(formattedCountFields, null, null, null, null, formattedGroupByFields);

        Object resultsList = getObjects();

        when(typedQueryBuilder.parseFilterExpression(eq(Operation.SEARCH), eq(entityClass), eq(keyClass), eq(filter), eq(clause), eq(fields), eq(null), eq(formattedAggregates), eq(paging)))
                .thenReturn(resultsList);

        Object result = abstractGraphQLCrudController.search(filter, clause, fields, aggregates, paging);

        assertEquals(resultsList, result);
    }
    @NotNull
    private static List<Object> getObjects() {
        Map<String, Object> bookDetails = new HashMap<>();
        bookDetails.put("name", "Joshua Bloch");
        bookDetails.put("pet.name", "Jojo");
        bookDetails.put("count_name", 1);

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
    void countWithValidFilter() {
        Map<String, Object> filter = new HashMap<>();
        Map<String, Object> equals = new LinkedHashMap<>();
        equals.put("containsic", "ss");
        filter.put("name", equals);
        Map<String, Object> clause = new HashMap<>();
        List<String> fields = List.of("Author_name");
        List<String> formattedFields = List.of("Author.name");
        long expectedCount = 4L;

        when(typedQueryBuilder.parseFilterExpression(eq(Operation.COUNT), eq(entityClass), eq(keyClass), eq(filter), eq(clause), eq(formattedFields), eq(null), eq(null), eq(null)))
                .thenReturn(expectedCount);

        long result = abstractGraphQLCrudController.count(filter, clause, fields);

        assertEquals(expectedCount, result);
    }

    @Test
    void countWithEmptyFilter() {
        Map<String, Object> filter = new HashMap<>();
        Map<String, Object> clause = new HashMap<>();
        List<String> fields = List.of("Author_id");
        List<String> formattedFields = List.of("Author.id");
        long expectedCount = 0L;

        when(typedQueryBuilder.parseFilterExpression(eq(Operation.COUNT), eq(entityClass), eq(keyClass), eq(filter), eq(clause), eq(formattedFields), eq(null), eq(null), eq(null)))
                .thenReturn(expectedCount);

        long result = abstractGraphQLCrudController.count(filter, clause, fields);

        assertEquals(expectedCount, result);
    }

    @Test
    void getFormattedFieldsHandlesDotDataSlash() {
        List<String> fields = List.of("pageData.data/data.name", "pageData.data/data.pet/pet.name");
        List<String> expected = List.of("data.name", "data.pet.name");

        List<String> result = AbstractGraphQLCrudController.getFormattedFields(fields);

        assertEquals(expected, result);
    }

    @Test
    void getFormattedFieldsHandlesSlash() {
        List<String> fields = List.of("data/data.name");
        List<String> expected = List.of("data.name");

        List<String> result = AbstractGraphQLCrudController.getFormattedFields(fields);

        assertEquals(expected, result);
    }

    @Test
    void getFormattedFieldsHandlesUnderscore() {
        List<String> fields = List.of("author_name");
        List<String> expected = List.of("author.name");

        List<String> result = AbstractGraphQLCrudController.getFormattedFields(fields);

        assertEquals(expected, result);
    }

    @Test
    void getFormattedFieldsPagingElements() {
        List<String> fields = List.of("data.currentPageCount", "data.totalPages", "data.currentPage", "data.total");
        List<String> expected = List.of("currentPageCount", "totalPages", "currentPage", "total");

        List<String> result = AbstractGraphQLCrudController.getFormattedFields(fields);

        assertEquals(expected, result);
    }

    @Test
    void getFormattedAggregatesReturnsNullWhenAggregatesIsNull() {
        Aggregates result = AbstractGraphQLCrudController.getFormattedAggregates(null, List.of("field1"));
        assertEquals(null, result);
    }

    @Test
    void getFormattedAggregatesHandlesEmptyFields() {
        Aggregates aggregates = new Aggregates(List.of("data_id"), List.of("data_age"), List.of("data_salary"), List.of("data_salary", "data_experience"), List.of("data_salary", "data_experience"), null);
        Aggregates expected = new Aggregates(List.of("data.id"), List.of("data.age"), List.of("data.salary"), List.of("data.salary", "data.experience"), List.of("data.salary", "data.experience"), null);

        Aggregates result = AbstractGraphQLCrudController.getFormattedAggregates(aggregates, List.of());

        assertEquals(expected, result);
    }

    @Test
    void getFormattedAggregatesHandlesNonEmptyFields() {
        Aggregates aggregates = new Aggregates(List.of("data_id"), List.of("data_age"), List.of("data_salary"), List.of("data_salary", "data_experience"), List.of("data_salary", "data_experience"), List.of("data_salary"));
        Aggregates expected = new Aggregates(List.of("data.id"), List.of("data.age"), List.of("data.salary"), List.of("data.salary", "data.experience"), List.of("data.salary", "data.experience"), List.of("data.salary", "data.name", "data.sex"));

        Aggregates result = AbstractGraphQLCrudController.getFormattedAggregates(aggregates, List.of("data.name", "data.sex"));

        assertEquals(expected, result);
    }

    @Test
    void getFormattedAggregatesHandlesNullFields() {
        Aggregates aggregates = new Aggregates(List.of("countField"), List.of("sumField"), List.of("avgField"), List.of("minField"), List.of("maxField"), List.of("groupByField"));
        Aggregates expected = new Aggregates(List.of("countField"), List.of("sumField"), List.of("avgField"), List.of("minField"), List.of("maxField"), List.of("groupByField"));

        Aggregates result = AbstractGraphQLCrudController.getFormattedAggregates(aggregates, null);

        assertEquals(expected, result);
    }

    @Test
    void getFormattedAggregatesHandlesEmptyAggregates() {
        Aggregates aggregates = new Aggregates(List.of(), List.of(), List.of(), List.of(), List.of(), List.of());
        Aggregates expected = new Aggregates(null, null, null, null, null, null);

        Aggregates result = AbstractGraphQLCrudController.getFormattedAggregates(aggregates, List.of("field1"));

        assertEquals(expected, result);
    }

    @Test
    void getFormattedAggregatesHandlesNullAggregates() {
        Aggregates aggregates = null;
        Aggregates expected = null;

        Aggregates result = AbstractGraphQLCrudController.getFormattedAggregates(aggregates, List.of("field1"));

        assertEquals(expected, result);
    }

    @Test
    void processJoinsWithValidJoins() {
        abstractGraphQLCrudController.joins.put("author_pet", JoinType.LEFT);
        abstractGraphQLCrudController.joins.put("author_books", JoinType.INNER);

        abstractGraphQLCrudController.processJoins();

        assertEquals(JoinType.LEFT, abstractGraphQLCrudController.joins.get("author.pet"));
        assertEquals(JoinType.INNER, abstractGraphQLCrudController.joins.get("author.books"));
    }

    @Test
    void processJoinsWithEmptyJoins() {
        abstractGraphQLCrudController.joins.clear();

        abstractGraphQLCrudController.processJoins();

        assertTrue(abstractGraphQLCrudController.joins.isEmpty());
    }

    @Test
    void processJoinsWithNullJoins() {
        abstractGraphQLCrudController.joins = null;

        abstractGraphQLCrudController.processJoins();

        assertNull(abstractGraphQLCrudController.joins);
    }

    @Test
    void processJoinsWithMixedJoinTypes() {
        abstractGraphQLCrudController.joins.put("author_pet", JoinType.LEFT);
        abstractGraphQLCrudController.joins.put("author_books", JoinType.RIGHT);

        abstractGraphQLCrudController.processJoins();

        assertEquals(JoinType.LEFT, abstractGraphQLCrudController.joins.get("author.pet"));
        assertEquals(JoinType.RIGHT, abstractGraphQLCrudController.joins.get("author.books"));
    }

    @Test
    void extractFieldsFromEnvironmentWithValidEnvironment() {
        DataFetchingEnvironment environment = mock(DataFetchingEnvironment.class);
        DataFetchingFieldSelectionSet selectionSet = mock(DataFetchingFieldSelectionSet.class);
        SelectedField field = mock(SelectedField.class);
        when(environment.getSelectionSet()).thenReturn(selectionSet);
        when(selectionSet.getFields()).thenReturn(List.of(field));
        when(field.getFullyQualifiedName()).thenReturn("author.name");
        when(field.getArguments()).thenReturn(Map.of());

        List<String> result = abstractGraphQLCrudController.extractFieldsFromEnvironment(environment);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("author.name", result.get(0));
    }

    @Test
    void extractFieldsFromEnvironmentWithNullEnvironment() {
        List<String> result = abstractGraphQLCrudController.extractFieldsFromEnvironment(null);

        assertEquals(List.of(), result);
    }

    @Test
    void extractFieldsFromEnvironmentWithEmptySelectionSet() {
        DataFetchingEnvironment environment = mock(DataFetchingEnvironment.class);
        DataFetchingFieldSelectionSet selectionSet = mock(DataFetchingFieldSelectionSet.class);
        when(environment.getSelectionSet()).thenReturn(selectionSet);
        when(selectionSet.getFields()).thenReturn(Collections.emptyList());

        List<String> result = abstractGraphQLCrudController.extractFieldsFromEnvironment(environment);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void extractFieldsFromEnvironmentWithJoinArguments() {
        DataFetchingEnvironment environment = mock(DataFetchingEnvironment.class);
        DataFetchingFieldSelectionSet selectionSet = mock(DataFetchingFieldSelectionSet.class);
        SelectedField field = mock(SelectedField.class);
        when(environment.getSelectionSet()).thenReturn(selectionSet);
        when(selectionSet.getFields()).thenReturn(List.of(field));
        when(field.getFullyQualifiedName()).thenReturn("author.pet");
        when(field.getArguments()).thenReturn(Map.of("join", "LEFT"));

        List<String> result = abstractGraphQLCrudController.extractFieldsFromEnvironment(environment);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("author.pet", result.get(0));
        assertEquals(JoinType.LEFT, abstractGraphQLCrudController.joins.get("author.pet"));
    }
}