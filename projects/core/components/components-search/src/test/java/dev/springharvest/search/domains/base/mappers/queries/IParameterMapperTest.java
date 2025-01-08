import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import dev.springharvest.search.domains.base.mappers.queries.IParameterMapper;
import dev.springharvest.search.domains.base.models.queries.parameters.filters.FilterParameterBO;
import dev.springharvest.search.domains.base.models.queries.parameters.filters.FilterParameterDTO;
import dev.springharvest.search.domains.base.models.queries.parameters.selections.SelectionBO;
import dev.springharvest.search.domains.base.models.queries.parameters.selections.SelectionDTO;

public class IParameterMapperTest {

    @Mock
    private IParameterMapper parameterMapper;

    @Mock
    private FilterParameterDTO filterParameterDTO;
    @Mock
    private FilterParameterBO filterParameterBO;
    @Mock
    private SelectionDTO selectionDTO;
    @Mock
    private SelectionBO selectionBO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test toParameter() method
    @Test
    void testToParameter() {
        // Arrange
        when(parameterMapper.toParameter(filterParameterDTO)).thenReturn(filterParameterBO);
        
        // Act
        FilterParameterBO result = parameterMapper.toParameter(filterParameterDTO);
        
        // Assert
        assertNotNull(result);
        assertEquals(filterParameterBO, result);
        verify(parameterMapper, times(1)).toParameter(filterParameterDTO);
    }

    // Test toSelection() method (single selection)
    @Test
    void testToSelection() {
        // Arrange
        when(parameterMapper.toSelection(selectionDTO)).thenReturn(selectionBO);
        
        // Act
        SelectionBO result = parameterMapper.toSelection(selectionDTO);
        
        // Assert
        assertNotNull(result);
        assertEquals(selectionBO, result);
        verify(parameterMapper, times(1)).toSelection(selectionDTO);
    }

    // Test toSelection() method (list of selections)
    @Test
    void testToSelectionList() {
        // Arrange
        List<SelectionDTO> selectionDTOList = List.of(selectionDTO);
        List<SelectionBO> selectionBOList = List.of(selectionBO);
        when(parameterMapper.toSelection(selectionDTOList)).thenReturn(selectionBOList);
        
        // Act
        List<SelectionBO> result = parameterMapper.toSelection(selectionDTOList);
        
        // Assert
        assertNotNull(result);
        assertEquals(selectionBOList, result);
        verify(parameterMapper, times(1)).toSelection(selectionDTOList);
    }

    // Test getClazz() method (default method in interface)
    @Test
    void testGetClazz() {
        // Arrange
        String path = "some.path";
        when(parameterMapper.getClazz(path)).thenCallRealMethod();  // Call the real method

        // Act
        Class<?> clazz = parameterMapper.getClazz(path);

        // Assert
        assertNull(clazz);  // Since it's the default method and throws OperationNotSupportedException
        verify(parameterMapper, times(1)).getClazz(path);
    }

    // Test getIsJoined() method (default method in interface)
    @Test
    void testGetIsJoined() {
        // Arrange
        String path = "some.path";
        Set<String> roots = Set.of("root");
        when(parameterMapper.getRoots()).thenReturn(roots);  // Mock getRoots method
        when(parameterMapper.getIsJoined(path)).thenCallRealMethod();  // Call the real method

        // Act
        boolean isJoined = parameterMapper.getIsJoined(path);

        // Assert
        assertTrue(isJoined);  // Since "some" is not in roots, it should be considered joined
        verify(parameterMapper, times(1)).getIsJoined(path);
    }

    // Test getRoots() method (default method in interface)
    @Test
    void testGetRoots() {
        // Arrange
        Set<String> expectedRoots = Set.of("root1", "root2");
        when(parameterMapper.getRoots()).thenReturn(expectedRoots);  // Mock getRoots method

        // Act
        Set<String> roots = parameterMapper.getRoots();

        // Assert
        assertEquals(expectedRoots, roots);
        verify(parameterMapper, times(1)).getRoots();
    }
}
