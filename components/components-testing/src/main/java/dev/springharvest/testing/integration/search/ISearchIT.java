package dev.springharvest.testing.integration.search;

import dev.springharvest.search.model.queries.requests.filters.BaseFilterRequestDTO;
import dev.springhavest.common.models.dtos.BaseDTO;
import dev.springhavest.common.models.entities.BaseEntity;
import java.io.Serializable;

public interface ISearchIT<D extends BaseDTO<K>, E extends BaseEntity<K>, K extends Serializable,
    B extends BaseFilterRequestDTO> {

}
