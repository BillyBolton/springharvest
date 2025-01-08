package dev.springharvest.expressions.mappers;

import jakarta.persistence.Tuple;

import java.util.List;
import java.util.Map;

/**
 * Interface for mapping database tuples to entities and maps.
 *
 * @param <T> the type of the entity
 */
public interface EntityMapper<T> {

    /**
     * Maps a list of database tuples to an entity of the specified class.
     *
     * @param rawList the list of tuples to map
     * @param entityClass the class of the entity to map to
     * @return the mapped entity
     */
    List<T> mapTuplesToEntity(List<Tuple> rawList, Class<T> entityClass);

    /**
     * Maps a list of database tuples to a list of maps.
     *
     * @param rawList the list of tuples to map
     * @return the list of maps
     */
    List<Map<String, Object>> mapTuplesToMap(List<Tuple> rawList);
}
