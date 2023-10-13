package dev.springharvest.search.domains.base.models.queries.parameters.filters;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.springharvest.search.domains.base.models.queries.parameters.base.BaseParameterDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * This class is used to represent the filter parameter data transfer object.
 *
 * @author Billy Bolton
 * @see BaseParameterDTO
 * @since 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FilterParameterDTO extends BaseParameterDTO {

  @Schema(name = "operator", description = "The operator to use for the search.", example = "EQUALS")
  @Builder.Default
  private CriteriaOperator operator = CriteriaOperator.EQUALS;
  @Schema(name = "values", description = "The values that will be used to search on.", example = "[]")
  private Set<?> values;

  @JsonIgnore
  @Override
  public String getAlias() {
    return super.getAlias();
  }

}
