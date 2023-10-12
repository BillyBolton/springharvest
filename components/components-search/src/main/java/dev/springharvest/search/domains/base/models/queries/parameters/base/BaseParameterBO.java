package dev.springharvest.search.domains.base.models.queries.parameters.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
 * This class is used to represent the base parameter business object.
 *
 * @author Billy Bolton
 * @see BaseParameter
 * @see IBaseParameterBO
 * @since 1.0
 */
@Data
@SuperBuilder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public abstract class BaseParameterBO extends BaseParameter implements IBaseParameterBO {

  public boolean isJoined() {
    return isJoined;
  }

}

