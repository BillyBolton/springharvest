package dev.springharvest.testing.integration.search;

import dev.springharvest.search.model.queries.requests.filters.BaseFilterRequestDTO;
import dev.springhavest.common.models.dtos.BaseDTO;
import dev.springhavest.common.models.entities.BaseEntity;

public interface ISearchIT<D extends BaseDTO<K>, E extends BaseEntity<K>, K, B extends BaseFilterRequestDTO> {}
