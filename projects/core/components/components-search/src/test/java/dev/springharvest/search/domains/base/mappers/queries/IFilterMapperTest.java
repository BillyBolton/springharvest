package dev.springharvest.search.domains.base.mappers.queries;

import dev.springharvest.search.domains.base.models.queries.parameters.filters.FilterParameterDTO;
import dev.springharvest.search.domains.base.models.queries.requests.filters.BaseFilterBO;
import dev.springharvest.search.domains.base.models.queries.requests.filters.BaseFilterDTO;
import dev.springharvest.search.domains.base.models.queries.requests.filters.BaseFilterRequestBO;
import dev.springharvest.search.domains.base.models.queries.requests.filters.BaseFilterRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;

public class IFilterMapperTest {

    @Mock
    private IFilterMapper<BaseFilterRequestDTO, BaseFilterRequestBO, BaseFilterDTO, BaseFilterBO> filterMapper;

    @Mock
    private BaseFilterRequestDTO baseFilterRequestDTO;
    @Mock
    private BaseFilterRequestBO baseFilterRequestBO;
    @Mock
    private BaseFilterDTO baseFilterDTO;
    @Mock
    private BaseFilterBO baseFilterBO;

    @Mock
    private FilterParameterDTO filterParameterDTO;

    private Set<BaseFilterRequestDTO> filterRequestDTOSet;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);
        filterRequestDTOSet = new HashSet<>();
        filterRequestDTOSet.add(baseFilterRequestDTO);
    }

    @Test
    void testToBO_single() {
        // Arrange
        when(filterMapper.toBO(baseFilterRequestDTO)).thenReturn(baseFilterRequestBO);

        // Act
        BaseFilterRequestBO result = filterMapper.toBO(baseFilterRequestDTO);

        // Assert
        verify(filterMapper, times(1)).toBO(baseFilterRequestDTO);
        assert(result == baseFilterRequestBO);
    }

    @Test
    void testToBO_set() {
        // Arrange
        Set<BaseFilterRequestBO> boSet = new HashSet<>();
        boSet.add(baseFilterRequestBO);
        when(filterMapper.toBO(filterRequestDTOSet)).thenReturn(boSet);

        // Act
        Set<BaseFilterRequestBO> resultSet = filterMapper.toBO(filterRequestDTOSet);

        // Assert
        verify(filterMapper, times(1)).toBO(filterRequestDTOSet);
        assert(resultSet.contains(baseFilterRequestBO));
    }

    @Test
    void testToFilter() {
        // Arrange
        when(filterMapper.toFilter(baseFilterDTO)).thenReturn(baseFilterBO);

        // Act
        BaseFilterBO result = filterMapper.toFilter(baseFilterDTO);

        // Assert
        verify(filterMapper, times(1)).toFilter(baseFilterDTO);
        assert(result == baseFilterBO);
    }

    @Test
    void testSetDirtyFields() {
        // Arrange
        filterMapper.setDirtyFields(baseFilterRequestDTO, baseFilterRequestBO);

        // Act
        // No actual assertion, just verifying no exception is thrown
        verify(filterMapper, times(1)).setDirtyFields(baseFilterRequestDTO, baseFilterRequestBO);
    }



}
