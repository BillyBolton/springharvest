package dev.springharvest.testing.integration.search.helpers;

import dev.springharvest.search.model.entities.EntityMetadata;
import dev.springharvest.search.model.entities.IEntityMetadata;
import dev.springharvest.search.model.queries.parameters.selections.SelectionDTO;
import dev.springharvest.search.model.queries.requests.filters.BaseFilterRequestDTO;
import dev.springharvest.search.model.queries.requests.search.SearchRequestDTO;
import dev.springhavest.common.models.domains.DomainModel;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public abstract class AbstractSearchTestFactoryImpl<M extends DomainModel, B extends BaseFilterRequestDTO>
    implements ISearchTestFactory<B> {

  protected final IEntityMetadata<M> entityMetadata;
  protected final String idPath;

  protected AbstractSearchTestFactoryImpl(EntityMetadata<M> entityMetadata) {
    this.entityMetadata = entityMetadata;
    this.idPath = entityMetadata.getDomainName() + "." + "id";
  }

  @Override
  public Map<String, List<SelectionDTO>> buildValidSelections() {

    Map<String, List<SelectionDTO>> selections = new HashMap<>();

    selections.put("id", List.of(SelectionDTO.builder().alias(getIdPath()).build()));
    selections.put("empty", List.of());
    selections.put("all", buildValidSelections(false));

    return selections;

  }

  @Override
  public List<SelectionDTO> buildValidSelections(boolean selectAll) {

    if (selectAll) {
      return List.of();
    }

    return entityMetadata.getPaths().stream().map(path -> SelectionDTO.builder().alias(path).build()).collect(Collectors.toList());

  }

  @Override
  public List<SearchRequestDTO<B>> buildValidSearchRequestDTOs() {

    List<SearchRequestDTO<B>> requests = new LinkedList<>();

    Map<String, List<SelectionDTO>> selections = buildValidSelections();

    // TODO make parameterized. Only using the query that selects id for now.
    requests.add(SearchRequestDTO.<B>builder()
                     .selections(selections.get("id"))
                     .filters(Set.of(buildValidFilters()))
                     .build());

    return requests;
  }

}
