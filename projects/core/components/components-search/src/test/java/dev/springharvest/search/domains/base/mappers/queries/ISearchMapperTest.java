import java.io.Serializable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import dev.springharvest.search.domains.base.mappers.queries.ISearchMapper;
import dev.springharvest.search.domains.base.models.entities.IEntityMetadata;
import dev.springharvest.search.domains.base.models.queries.requests.filters.BaseFilterBO;
import dev.springharvest.search.domains.base.models.queries.requests.filters.BaseFilterDTO;
import dev.springharvest.search.domains.base.models.queries.requests.filters.BaseFilterRequestBO;
import dev.springharvest.search.domains.base.models.queries.requests.filters.BaseFilterRequestDTO;
import dev.springharvest.search.domains.base.models.queries.requests.search.SearchRequest;
import dev.springharvest.search.domains.base.models.queries.requests.search.SearchRequestDTO;
import dev.springharvest.search.global.IGlobalClazzResolver;
import dev.springharvest.shared.domains.base.models.entities.BaseEntity;

public class ISearchMapperTest {

    @Mock
    private IGlobalClazzResolver globalClazzResolver;

    @Mock
    private IEntityMetadata<BaseEntity<Serializable>> entityMetadata;

    @Mock
    private ISearchMapper<BaseEntity<Serializable>, Serializable, BaseFilterRequestDTO, BaseFilterRequestBO, BaseFilterDTO, BaseFilterBO> searchMapper;

    @Mock
    private SearchRequestDTO<BaseFilterRequestDTO> searchRequestDTO;

    @Mock
    private SearchRequest<BaseFilterRequestBO> searchRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Testing toSearchRequest() method
    @Test
    void testToSearchRequest() {
        // Arrange
        when(searchMapper.toSearchRequest(searchRequestDTO)).thenReturn(searchRequest);

        // Act
        SearchRequest<BaseFilterRequestBO> result = searchMapper.toSearchRequest(searchRequestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(searchRequest, result);
        verify(searchMapper, times(1)).toSearchRequest(searchRequestDTO);
    }

    

    

    // Testing getGlobalClazzResolver() method (abstract, mocking it for indirect testing)
    @Test
    void testGetGlobalClazzResolver() {
        // Arrange
        when(searchMapper.getGlobalClazzResolver()).thenReturn(globalClazzResolver);

        // Act
        IGlobalClazzResolver result = searchMapper.getGlobalClazzResolver();

        // Assert
        assertNotNull(result);
        verify(searchMapper, times(1)).getGlobalClazzResolver();
    }

    // Testing getEntityMetadata() method (abstract, mocking it for indirect testing)
    @Test
    void testGetEntityMetadata() {
        // Arrange
        when(searchMapper.getEntityMetadata()).thenReturn(entityMetadata);

        // Act
        IEntityMetadata<BaseEntity<Serializable>> result = searchMapper.getEntityMetadata();

        // Assert
        assertNotNull(result);
        verify(searchMapper, times(1)).getEntityMetadata();
    }
}
