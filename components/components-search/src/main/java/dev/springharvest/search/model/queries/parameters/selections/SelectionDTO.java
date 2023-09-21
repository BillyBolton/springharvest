package dev.springharvest.search.model.queries.parameters.selections;

import dev.springharvest.search.model.queries.parameters.base.BaseParameterDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * This class is used to represent the base selection data transfer object.
 *
 * @author Billy Bolton
 * @see BaseParameterDTO
 * @since 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SelectionDTO extends BaseParameterDTO {

  @Schema(name = "isAscending", description = "Set to true to make the sort in ascending order for this attribute.",
          example = "true")
  private Boolean isAscending;

  @Schema(name = "priority",
          description = "The sorting priority for this attribute. The higher the number, the higher the priority.",
          example = "0")
  private Integer priority;

  @Schema(name = "alias", description = "The alias of the field to sort on.", example = "entityName.id")
  @Override
  public String getAlias() {
    return super.getAlias();
  }

}
