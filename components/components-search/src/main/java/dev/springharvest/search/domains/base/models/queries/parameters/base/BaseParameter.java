package dev.springharvest.search.domains.base.models.queries.parameters.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * This class is used to represent the base parameter.
 *
 * @author Billy Bolton
 * @since 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseParameter {

  @JsonIgnore
  protected Boolean isJoined;

  protected String alias;

  @JsonIgnore
  protected String path;

  @JsonIgnore
  protected Class<?> clazz;

}

