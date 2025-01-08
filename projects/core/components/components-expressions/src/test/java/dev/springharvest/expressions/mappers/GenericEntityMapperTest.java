package dev.springharvest.expressions.mappers;

import dev.springharvest.shared.domains.base.models.entities.BaseEntity;
import jakarta.persistence.Tuple;
import jakarta.persistence.TupleElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GenericEntityMapperTest {

    @Mock
    private Tuple tuple;

    @Mock
    private TupleElement<?> tupleElement;

    private GenericEntityMapper<Author> genericEntityMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        genericEntityMapper = new GenericEntityMapper<Author>();
    }

    @Test
    void mapTuplesToEntity_withValidTuples_returnsMappedEntities() throws Exception {

        Tuple tuple = mock(Tuple.class);
        TupleElement<String> authorNameElement = mock(TupleElement.class);
        TupleElement<String> petNameElement = mock(TupleElement.class);

        List<Tuple> rawList = new ArrayList<>();
        rawList.add(tuple);

        when(tuple.getElements()).thenReturn(List.of(authorNameElement, petNameElement));
        when(authorNameElement.getJavaType()).thenReturn((Class) String.class);
        when(petNameElement.getJavaType()).thenReturn((Class) String.class);

        when(authorNameElement.getAlias()).thenReturn("name");
        when(petNameElement.getAlias()).thenReturn("pet.name");

        when(tuple.get("name", String.class)).thenReturn("John Doe");
        when(tuple.get("pet.name", String.class)).thenReturn("Buddy");

        // Invoking the method under test
        List<GenericEntityMapperTest.Author> result = genericEntityMapper.mapTuplesToEntity(rawList, GenericEntityMapperTest.Author.class);

        // Assertions
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
        assertEquals("Buddy", result.get(0).getPet().getName());
    }


    @Test
    void mapTuplesToEntity_withEmptyList_returnsEmptyList() {
        List<Tuple> rawList = new ArrayList<>();

        List<GenericEntityMapperTest.Author> result = genericEntityMapper.mapTuplesToEntity(rawList, GenericEntityMapperTest.Author.class);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void mapTuplesToEntity_withInvalidField_throwsException() {
        List<Tuple> rawList = new ArrayList<>();
        rawList.add(tuple);

        when(tuple.getElements()).thenReturn(List.of(tupleElement));
        when(tupleElement.getAlias()).thenReturn("invalidField");

        List<GenericEntityMapperTest.Author> result = genericEntityMapper.mapTuplesToEntity(rawList, GenericEntityMapperTest.Author.class);

        assertTrue(result.isEmpty());
    }

    @Test
    void mapTuplesToMap_withValidTuples_returnsMappedList() {
        Tuple tuple = mock(Tuple.class);
        TupleElement<String> tupleElement = mock(TupleElement.class);

        List<Tuple> rawList = new ArrayList<>();
        rawList.add(tuple);

        when(tuple.getElements()).thenReturn(List.of(tupleElement));

        when(tupleElement.getAlias()).thenReturn("field");

        when(tuple.get(0)).thenReturn("value");

        List<Map<String, Object>> result = genericEntityMapper.mapTuplesToMap(rawList);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("value", result.get(0).get("field"));
    }

    @Test
    void mapTuplesToMap_withEmptyList_returnsEmptyList() {
        List<Tuple> rawList = new ArrayList<>();

        List<Map<String, Object>> result = genericEntityMapper.mapTuplesToMap(rawList);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    public static class Author extends BaseEntity<UUID> {
        private String name;
        private Pet pet;

        public String getName() {
            return name;
        }
        public Pet getPet() {
            return pet;
        }
    }

    public static class Pet extends BaseEntity<UUID>{
        private String name;
        public String getName() {
            return name;
        }
    }
}
