package dev.springharvest.crud.rest;

import dev.springharvest.crud.mappers.IBaseModelMapper;
import dev.springharvest.crud.rest.constants.CrudControllerUri;
import dev.springharvest.crud.service.AbstractCrudService;
import dev.springharvest.errors.models.ClientException;
import dev.springharvest.errors.models.ExceptionDetail;
import dev.springhavest.common.models.dtos.BaseDTO;
import dev.springhavest.common.models.entities.BaseEntity;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * A generic implementation of the IBaseCrudController interface.
 *
 * @param <D> The DTO type
 * @param <E> The entity type
 * @param <K> The type of the id (primary key) field
 * @author Billy Bolton
 * @see ICrudController
 * @see CrudControllerUri
 * @see AbstractCrudService
 * @see BaseDTO
 * @see BaseEntity
 * @since 1.0
 */
@Slf4j
public abstract class AbstractCrudController<D extends BaseDTO<K>, E extends BaseEntity<K>, K extends Serializable>
    implements ICrudController<D, K> {

  protected IBaseModelMapper<D, E, K> baseModelMapper;
  protected AbstractCrudService<E, K> crudService;

  protected AbstractCrudController(IBaseModelMapper<D, E, K> baseModelMapper,
                                   AbstractCrudService<E, K> crudService) {
    this.baseModelMapper = baseModelMapper;
    this.crudService = crudService;
  }

  @Override
  @GetMapping(value = {CrudControllerUri.COUNT}, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Long> count() {
    return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(crudService.count());
  }

  @Override
  @GetMapping(value = {CrudControllerUri.FIND_BY_ID}, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<D> findById(@PathVariable(required = true) K id) {
    Optional<E> entity = crudService.findById(id);
    if (entity.isEmpty()) {
      throw ClientException.builder()
          .details(List.of(ExceptionDetail.builder()
                               .field("id")
                               .message(String.format("No entity found with id: %s",
                                                      id))
                               .build()))
          .build();
    }
    D dto = baseModelMapper.entityToDto(entity.get());
    return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(dto);
  }

  @Override
  @GetMapping(value = {CrudControllerUri.FIND_ALL}, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<D>> findAll() {
    List<E> entities = crudService.findAll();
    return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(baseModelMapper.entityToDto(entities));
  }

  @Override
  @PostMapping(value = {CrudControllerUri.CREATE}, consumes = MediaType.APPLICATION_JSON_VALUE,
               produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<D> create(@RequestBody(required = true) D dto) {
    E entity = baseModelMapper.dtoToEntity(dto);
    entity = crudService.create(entity);
    return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(baseModelMapper.entityToDto(entity));
  }

  @Override
  @PostMapping(value = {CrudControllerUri.CREATE_ALL}, consumes = MediaType.APPLICATION_JSON_VALUE,
               produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<D>> createAll(@RequestBody(required = true) List<D> dtos) {
    List<E> entities = baseModelMapper.dtoToEntity(dtos);
    entities = crudService.create(entities);
    return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(baseModelMapper.entityToDto(entities));
  }

  @Override
  @PatchMapping(value = {CrudControllerUri.UPDATE_BY_ID}, consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<D> update(@PathVariable(required = true) K id, @RequestBody(required = true) D dto) {
    dto.setId(id);
    E entity = baseModelMapper.dtoToEntity(dto);
    entity = crudService.update(entity);
    return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(baseModelMapper.entityToDto(entity));
  }

  @Override
  @PatchMapping(value = {CrudControllerUri.UPDATE_ALL}, consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<D>> updateAll(@RequestBody(required = true) List<D> dtos) {
    List<E> entities = baseModelMapper.dtoToEntity(dtos);
    entities = crudService.update(entities);
    return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(baseModelMapper.entityToDto(entities));
  }

  @Override
  @DeleteMapping(value = {CrudControllerUri.DELETE_BY_ID})
  public ResponseEntity<Void> deleteById(@PathVariable(required = true) K id) {
    crudService.deleteById(id);
    return ResponseEntity.status(204).build();
  }

  @Override
  @DeleteMapping(value = {CrudControllerUri.DELETE_ALL}, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> deleteAllById(@RequestBody(required = true) List<K> ids) {
    crudService.deleteById(ids);
    return ResponseEntity.status(204).build();
  }

}