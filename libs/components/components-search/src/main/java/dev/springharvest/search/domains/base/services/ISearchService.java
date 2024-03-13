package dev.springharvest.search.domains.base.services;

import dev.springharvest.search.domains.base.models.queries.requests.filters.BaseFilterRequestDTO;
import dev.springharvest.search.domains.base.models.queries.requests.search.SearchRequestDTO;
import dev.springharvest.shared.domains.base.models.entities.BaseEntity;
import java.io.Serializable;
import java.util.List;

/**
 * The interface used to define the contract for a base service.
 *
 * @param <E>  The type of the Entity.
 * @param <K>  The type of the id (primary key) field.
 * @param <RD> The type of the request DTO.
 */
public interface ISearchService<E extends BaseEntity<K>, K extends Serializable, RD extends BaseFilterRequestDTO> {

  /**
   * This method is used to determine if an entity exists by the unique filter.
   *
   * @param query The search request.
   * @return A list of DTOs that are retrieved by the search request.
   */
  List<E> search(SearchRequestDTO<RD> query);

  Integer count(SearchRequestDTO<RD> query);

  boolean exists(SearchRequestDTO<RD> query);

}
