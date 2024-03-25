package dev.springharvest.library.domains.authors.models.queries;

import dev.springharvest.search.domains.base.models.queries.parameters.filters.FilterParameterDTO;
import dev.springharvest.search.domains.base.models.queries.requests.filters.BaseFilterDTO;
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
public class AuthorFilterDTO extends BaseFilterDTO {

  @Schema(name = "id", description = "The id of the Author.")
  private FilterParameterDTO id;

  @Schema(name = "name", description = "The name of the Author.")
  private FilterParameterDTO name;

}
