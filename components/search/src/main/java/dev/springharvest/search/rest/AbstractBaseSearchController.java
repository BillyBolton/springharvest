package dev.springharvest.search.rest;

import dev.springharvest.search.model.queries.requests.filters.BaseFilterBO;
import dev.springharvest.search.model.queries.requests.filters.BaseFilterDTO;
import dev.springharvest.search.model.queries.requests.filters.BaseFilterRequestBO;
import dev.springharvest.search.model.queries.requests.filters.BaseFilterRequestDTO;
import dev.springharvest.search.model.queries.requests.search.SearchRequestDTO;
import dev.springharvest.search.service.AbstractSearchService;
import dev.springhavest.common.constants.ControllerEndpoints;
import dev.springhavest.common.models.dtos.BaseDTO;
import dev.springhavest.common.models.entities.BaseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Slf4j
public class AbstractBaseSearchController<D extends BaseDTO<K>, E extends BaseEntity<K>, K,
        RD extends BaseFilterRequestDTO, RB extends BaseFilterRequestBO, FD extends BaseFilterDTO,
        FB extends BaseFilterBO>
        implements IBaseSearchController<RD, D, K> {

    protected AbstractSearchService<D, E, K, RD, RB, FD, FB> baseService;

    protected AbstractBaseSearchController(AbstractSearchService<D, E, K, RD, RB, FD, FB> baseService) {
        this.baseService = baseService;
    }

    @Override
    @PostMapping(value = {ControllerEndpoints.SEARCH}, consumes = MediaType.APPLICATION_JSON_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<D>> search(@RequestBody SearchRequestDTO<RD> searchQuery) {
        List<D> dtos = baseService.search(searchQuery);
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(dtos);
    }

}