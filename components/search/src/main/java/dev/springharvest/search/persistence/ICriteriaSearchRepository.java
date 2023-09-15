package dev.springharvest.search.persistence;

import dev.springharvest.search.model.queries.requests.filters.BaseFilterRequestBO;
import dev.springharvest.search.model.queries.requests.search.SearchRequest;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

/**
 * This interface is used to define the contract for a criteria search repository.
 *
 * @param <E>
 * @param <RB>
 */
@NoRepositoryBean
public interface ICriteriaSearchRepository<E, RB extends BaseFilterRequestBO> {

    /**
     * This method is used to determine if an entity exists by the unique filter.
     *
     * @param searchRequest The search request.
     *
     * @return True if the entity exists, false otherwise.
     */
    boolean existsByUnique(SearchRequest<RB> searchRequest);

    /**
     * This method is used to determine if an entity exists by the unique filter.
     *
     * @param searchRequest The search request.
     *
     * @return True if the entity exists, false otherwise.
     */
    boolean exists(SearchRequest<RB> searchRequest);

    /**
     * Counts the number of entities that match the search request.
     *
     * @param searchRequest The search request that will be used to identify the entity.
     *
     * @return The number of entities that match the search request.
     */
    int count(SearchRequest<RB> searchRequest);

    /**
     * Searches for entities that match the criteria search request.
     *
     * @param searchRequest The search request that will be used to identify the entity.
     *
     * @return The list of entities that match the search request.
     */
    List<E> search(SearchRequest<RB> searchRequest);

}
