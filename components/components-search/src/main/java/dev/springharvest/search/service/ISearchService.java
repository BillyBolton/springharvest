package dev.springharvest.search.service;

import dev.springharvest.search.model.queries.requests.filters.BaseFilterRequestDTO;
import dev.springharvest.search.model.queries.requests.search.SearchRequestDTO;
import dev.springhavest.common.models.dtos.BaseDTO;

import java.io.Serializable;
import java.util.List;

/**
 * The interface used to define the contract for a base service.
 *
 * @param <D>  The type of the DTO.
 * @param <K>  The type of the id (primary key) field.
 * @param <RD> The type of the request DTO.
 */
public interface ISearchService<D extends BaseDTO<K>, K extends Serializable, RD extends BaseFilterRequestDTO> {

    /**
     * Returns the entity with the given unique fields of the entity related to the DTO.
     *
     * @param dto The DTO that relates to the entity.
     *
     * @return The found DTO based on the unique fields of the entity related to the DTO argument.
     */
    D findByUnique(D dto);

    /**
     * Returns true if an entity with the given unique fields of the entity related to the DTO exists, false otherwise.
     *
     * @param dto The DTO that relates to the entity.
     *
     * @return True if an entity with the given unique fields of the entity related to the DTO exists, false otherwise.
     */
    boolean existsByUnique(D dto);

    /**
     * This method is used to determine if an entity exists by the unique filter.
     *
     * @param query The search request.
     *
     * @return A list of DTOs that are retrieved by the search request.
     */
    List<D> search(SearchRequestDTO<RD> query);

}
