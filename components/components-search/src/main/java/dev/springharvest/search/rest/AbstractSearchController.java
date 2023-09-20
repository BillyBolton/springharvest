package dev.springharvest.search.rest;

import dev.springharvest.crud.mappers.IBaseModelMapper;
import dev.springharvest.search.model.queries.requests.filters.BaseFilterBO;
import dev.springharvest.search.model.queries.requests.filters.BaseFilterDTO;
import dev.springharvest.search.model.queries.requests.filters.BaseFilterRequestBO;
import dev.springharvest.search.model.queries.requests.filters.BaseFilterRequestDTO;
import dev.springharvest.search.model.queries.requests.search.SearchRequestDTO;
import dev.springharvest.search.service.AbstractSearchService;
import dev.springhavest.common.constants.ControllerEndpoints;
import dev.springhavest.common.models.dtos.BaseDTO;
import dev.springhavest.common.models.entities.BaseEntity;
import java.io.Serializable;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
public class AbstractSearchController<D extends BaseDTO<K>, E extends BaseEntity<K>, K extends Serializable,
    RD extends BaseFilterRequestDTO, RB extends BaseFilterRequestBO, FD extends BaseFilterDTO,
    FB extends BaseFilterBO>
    implements ISearchController<RD, D, K> {

  protected IBaseModelMapper<D, E, K> baseModelMapper;
  protected AbstractSearchService<E, K, RD, RB, FD, FB> baseService;

  protected AbstractSearchController(IBaseModelMapper<D, E, K> baseModelMapper,
                                     AbstractSearchService<E, K, RD, RB, FD, FB> baseService) {
    this.baseModelMapper = baseModelMapper;
    this.baseService = baseService;
  }

  @Override
  @PostMapping(value = {ControllerEndpoints.SEARCH}, consumes = MediaType.APPLICATION_JSON_VALUE,
               produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<D>> search(@RequestBody SearchRequestDTO<RD> searchQuery) {
    List<D> dtos = baseModelMapper.entityToDto(baseService.search(searchQuery));
    return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(dtos);
  }

}
