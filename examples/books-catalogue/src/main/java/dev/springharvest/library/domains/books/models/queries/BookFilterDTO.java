package dev.springharvest.library.domains.books.models.queries;

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
public class BookFilterDTO extends BaseFilterDTO {

  @Schema(name = "id", description = "The id of the Book.")
  private FilterParameterDTO id;

  @Schema(name = "title", description = "The title of the Book.")
  private FilterParameterDTO title;

  @Schema(name = "genre", description = "The genre of the Book.")
  private FilterParameterDTO genre;

}
