package dev.springharvest.crud.domains.base.services;

import dev.springharvest.shared.domains.base.models.entities.BaseEntity;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

/**
 * The interface used to define the contract for a base service.
 *
 * @param <E> The type of the Entity.
 * @param <K> The type of the id (primary key) field.
 */
public interface ICrudService<E extends BaseEntity<K>, K extends Serializable> {

  /**
   * Returns the number of entities of the entity domain.
   *
   * @return The number of entities of the entity domain.
   */
  long count();

  /**
   * Returns true if an entity with the given id exists, false otherwise.
   *
   * @param id The id of the entity to check for existence.
   * @return True if an entity with the given id exists, false otherwise.
   */
  boolean existsById(K id);

  /**
   * Returns the entity with the given id.
   *
   * @param id The id of the entity to retrieve.
   * @return The entity with the given id.
   */
  Optional<E> findById(K id);

  Optional<E> findByExample(E entity);

  Iterable<E> findAllByExample(E entity);

  List<E> findAllByIds(Set<K> ids);

  /**
   * Returns all entities of the entity domain.
   *
   * @return All entities of the entity domain.
   */
  Page<E> findAll(Pageable pageable);

  /**
   * Saves a new entity to the database if it does not already exist.
   *
   * @param dto The DTO to save.
   * @return The saved DTO.
   */
  E create(E dto);

  /**
   * Saves a list of new entities to the database if they do not already exist.
   *
   * @param dtos The list of DTOs to save.
   * @return The list of saved DTOs.
   */
  List<E> create(List<E> dtos);

  /**
   * Updates an existing entity in the database.
   *
   * @param dto The DTO to update.
   * @return The updated DTO.
   */
  E update(E dto);

  /**
   * Updates a list of existing entities in the database.
   *
   * @param dtos
   * @return The list of updated DTOs.
   */
  List<E> update(List<E> dtos);

  /**
   * Deletes an entity from the database.
   *
   * @param id The id of the entity to delete.
   */
  void deleteById(K id);

  /**
   * Deletes a list of entities from the database.
   *
   * @param ids The list of ids of the entities to delete.
   */
  void deleteById(List<K> ids);

}