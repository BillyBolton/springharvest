package dev.springharvest.search.global;

import dev.springharvest.search.domains.base.models.entities.IEntityMetadata;
import dev.springharvest.shared.domains.DomainModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Collections;


import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IGlobalClazzResolverTest {

    private IGlobalClazzResolver globalClazzResolver;

    @Mock
    private IEntityMetadata<TestDomainModel> entityMetadata;

    @Mock
    private Map<String, IEntityMetadata<TestDomainModel>> entityMetadataMap;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        globalClazzResolver = new IGlobalClazzResolver() {
            @Override
            public Map<String, IEntityMetadata<?>> getEntityMetadataMap() {
                Map<String, IEntityMetadata<?>> map = new HashMap<>();
                map.put("domain", entityMetadata);
                return map;
            }
        };
    }

    

    @Test
    void testGetClazz_WithInvalidPath() {
        // Arrange
        String path = "invalid.path";

        // Act & Assert
        assertThrows(NullPointerException.class, () -> globalClazzResolver.getClazz(path),
                "Should throw NullPointerException for invalid domain");
    }

    @Test
    void testGetClazz_WithEmptyPath() {
        // Arrange
        String path = "";

        // Act & Assert
        assertThrows(NullPointerException.class, () -> globalClazzResolver.getClazz(path),
                "Should throw NullPointerException for empty path");
    }

    @Test
    void testGetClazz_WithNoMatchingDomain() {
        // Arrange
        globalClazzResolver = new IGlobalClazzResolver() {
            @Override
            public Map<String, IEntityMetadata<?>> getEntityMetadataMap() {
                return Collections.emptyMap(); // No domains available
            }
        };
        String path = "some.domain.path";

        // Act & Assert
        assertThrows(NullPointerException.class, () -> globalClazzResolver.getClazz(path),
                "Should throw NullPointerException for a domain that doesn't exist");
    }

    


    


    static class TestDomainModel extends DomainModel {

        @Override
        public boolean isEmpty() {
            return false; 
        }
    }
}
