package dev.springharvest.search.service;

import dev.springharvest.crud.mappers.IBaseModelMapper;
import dev.springharvest.search.mapper.queries.ISearchMapper;
import dev.springharvest.search.model.queries.requests.filters.BaseFilterBO;
import dev.springharvest.search.model.queries.requests.filters.BaseFilterDTO;
import dev.springharvest.search.model.queries.requests.filters.BaseFilterRequestBO;
import dev.springharvest.search.model.queries.requests.filters.BaseFilterRequestDTO;
import dev.springharvest.search.model.queries.requests.search.SearchRequestDTO;
import dev.springharvest.search.persistence.AbstractCriteriaSearchDaoImpl;
import dev.springharvest.search.persistence.ICriteriaSearchRepository;
import dev.springhavest.common.models.dtos.BaseDTO;
import dev.springhavest.common.models.entities.BaseEntity;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Set;

@Slf4j
public abstract class AbstractSearchService<D extends BaseDTO<K>, E extends BaseEntity<K>, K extends Serializable,
        RD extends BaseFilterRequestDTO, RB extends BaseFilterRequestBO, FD extends BaseFilterDTO,
        FB extends BaseFilterBO>
        implements ISearchService<D, K, RD> {

    protected IBaseModelMapper<D, E, K> baseMapper;
    protected ISearchMapper<RD, RB, FD, FB> filterMapper;
    protected ICriteriaSearchRepository<E, RB> criteriaSearchRepository;

    protected AbstractSearchService(IBaseModelMapper<D, E, K> baseMapper, ISearchMapper<RD, RB, FD, FB> filterMapper,
                                    AbstractCriteriaSearchDaoImpl<E, K, RB> criteriaSearchRepository) {
        this.baseMapper = baseMapper;
        this.filterMapper = filterMapper;
        this.criteriaSearchRepository = criteriaSearchRepository;
    }

    protected Class<E> getClazz() {
        ParameterizedType paramType = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<E>) paramType.getActualTypeArguments()[1];
    }

    @Override
    public D findByUnique(D dto) {
        return search(SearchRequestDTO.<RD>builder().filters(buildUniqueFilters(dto)).build()).stream()
                                                                                              .findFirst()
                                                                                              .orElse(null);
    }

    @Override
    public boolean existsByUnique(D dto) {

        SearchRequestDTO<RD> searchRequestDTO = SearchRequestDTO.<RD>builder().filters(buildUniqueFilters(dto)).build();

        return !searchRequestDTO.getFilters().isEmpty() &&
               criteriaSearchRepository.existsByUnique(filterMapper.toSearchRequest(searchRequestDTO));
    }

    @Override
    public List<D> search(SearchRequestDTO<RD> filterRequest) {
        var searchRequest = filterMapper.toSearchRequest(filterRequest);
        return entityToDto(criteriaSearchRepository.search(searchRequest));

    }

    protected abstract Set<RD> buildUniqueFilters(D dto);

    // TODO: Migrate mappers to controller

    protected D entityToDto(E entity) {
        return baseMapper.entityToDto(entity);
    }

    protected E dtoToEntity(D dto) {
        return baseMapper.dtoToEntity(dto);
    }

    protected List<D> entityToDto(List<E> entities) {
        return baseMapper.entityToDto(entities);
    }

    protected List<E> dtoToEntity(List<D> dtos) {
        return baseMapper.dtoToEntity(dtos);
    }

}
