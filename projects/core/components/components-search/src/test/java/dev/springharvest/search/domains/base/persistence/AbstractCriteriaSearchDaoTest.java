package dev.springharvest.search.domains.base.persistence;

import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;

import dev.springharvest.search.domains.base.models.queries.parameters.selections.SelectionBO;
import dev.springharvest.search.domains.base.models.queries.requests.filters.BaseFilterRequestBO;
import dev.springharvest.search.domains.base.models.queries.requests.search.SearchRequest;
import dev.springharvest.shared.domains.base.models.entities.BaseEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.mockito.quality.Strictness;




@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AbstractCriteriaSearchDaoTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private CriteriaBuilder criteriaBuilder;

    @Mock
    private CriteriaQuery<TestEntity> criteriaQuery;

    @Mock
    private CriteriaQuery<Tuple> tupleQuery;

    @Mock
    private Root<TestEntity> root;

    @Mock
    private TypedQuery<TestEntity> typedQuery;

    @Mock
    private SearchRequest<BaseFilterRequestBO> searchRequest;

    @Mock
    private PriorityQueue<SelectionBO> selectionsByPriority;

    private AbstractCriteriaSearchDao<TestEntity, Long, BaseFilterRequestBO> dao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create concrete implementation for AbstractCriteriaSearchDao
        dao = new AbstractCriteriaSearchDao<TestEntity, Long, BaseFilterRequestBO>("testRoot", tuple -> new TestEntity()) {
            @Override
            protected List<TestEntity> aggregateAssociatedEntityLists(List<TestEntity> entities) {
                return entities;
            }
        };

        dao.setEntityManager(entityManager); // Inject mocked EntityManager

        // Mock EntityManager and CriteriaBuilder behavior
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(TestEntity.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(TestEntity.class)).thenReturn(root);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(List.of(new TestEntity(), new TestEntity()));

        // Mock Tuple query for field-specific selection
        when(criteriaBuilder.createQuery(Tuple.class)).thenReturn(tupleQuery);
        when(tupleQuery.from(TestEntity.class)).thenReturn(root);

        // Setup PriorityQueue mock for selectionsByPriority
        selectionsByPriority = mock(PriorityQueue.class);
        when(selectionsByPriority.isEmpty()).thenReturn(true); // Prevent NullPointerException

        // Setup SearchRequest mock
        searchRequest = mock(SearchRequest.class);
        when(searchRequest.getSelectionsByPriority(true)).thenReturn(selectionsByPriority);
        when(searchRequest.getSelections()).thenReturn(Collections.emptyList());
    }






    @Test
    void testDaoInitialization() {
        assertNotNull(dao);
        assertEquals("testRoot", dao.getRootPath());
    }
/* 
    @Test
    void testExistsByUnique() {
        // Mock behavior
        SearchRequest<BaseFilterRequestBO> searchRequest = mock(SearchRequest.class);
        when(typedQuery.getResultList()).thenReturn(List.of(new TestEntity()));

        // Execute test
        boolean result = dao.existsByUnique(searchRequest);

        // Verify results
        assertTrue(result);
        verify(typedQuery).getResultList();
    }

    @Test
    void testExists() {
        // Stub PriorityQueue behavior for selections
        when(selectionsByPriority.isEmpty()).thenReturn(false);

        // Mock EntityManager behavior
        when(typedQuery.getResultList()).thenReturn(List.of(new TestEntity()));

        // Execute test
        boolean result = dao.exists(searchRequest);

        // Verify result
        assertTrue(result);
        verify(typedQuery).getResultList();
    }
*/

    @Test
    void testCount() {
        when(selectionsByPriority.isEmpty()).thenReturn(true);

        int count = dao.count(searchRequest);

        assertEquals(2, count, "Count should match mocked result");
        verify(typedQuery).getResultList();
    }


    @Test
    void testSearch_SelectAll() {
        // Ensure selections are empty for "select all"
        when(searchRequest.getSelections()).thenReturn(Collections.emptyList());
        when(typedQuery.getResultList()).thenReturn(List.of(new TestEntity()));

        // Execute test
        List<TestEntity> result = dao.search(searchRequest);

        // Verify results
        assertNotNull(result, "Result should not be null");
        assertEquals(1, result.size(), "Result size should match mocked response");
        verify(typedQuery).getResultList();
    }

/* 
    @Test
    void testSearch_SelectSpecificFields() {
        // Mock a valid SelectionBO
        SelectionBO mockSelection = mock(SelectionBO.class);
        when(mockSelection.getPath()).thenReturn("valid.path");
        when(mockSelection.getAlias()).thenReturn("alias");

        // Set up searchRequest with valid selections
        when(searchRequest.getSelections()).thenReturn(List.of(mockSelection));
        when(typedQuery.getResultList()).thenReturn(List.of(new TestEntity()));

        // Execute test
        List<TestEntity> result = dao.search(searchRequest);

        // Verify results
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(typedQuery).getResultList();
    } */



    // Concrete implementation for testing purposes
    /* 
    static class TestCriteriaSearchDao extends AbstractCriteriaSearchDao<TestEntity, Long, BaseFilterRequestBO> {

        public TestCriteriaSearchDao() {
            super("testRoot", tuple -> new TestEntity());
        }
    
        // These methods may not override anything but can be implemented if needed
        protected List<TestEntity> transformResults(List<Tuple> tuples) {
            return Collections.emptyList(); // Stub implementation for testing
        }
    
        protected CriteriaQuery<TestEntity> applyFilters(
            SearchRequest<BaseFilterRequestBO> searchRequest,
            CriteriaQuery<TestEntity> query,
            Root<TestEntity> root
        ) {
            return query; // Stub implementation for testing
        }
    }
    */
    

    // Mock TestEntity class
    public class TestEntity extends BaseEntity<Long> {
        private Long id;
    
        @Override
        public Long getId() {
            return id;
        }
    
        @Override
        public void setId(Long id) {
            this.id = id;
        }
    }
    @Test
    void testSearch() {
        SearchRequest<BaseFilterRequestBO> searchRequest = new SearchRequest<>();
        searchRequest.setSelections(Collections.emptyList()); // Mimic "select all"

        List<TestEntity> results = dao.search(searchRequest);

        assertNotNull(results, "Results should not be null");
        assertEquals(2, results.size(), "Results size should match the mocked response");
    }

    
}