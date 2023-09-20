package dev.springharvest.crud.persistence;

import dev.springhavest.common.models.entities.BaseEntity;
import java.io.Serializable;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * This interface is used to define the contract for a base repository. It wraps the ListCrudRepository and ListPagingAndSortingRepository interfaces.
 *
 * @param <E> The type of the entity.
 * @param <K> The type of the id (primary key) field.
 * @see ListCrudRepository
 * @see ListPagingAndSortingRepository
 */

@NoRepositoryBean
public interface IBaseCrudRepository<E extends BaseEntity<K>, K extends Serializable>
    extends ListCrudRepository<E, K>, ListPagingAndSortingRepository<E, K> {

  long count();

}
