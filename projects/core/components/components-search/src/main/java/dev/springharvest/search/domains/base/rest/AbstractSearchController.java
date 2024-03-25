package dev.springharvest.search.domains.base.rest;

import dev.springharvest.search.domains.base.models.queries.requests.filters.BaseFilterBO;
import dev.springharvest.search.domains.base.models.queries.requests.filters.BaseFilterDTO;
import dev.springharvest.search.domains.base.models.queries.requests.filters.BaseFilterRequestBO;
import dev.springharvest.search.domains.base.models.queries.requests.filters.BaseFilterRequestDTO;
import dev.springharvest.search.domains.base.models.queries.requests.search.SearchRequestDTO;
import dev.springharvest.search.domains.base.rest.constants.SearchControllerUri;
import dev.springharvest.search.domains.base.services.AbstractSearchService;
import dev.springharvest.shared.domains.base.mappers.IBaseModelMapper;
import dev.springharvest.shared.domains.base.models.dtos.BaseDTO;
import dev.springharvest.shared.domains.base.models.entities.BaseEntity;
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

  protected IBaseModelMapper<D, E, K> modelMapper;
  protected AbstractSearchService<E, K, RD, RB, FD, FB> searchService;

  protected AbstractSearchController(IBaseModelMapper<D, E, K> modelMapper,
                                     AbstractSearchService<E, K, RD, RB, FD, FB> searchService) {
    this.modelMapper = modelMapper;
    this.searchService = searchService;
  }

  @Override
  @PostMapping(value = {SearchControllerUri.SEARCH}, consumes = MediaType.APPLICATION_JSON_VALUE,
               produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<D>> search(@RequestBody SearchRequestDTO<RD> searchQuery) {
    List<D> dtos = modelMapper.entityToDto(searchService.search(searchQuery));
    return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(dtos);
  }

  @Override
  @PostMapping(value = {SearchControllerUri.COUNT}, consumes = MediaType.APPLICATION_JSON_VALUE,
               produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Integer> count(@RequestBody SearchRequestDTO<RD> searchQuery) {
    Integer count = searchService.count(searchQuery);
    return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(count);
  }

  @Override
  @PostMapping(value = {SearchControllerUri.EXISTS}, consumes = MediaType.APPLICATION_JSON_VALUE,
               produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Boolean> exists(@RequestBody SearchRequestDTO<RD> searchQuery) {
    boolean exists = searchService.exists(searchQuery);
    return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(exists);
  }

}
