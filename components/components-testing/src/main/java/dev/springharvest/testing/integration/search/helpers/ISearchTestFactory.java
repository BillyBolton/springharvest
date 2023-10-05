package dev.springharvest.testing.integration.search.helpers;

import dev.springharvest.search.model.queries.parameters.selections.SelectionDTO;
import dev.springharvest.search.model.queries.requests.filters.BaseFilterRequestDTO;
import dev.springharvest.search.model.queries.requests.search.SearchRequestDTO;
import java.util.List;

public interface ISearchTestFactory<B extends BaseFilterRequestDTO> {

  String getIdPath();

  B buildValidFilters();

  List<SelectionDTO> buildValidSelections(boolean isEmpty);

  List<SearchRequestDTO<B>> buildValidSearchRequestDTOs();

}
