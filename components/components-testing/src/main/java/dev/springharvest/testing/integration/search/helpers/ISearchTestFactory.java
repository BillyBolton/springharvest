package dev.springharvest.testing.integration.search.helpers;

import dev.springharvest.search.model.queries.parameters.selections.SelectionDTO;
import dev.springharvest.search.model.queries.requests.filters.BaseFilterRequestDTO;
import dev.springharvest.search.model.queries.requests.search.SearchRequestDTO;
import java.util.List;
import java.util.Map;

public interface ISearchTestFactory<B extends BaseFilterRequestDTO> {

  String getIdPath();

  B buildValidFilters();

  List<SelectionDTO> buildValidSelections(boolean selectAll);

  Map<String, List<SelectionDTO>> buildValidSelections();

  List<SearchRequestDTO<B>> buildValidSearchRequestDTOs();

}
