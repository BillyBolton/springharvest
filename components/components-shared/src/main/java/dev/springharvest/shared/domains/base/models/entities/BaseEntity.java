package dev.springharvest.shared.domains.base.models.entities;

import dev.springharvest.shared.domains.DomainModel;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * This is an abstract class for implementing the IBaseEntity<K> interface. It is used as a base class for all Entities.
 *
 * @param <K> The type of the id (primary key) field
 * @author Billy Bolton
 * @see IBaseEntity
 * @since 1.0
 */
@Data
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public abstract class BaseEntity<K extends Serializable> extends DomainModel implements IBaseEntity<K> {

  @Id
  @GeneratedValue
  protected K id;

  @Override
  public boolean isEmpty() {
    return id == null;
  }

}