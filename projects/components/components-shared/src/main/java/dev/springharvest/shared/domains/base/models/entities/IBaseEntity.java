package dev.springharvest.shared.domains.base.models.entities;

import java.io.Serializable;

/**
 * > A generic interface for the base Entity.
 *
 * @param <K> The type of the id (primary key) field
 * @author Billy Bolton
 * @since 1.0
 */
public interface IBaseEntity<K extends Serializable> {

  /**
   * This method is used to get the id of the Entity.
   *
   * @return The id of the Entity.
   */
  K getId();

}
