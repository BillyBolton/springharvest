package dev.springharvest.library.domains.pet.graphql;

import dev.springharvest.expressions.builders.TypedQueryBuilder;
import dev.springharvest.expressions.helpers.Operation;
import dev.springharvest.library.config.LiquibaseTestExecutionListener;
import dev.springharvest.library.config.TestComponentScanningConfig;
import dev.springharvest.library.config.TestContainerConfig;
import dev.springharvest.library.domains.pet.service.PetCrudService;
import dev.springharvest.shared.constants.DataPaging;
import dev.springharvest.shared.constants.Sort;
import dev.springharvest.shared.constants.SortDirection;
import dev.springharvest.library.domains.pet.models.entities.PetEntity;
import dev.springharvest.shared.constants.PageData;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(value = {TestComponentScanningConfig.class, TestContainerConfig.class})
@TestExecutionListeners(
        listeners = {DependencyInjectionTestExecutionListener.class, LiquibaseTestExecutionListener.class},
        mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
@TestPropertySource(locations = "classpath:application.properties")
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class PetGraphQLControllerTest {

    @Autowired
    private PetGraphQLController petGraphQLController;

    @MockBean
    private PetCrudService petCrudService;

    @MockBean
    private TypedQueryBuilder typedQueryBuilder;

    @Test
    void searchPetsReturnsExpectedResults() {
        Map<String, Object> filter = Map.of("name", "Buddy");
        Map<String, Object> clause = Map.of();
        DataPaging paging = new DataPaging(0, 10, Collections.singletonList(new Sort("name", SortDirection.ASC)));
        DataFetchingEnvironment environment = mock(DataFetchingEnvironment.class);

        DataFetchingFieldSelectionSet selectionSet = mock(DataFetchingFieldSelectionSet.class);
        when(environment.getSelectionSet()).thenReturn(selectionSet);
        when(selectionSet.getFields()).thenReturn(Collections.emptyList());

        PageData<PetEntity> expectedPageData = new PageData<>(List.of(new PetEntity("Buddy")), 1, 1, 10, 1, 10);

        when(typedQueryBuilder.parseFilterExpression(eq(Operation.SEARCH), eq(PetEntity.class), eq(UUID.class), eq(filter), eq(clause), anyList(), anyMap(), isNull(), eq(paging)))
                .thenReturn(expectedPageData);

        PageData<PetEntity> result = petGraphQLController.searchPets(filter, clause, paging, environment);

        assertEquals(expectedPageData, result);
    }

    @Test
    void searchPetsReturnsEmptyResults() {
        Map<String, Object> filter = Map.of("name", "NonExistent");
        Map<String, Object> clause = Map.of();
        DataPaging paging = new DataPaging(0, 10, Collections.singletonList(new Sort("name", SortDirection.ASC)));
        DataFetchingEnvironment environment = mock(DataFetchingEnvironment.class);

        DataFetchingFieldSelectionSet selectionSet = mock(DataFetchingFieldSelectionSet.class);
        when(environment.getSelectionSet()).thenReturn(selectionSet);
        when(selectionSet.getFields()).thenReturn(Collections.emptyList());

        PageData<PetEntity> expectedPageData = new PageData<>(List.of(), 0, 0, 10, 1, 10);

        when(typedQueryBuilder.parseFilterExpression(eq(Operation.SEARCH), eq(PetEntity.class), eq(UUID.class), eq(filter), eq(clause), anyList(), anyMap(), isNull(), eq(paging)))
                .thenReturn(expectedPageData);

        PageData<PetEntity> result = petGraphQLController.searchPets(filter, clause, paging, environment);

        assertEquals(expectedPageData, result);
    }

    @Test
    void searchPetsReturnsResultsForPartialNameMatch() {
        Map<String, Object> filter = Map.of("name", "Bud");
        Map<String, Object> clause = Map.of();
        DataPaging paging = new DataPaging(0, 10, Collections.singletonList(new Sort("name", SortDirection.ASC)));
        DataFetchingEnvironment environment = mock(DataFetchingEnvironment.class);

        DataFetchingFieldSelectionSet selectionSet = mock(DataFetchingFieldSelectionSet.class);
        when(environment.getSelectionSet()).thenReturn(selectionSet);
        when(selectionSet.getFields()).thenReturn(Collections.emptyList());

        List<PetEntity> pets = List.of(new PetEntity("Buddy"), new PetEntity("Buddy Jr."));
        PageData<PetEntity> expectedPageData = new PageData<>(pets, 2, 1, 10, 1, 10);

        when(typedQueryBuilder.parseFilterExpression(eq(Operation.SEARCH), eq(PetEntity.class), eq(UUID.class), eq(filter), eq(clause), anyList(), anyMap(), isNull(), eq(paging)))
                .thenReturn(expectedPageData);

        PageData<PetEntity> result = petGraphQLController.searchPets(filter, clause, paging, environment);

        assertEquals(expectedPageData, result);
    }

    @Test
    void searchPetsReturnsResultsForEmptyFilter() {
        Map<String, Object> filter = Map.of();
        Map<String, Object> clause = Map.of();
        DataPaging paging = new DataPaging(0, 10, Collections.singletonList(new Sort("name", SortDirection.ASC)));
        DataFetchingEnvironment environment = mock(DataFetchingEnvironment.class);

        DataFetchingFieldSelectionSet selectionSet = mock(DataFetchingFieldSelectionSet.class);
        when(environment.getSelectionSet()).thenReturn(selectionSet);
        when(selectionSet.getFields()).thenReturn(Collections.emptyList());

        List<PetEntity> pets = List.of(new PetEntity("Buddy"), new PetEntity("Max"));
        PageData<PetEntity> expectedPageData = new PageData<>(pets, 2, 1, 10, 1, 10);

        when(typedQueryBuilder.parseFilterExpression(eq(Operation.SEARCH), eq(PetEntity.class), eq(UUID.class), eq(filter), eq(clause), anyList(), anyMap(), isNull(), eq(paging)))
                .thenReturn(expectedPageData);

        PageData<PetEntity> result = petGraphQLController.searchPets(filter, clause, paging, environment);

        assertEquals(expectedPageData, result);
    }

    @Test
    void countPetsReturnsExpectedCount() {
        Map<String, Object> filter = Map.of("type", "Dog");
        Map<String, Object> clause = Map.of();
        List<String> fields = List.of("type");

        long expectedCount = 5L;

        when(typedQueryBuilder.parseFilterExpression(eq(Operation.COUNT), eq(PetEntity.class), eq(UUID.class), eq(filter), eq(clause), anyList(), isNull(), isNull(), isNull()))
                .thenReturn(expectedCount);

        long result = petGraphQLController.countPets(filter, clause, fields);

        assertEquals(expectedCount, result);
    }

    @Test
    void countPetsReturnsZeroForNoMatches() {
        Map<String, Object> filter = Map.of("type", "NonExistentType");
        Map<String, Object> clause = Map.of();
        List<String> fields = List.of("type");

        long expectedCount = 0L;

        when(typedQueryBuilder.parseFilterExpression(eq(Operation.COUNT), eq(PetEntity.class), eq(UUID.class), eq(filter), eq(clause), anyList(), isNull(), isNull(), isNull()))
                .thenReturn(expectedCount);

        long result = petGraphQLController.countPets(filter, clause, fields);

        assertEquals(expectedCount, result);
    }

    @Test
    void countPetsReturnsCountForMultipleFields() {
        Map<String, Object> filter = Map.of("type", "Dog", "location", "USA");
        Map<String, Object> clause = Map.of();
        List<String> fields = List.of("type", "location");

        long expectedCount = 3L;

        when(typedQueryBuilder.parseFilterExpression(eq(Operation.COUNT), eq(PetEntity.class), eq(UUID.class), eq(filter), eq(clause), anyList(), isNull(), isNull(), isNull()))
                .thenReturn(expectedCount);

        long result = petGraphQLController.countPets(filter, clause, fields);

        assertEquals(expectedCount, result);
    }

    @Test
    void countPetsReturnsZeroForEmptyFilter() {
        Map<String, Object> filter = Map.of();
        Map<String, Object> clause = Map.of();
        List<String> fields = List.of();

        long expectedCount = 0L;

        when(typedQueryBuilder.parseFilterExpression(eq(Operation.COUNT), eq(PetEntity.class), eq(UUID.class), eq(filter), eq(clause), anyList(), isNull(), isNull(), isNull()))
                .thenReturn(expectedCount);

        long result = petGraphQLController.countPets(filter, clause, fields);

        assertEquals(expectedCount, result);
    }

}