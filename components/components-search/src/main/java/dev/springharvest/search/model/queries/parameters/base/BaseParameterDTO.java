package dev.springharvest.search.model.queries.parameters.base;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
 * This class is used to represent the base parameter data transfer object.
 *
 * @author Billy Bolton
 * @see BaseParameter
 * @since 1.0
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public abstract class BaseParameterDTO extends BaseParameter {

  public BaseParameterDTO() {
    super();
  }

}

