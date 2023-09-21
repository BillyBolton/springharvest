package dev.springharvest.search.model.queries.parameters.filters;

import dev.springharvest.search.model.queries.parameters.base.BaseParameterBO;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * This class is used to represent the filter parameter business object.
 *
 * @author Billy Bolton
 * @see BaseParameterBO
 * @see IFilterParameter
 * @since 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FilterParameterBO extends BaseParameterBO implements IFilterParameter {

  @Builder.Default
  private CriteriaOperator operator = CriteriaOperator.EQUALS;
  @Builder.Default
  private Set<?> values = Set.of();

  //TODO: refactor once CriteriaSearch is better tested

  /**
   * This method is used to identify whether the values in the filter parameter are empty.
   *
   * @return The value of the filter parameter.
   */
  public boolean isEmpty() {
    return values.isEmpty() || values.stream()
        .allMatch(Objects::isNull);
  }

}
