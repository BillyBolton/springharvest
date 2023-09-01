package ca.billybolton.common.models.entities;

import ca.billybolton.common.models.domains.DomainModel;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * This is an abstract class for implementing the IBaseEntity<K> interface. It is used as a base class for all
 * Entities.
 *
 * @param <K> The type of the id (primary key) field
 *
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
public abstract class BaseEntity<K> extends DomainModel implements IBaseEntity<K> {

    @Id
    // TODO: Once Postgres db up, change strategy to other than IDENTITY to enable batching. (IDENTITY disables BATCH)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected K id;

}