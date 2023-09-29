package dev.springharvest.search.mappers.queries;


import dev.springharvest.search.global.IGlobalClazzResolver;
import dev.springharvest.search.model.entities.IEntityMetadata;
import dev.springharvest.search.model.queries.requests.filters.BaseFilterBO;
import dev.springharvest.search.model.queries.requests.filters.BaseFilterDTO;
import dev.springharvest.search.model.queries.requests.filters.BaseFilterRequestBO;
import dev.springharvest.search.model.queries.requests.filters.BaseFilterRequestDTO;
import dev.springharvest.search.model.queries.requests.search.SearchRequest;
import dev.springharvest.search.model.queries.requests.search.SearchRequestDTO;
import dev.springhavest.common.models.entities.BaseEntity;
import java.io.Serializable;
import java.util.Set;

/**
 * This interface contracts the methods that are used to map the search request objects.
 *
 * @param <RD> An object that extends the BaseFilterRequestDTO
 * @param <RB> An object that extends the BaseFilterRequestBO
 * @param <D>  An object that extends the BaseFilterDTO
 * @param <B>  An object that extends the BaseFilterBO
 * @see BaseFilterRequestDTO
 * @see BaseFilterRequestBO
 * @see BaseFilterDTO
 * @see BaseFilterBO
 * @see IParameterMapper
 */
public interface ISearchMapper<E extends BaseEntity<K>, K extends Serializable, RD extends BaseFilterRequestDTO, RB extends BaseFilterRequestBO,
    D extends BaseFilterDTO, B extends BaseFilterBO>
    extends IParameterMapper, IFilterMapper<RD, RB, D, B> {

  /**
   * This method is used to map a SearchRequestDTO object to a SearchRequest.
   *
   * @param searchRequestDTO
   * @return The SearchRequest object that will be mapped to.
   * @see SearchRequestDTO
   * @see SearchRequest
   */
  SearchRequest<RB> toSearchRequest(SearchRequestDTO<RD> searchRequestDTO);

  @Override
  default Class<?> getClazz(String path) {
    return getGlobalClazzResolver().getClazz(path);
  }

  IGlobalClazzResolver getGlobalClazzResolver();

  @Override
  default Set<String> getRoots() {
    return getEntityMetadata().getRootPaths();
  }

  IEntityMetadata<E> getEntityMetadata();

}
