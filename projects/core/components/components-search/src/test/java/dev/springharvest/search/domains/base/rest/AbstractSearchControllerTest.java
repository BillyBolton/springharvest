package dev.springharvest.search.domains.base.rest;


import dev.springharvest.search.domains.base.models.queries.requests.filters.BaseFilterBO;
import dev.springharvest.search.domains.base.models.queries.requests.filters.BaseFilterDTO;
import dev.springharvest.search.domains.base.models.queries.requests.filters.BaseFilterRequestBO;
import dev.springharvest.search.domains.base.models.queries.requests.filters.BaseFilterRequestDTO;
import dev.springharvest.search.domains.base.models.queries.requests.search.SearchRequestDTO;
import dev.springharvest.search.domains.base.services.AbstractSearchService;
import dev.springharvest.shared.domains.base.mappers.IBaseModelMapper;
import dev.springharvest.shared.domains.base.models.dtos.BaseDTO;
import dev.springharvest.shared.domains.base.models.entities.BaseEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AbstractSearchControllerTest {

    // Mocks for dependencies
    @Mock
    private IBaseModelMapper<TestDTO, TestEntity, Long> modelMapper;

    @Mock
    private AbstractSearchService<TestEntity, Long, BaseFilterRequestDTO, BaseFilterRequestBO, BaseFilterDTO, BaseFilterBO> searchService;

    // Test instance of the controller
    @InjectMocks
    private TestSearchController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks and inject them
    }

    @Test
    void testSearch() {
        // Mock inputs
        SearchRequestDTO<BaseFilterRequestDTO> searchQuery = new SearchRequestDTO<>();
        List<TestEntity> entities = Arrays.asList(new TestEntity(1L), new TestEntity(2L));
        List<TestDTO> dtos = Arrays.asList(new TestDTO(1L), new TestDTO(2L));

        // Mock behavior
        when(searchService.search(searchQuery)).thenReturn(entities);
        when(modelMapper.entityToDto(entities)).thenReturn(dtos);

        // Execute method
        ResponseEntity<List<TestDTO>> response = controller.search(searchQuery);

        // Validate results
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(dtos, response.getBody());

        // Verify interactions
        verify(searchService).search(searchQuery);
        verify(modelMapper).entityToDto(entities);
    }

    @Test
    void testCount() {
        // Mock inputs
        SearchRequestDTO<BaseFilterRequestDTO> searchQuery = new SearchRequestDTO<>();
        int expectedCount = 5;

        // Mock behavior
        when(searchService.count(searchQuery)).thenReturn(expectedCount);

        // Execute method
        ResponseEntity<Integer> response = controller.count(searchQuery);

        // Validate results
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedCount, response.getBody());

        // Verify interactions
        verify(searchService).count(searchQuery);
    }

    @Test
    void testExists() {
        // Mock inputs
        SearchRequestDTO<BaseFilterRequestDTO> searchQuery = new SearchRequestDTO<>();
        boolean expectedExists = true;

        // Mock behavior
        when(searchService.exists(searchQuery)).thenReturn(expectedExists);

        // Execute method
        ResponseEntity<Boolean> response = controller.exists(searchQuery);

        // Validate results
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedExists, response.getBody());

        // Verify interactions
        verify(searchService).exists(searchQuery);
    }

    // Concrete implementation for testing
    private static class TestSearchController extends AbstractSearchController<TestDTO, TestEntity, Long, BaseFilterRequestDTO, BaseFilterRequestBO, BaseFilterDTO, BaseFilterBO> {
        protected TestSearchController(IBaseModelMapper<TestDTO, TestEntity, Long> modelMapper,
                                       AbstractSearchService<TestEntity, Long, BaseFilterRequestDTO, BaseFilterRequestBO, BaseFilterDTO, BaseFilterBO> searchService) {
            super(modelMapper, searchService);
        }
    }

    // Test DTO class
    private static class TestDTO extends BaseDTO<Long> {
        private Long id;

        public TestDTO(Long id) {
            this.id = id;
        }

        @Override
        public Long getId() {
            return id;
        }

        @Override
        public void setId(Long id) {
            this.id = id;
        }
    }

    // Test Entity class
    private static class TestEntity extends BaseEntity<Long> {
        private Long id;

        public TestEntity(Long id) {
            this.id = id;
        }

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

