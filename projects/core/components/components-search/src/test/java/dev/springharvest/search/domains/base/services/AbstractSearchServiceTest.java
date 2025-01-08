package dev.springharvest.search.domains.base.services;

import dev.springharvest.search.domains.base.mappers.queries.ISearchMapper;
import dev.springharvest.search.domains.base.models.entities.EntityMetadata;
import dev.springharvest.search.domains.base.models.queries.requests.filters.BaseFilterBO;
import dev.springharvest.search.domains.base.models.queries.requests.filters.BaseFilterDTO;
import dev.springharvest.search.domains.base.models.queries.requests.filters.BaseFilterRequestBO;
import dev.springharvest.search.domains.base.models.queries.requests.filters.BaseFilterRequestDTO;
import dev.springharvest.search.domains.base.models.queries.requests.search.SearchRequest;
import dev.springharvest.search.domains.base.models.queries.requests.search.SearchRequestDTO;
import dev.springharvest.search.domains.base.persistence.AbstractCriteriaSearchDao;
import dev.springharvest.shared.domains.base.models.entities.BaseEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AbstractSearchServiceTest {

    private TestSearchService searchService;

    @Mock
    private EntityMetadata<TestEntity> entityMetadata;

    @Mock
    private ISearchMapper<TestEntity, Long, BaseFilterRequestDTO, BaseFilterRequestBO, BaseFilterDTO, BaseFilterBO> filterMapper;

    @Mock
    private AbstractCriteriaSearchDao<TestEntity, Long, BaseFilterRequestBO> searchRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        searchService = new TestSearchService(entityMetadata, filterMapper, searchRepository);
    }

    @Test
    void testSearch() {
        // Setup mock behavior
        var filterRequestDTO = new SearchRequestDTO<BaseFilterRequestDTO>();
        var searchRequest = mock(SearchRequest.class); // Use SearchRequest<BaseFilterRequestBO>
        var testEntities = List.of(new TestEntity(), new TestEntity());

        when(filterMapper.toSearchRequest(filterRequestDTO)).thenReturn(searchRequest); // Mock returning SearchRequest
        when(searchRepository.search(searchRequest)).thenReturn(testEntities);

        // Execute
        List<TestEntity> result = searchService.search(filterRequestDTO);

        // Verify
        assertNotNull(result, "Result should not be null");
        assertEquals(2, result.size(), "Result size should match the mocked data");
        verify(filterMapper).toSearchRequest(filterRequestDTO);
        verify(searchRepository).search(searchRequest);
    }


    @Test
    void testCount() {
        // Setup mock behavior
        var filterRequestDTO = new SearchRequestDTO<BaseFilterRequestDTO>();
        var searchRequest = mock(SearchRequest.class); // Use SearchRequest<BaseFilterRequestBO>
        var testEntities = List.of(new TestEntity(), new TestEntity());

        when(entityMetadata.getRootPaths()).thenReturn(Set.of("path"));
        when(filterMapper.toSearchRequest(filterRequestDTO)).thenReturn(searchRequest); // Mock returning SearchRequest
        when(searchRepository.search(searchRequest)).thenReturn(testEntities);

        // Execute
        int count = searchService.count(filterRequestDTO);

        // Verify
        assertEquals(2, count, "Count should match the size of mocked entities");
        verify(entityMetadata, times(2)).getRootPaths();
        verify(filterMapper).toSearchRequest(filterRequestDTO);
        verify(searchRepository).search(searchRequest);
    }


    


    // Define a concrete implementation of AbstractSearchService
    private static class TestSearchService extends AbstractSearchService<TestEntity, Long, BaseFilterRequestDTO, BaseFilterRequestBO, BaseFilterDTO, BaseFilterBO> {

        protected TestSearchService(EntityMetadata<TestEntity> entityMetadata,
                                    ISearchMapper<TestEntity, Long, BaseFilterRequestDTO, BaseFilterRequestBO, BaseFilterDTO, BaseFilterBO> filterMapper,
                                    AbstractCriteriaSearchDao<TestEntity, Long, BaseFilterRequestBO> searchRepository) {
            super(entityMetadata, filterMapper, searchRepository);
        }
    }
    

    // Define a TestEntity class for testing purposes
    static class TestEntity extends BaseEntity<Long> {
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
}
