package dev.springharvest.crud.service;

import dev.springhavest.common.models.dtos.BaseDTO;

import java.util.List;

/**
 * The interface used to define the contract for a base service.
 *
 * @param <D> The type of the DTO.
 * @param <K> The type of the id (primary key) field.
 */
public interface IBaseService<D extends BaseDTO<K>, K> {

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
     *
     * @return True if an entity with the given id exists, false otherwise.
     */
    boolean existsById(K id);

    /**
     * Returns the entity with the given id.
     *
     * @param id The id of the entity to retrieve.
     *
     * @return The entity with the given id.
     */
    D findById(K id);

    List<D> findAllByIds(List<K> ids);

    /**
     * Returns all entities of the entity domain.
     *
     * @return All entities of the entity domain.
     */
    List<D> findAll();

    /**
     * Saves a new entity to the database if it does not already exist.
     *
     * @param dto The DTO to save.
     *
     * @return The saved DTO.
     */
    D create(D dto);

    /**
     * Saves a list of new entities to the database if they do not already exist.
     *
     * @param dtos The list of DTOs to save.
     *
     * @return The list of saved DTOs.
     */
    List<D> create(List<D> dtos);

    /**
     * Updates an existing entity in the database.
     *
     * @param dto The DTO to update.
     *
     * @return The updated DTO.
     */
    D update(D dto);

    /**
     * Updates a list of existing entities in the database.
     *
     * @param dtos
     *
     * @return The list of updated DTOs.
     */
    List<D> update(List<D> dtos);

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