package dev.springharvest.search.domains.base.services;

import dev.springharvest.search.domains.base.mappers.queries.ISearchMapper;
import dev.springharvest.search.domains.base.models.entities.EntityMetadata;
import dev.springharvest.search.domains.base.models.entities.IEntityMetadata;
import dev.springharvest.search.domains.base.models.queries.parameters.selections.SelectionDTO;
import dev.springharvest.search.domains.base.models.queries.requests.filters.BaseFilterBO;
import dev.springharvest.search.domains.base.models.queries.requests.filters.BaseFilterDTO;
import dev.springharvest.search.domains.base.models.queries.requests.filters.BaseFilterRequestBO;
import dev.springharvest.search.domains.base.models.queries.requests.filters.BaseFilterRequestDTO;
import dev.springharvest.search.domains.base.models.queries.requests.search.SearchRequestDTO;
import dev.springharvest.search.domains.base.persistence.AbstractCriteriaSearchDao;
import dev.springharvest.search.domains.base.persistence.ICriteriaSearchRepository;
import dev.springharvest.shared.domains.base.models.entities.BaseEntity;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

@Slf4j
public abstract class AbstractSearchService<E extends BaseEntity<K>, K extends Serializable,
    RD extends BaseFilterRequestDTO, RB extends BaseFilterRequestBO, FD extends BaseFilterDTO,
    FB extends BaseFilterBO>
    implements ISearchService<E, K, RD> {

  protected final IEntityMetadata<E> entityMetadata;
  protected ISearchMapper<E, K, RD, RB, FD, FB> filterMapper;
  protected ICriteriaSearchRepository<E, RB> searchRepository;

  protected AbstractSearchService(EntityMetadata<E> entityMetadata,
                                  ISearchMapper<E, K, RD, RB, FD, FB> filterMapper,
                                  AbstractCriteriaSearchDao<E, K, RB> searchRepository) {
    this.entityMetadata = entityMetadata;
    this.filterMapper = filterMapper;
    this.searchRepository = searchRepository;
  }

  protected Class<E> getClazz() {
    ParameterizedType paramType = (ParameterizedType) getClass().getGenericSuperclass();
    return (Class<E>) paramType.getActualTypeArguments()[1];
  }

  @Override
  public List<E> search(SearchRequestDTO<RD> filterRequest) {
    var searchRequest = filterMapper.toSearchRequest(filterRequest);
    return searchRepository.search(searchRequest);
  }

  @Override
  public Integer count(SearchRequestDTO<RD> filterRequest) {
    Set<String> paths = CollectionUtils.isNotEmpty(entityMetadata.getRootPaths()) ? entityMetadata.getRootPaths() : entityMetadata.getNestedPaths();
    filterRequest.setSelections(List.of(SelectionDTO.builder()
                                            .alias(paths.stream()
                                                       .findFirst()
                                                       .orElseThrow(() -> new RuntimeException("No paths found for entity " + entityMetadata.getDomainName())))
                                            .build()));
    return search(filterRequest).size();
  }

  @Override
  public boolean exists(SearchRequestDTO<RD> filterRequest) {
    return count(filterRequest) > 0;
  }

}
