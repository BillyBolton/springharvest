package dev.springharvest.testing.integration.search.helpers;

import dev.springharvest.search.model.queries.parameters.selections.SelectionDTO;
import dev.springharvest.search.model.queries.requests.filters.BaseFilterRequestDTO;
import dev.springharvest.search.model.queries.requests.search.SearchRequestDTO;
import dev.springharvest.testing.integration.shared.helpers.IBaseTestHelper;
import dev.springhavest.common.models.dtos.BaseDTO;
import dev.springhavest.common.models.entities.BaseEntity;
import io.restassured.response.ValidatableResponse;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface ISearchTestHelper<D extends BaseDTO<K>, E extends BaseEntity<K>, K extends Serializable,
        B extends BaseFilterRequestDTO>
        extends IBaseTestHelper<D, E, K> {

    String getIdPath();

    ValidatableResponse search(SearchRequestDTO<B> filters);

    B buildValidFilters();

    List<SelectionDTO> buildValidSelections(boolean selectAll);

    Map<String, List<SelectionDTO>> buildValidSelections();

    List<SearchRequestDTO> buildValidSearchRequestDTOs();

}
