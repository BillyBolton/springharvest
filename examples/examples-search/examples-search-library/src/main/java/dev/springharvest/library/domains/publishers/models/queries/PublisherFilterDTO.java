package dev.springharvest.library.domains.publishers.models.queries;

import dev.springharvest.search.model.queries.parameters.filters.FilterParameterDTO;
import dev.springharvest.search.model.queries.requests.filters.BaseFilterDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PublisherFilterDTO extends BaseFilterDTO {

  @Schema(name = "id", description = "The id of the Publisher.")
  private FilterParameterDTO id;

  @Schema(name = "name", description = "The name of the Publisher.")
  private FilterParameterDTO name;

}
