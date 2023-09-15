package dev.springharvest.search.service;

import dev.springharvest.search.model.queries.requests.filters.BaseFilterRequestDTO;
import dev.springharvest.search.model.queries.requests.search.SearchRequestDTO;
import dev.springhavest.common.models.entities.BaseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * The interface used to define the contract for a base service.
 *
 * @param <E>  The type of the Entity.
 * @param <K>  The type of the id (primary key) field.
 * @param <RD> The type of the request DTO.
 */
public interface ISearchService<E extends BaseEntity<K>, K extends Serializable, RD extends BaseFilterRequestDTO> {

    /**
     * Returns the entity with the given unique fields of the entity related to the DTO.
     *
     * @param entity The DTO that relates to the entity.
     *
     * @return The found DTO based on the unique fields of the entity related to the DTO argument.
     */
    Optional<E> findByUnique(E entity);

    /**
     * Returns true if an entity with the given unique fields of the entity related to the DTO exists, false otherwise.
     *
     * @param entity The DTO that relates to the entity.
     *
     * @return True if an entity with the given unique fields of the entity related to the DTO exists, false otherwise.
     */
    boolean existsByUnique(E entity);

    /**
     * This method is used to determine if an entity exists by the unique filter.
     *
     * @param query The search request.
     *
     * @return A list of DTOs that are retrieved by the search request.
     */
    List<E> search(SearchRequestDTO<RD> query);

}
