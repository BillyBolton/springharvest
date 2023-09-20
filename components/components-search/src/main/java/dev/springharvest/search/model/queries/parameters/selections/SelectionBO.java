package dev.springharvest.search.model.queries.parameters.selections;

import dev.springharvest.search.model.queries.parameters.base.BaseParameterBO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * This class is used to represent the base selection business object.
 *
 * @author Billy Bolton
 * @see BaseParameterBO
 * @since 1.0
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class SelectionBO extends BaseParameterBO {

  @Builder.Default
  private Boolean isAscending = true;

  private Integer priority;

}
