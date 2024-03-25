package dev.springharvest.search.domains.base.models.queries.requests.search;

import dev.springharvest.search.domains.base.models.queries.parameters.selections.SelectionDTO;
import dev.springharvest.search.domains.base.models.queries.requests.filters.BaseFilterRequestDTO;
import dev.springharvest.search.domains.base.models.queries.requests.pages.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * This class is used to represent the search request.
 *
 * @author Billy Bolton
 * @since 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SearchRequestDTO<F extends BaseFilterRequestDTO> {

  @Schema(name = "page", description = "The meta data for a pageable search request.")
  @Builder.Default
  private Page page = Page.builder()
      .build();

  @Schema(name = "selections",
          description = "The selections to apply to the search." + "The order of elements dictates the priority of the sorted attributes.")
  @Builder.Default
  private List<SelectionDTO> selections = List.of();

  @Schema(name = "filters", description = "The filters to apply to the search.")
  private Set<F> filters;

}

