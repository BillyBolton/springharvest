package dev.springharvest.crud.domains.base.rest;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import dev.springharvest.crud.domains.base.rest.constants.CrudControllerUri;
import dev.springharvest.crud.domains.base.services.AbstractCrudService;
import dev.springharvest.errors.models.ClientException;
import dev.springharvest.errors.models.ExceptionDetail;
import dev.springharvest.shared.domains.base.mappers.CyclicMappingHandler;
import dev.springharvest.shared.domains.base.mappers.IBaseModelMapper;
import dev.springharvest.shared.domains.base.models.dtos.BaseDTO;
import dev.springharvest.shared.domains.base.models.entities.BaseEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

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

  protected IBaseModelMapper<D, E, K> modelMapper;
  protected AbstractCrudService<E, K> crudService;

  protected AbstractCrudController(IBaseModelMapper<D, E, K> modelMapper,
                                   AbstractCrudService<E, K> crudService) {
    this.modelMapper = modelMapper;
    this.crudService = crudService;
  }

  @Override
  @GetMapping(value = {CrudControllerUri.COUNT}, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Long> count() {
    return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(crudService.count());
  }

  @Override
  @GetMapping(value = {CrudControllerUri.FIND},
              produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<D> findById(@RequestParam(required = true) K id) {
    Optional<E> entity = crudService.findById(id);
    if (entity.isEmpty()) {
      throw ClientException.builder()
          .details(List.of(ExceptionDetail.builder()
                               .field("id")
                               .message(String.format("No entity found with id: %s", id))
                               .build()))
          .build();
    }
    D dto = modelMapper.entityToDto(entity.get());
    return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(dto);
  }

  @Override
  @GetMapping(value = {CrudControllerUri.FIND_ALL},
              produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Page<D>> findAll(@RequestParam(name = "pageNumber", required = false) Integer pageNumber,
                                         @RequestParam(name = "pageSize", required = false) Integer pageSize,
                                         @RequestParam(name = "sorts", required = false) List<String> sorts) {
    if (pageNumber == null || pageNumber < 0) {
      pageNumber = 0;
    }

    if (pageSize == null || pageSize < 0) {
      pageSize = Integer.MAX_VALUE;
    }

    Sort sort = CollectionUtils.isNotEmpty(sorts) ?
                Sort.by(sorts.stream().map(order -> {
                  String[] orderSplit = order.split("-");
                  if (orderSplit.length != 2) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid sort parameter: " + order);
                  }
                  return new Sort.Order(Sort.Direction.fromString(orderSplit[1]), orderSplit[0]);
                }).toList()) :
                Sort.unsorted();
    PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
    Page<E> entities = crudService.findAll(PageRequest.of(pageNumber, pageSize, sort));
    Page<D> dtos = entities.hasContent() ? modelMapper.pagedEntityToPagedDto(entities) : Page.empty(pageRequest);
    return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(dtos);
  }

  @Override
  @PostMapping(value = {CrudControllerUri.CREATE},
               consumes = MediaType.APPLICATION_JSON_VALUE,
               produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<D> create(@RequestBody(required = true) D dto) {
    E entity = modelMapper.dtoToEntity(dto);
    entity = crudService.create(entity);
    D createdDTO = modelMapper.entityToDto(entity);
    return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(createdDTO);
  }

  @Override
  @PostMapping(value = {CrudControllerUri.CREATE_ALL}, consumes = MediaType.APPLICATION_JSON_VALUE,
               produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<D>> createAll(@RequestBody(required = true) List<D> dtos) {
    List<E> entities = modelMapper.dtoToEntity(dtos);
    entities = crudService.create(entities);
    return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(modelMapper.entityToDto(entities));
  }

  @Override
  @PatchMapping(value = {CrudControllerUri.UPDATE}, consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<D> update(@RequestParam(name = "id", required = true) K id, @RequestBody(required = true) D dto) {
    dto.setId(id);

    Optional<E> optFound = crudService.findById(id);
    if (optFound.isEmpty()) {
      throw new EntityNotFoundException(String.format("No entity found with id: %s", id));
    }

    D found = modelMapper.setDirtyFields(dto, modelMapper.entityToDto(optFound.get()), new CyclicMappingHandler());
    E updated = crudService.update(modelMapper.dtoToEntity(found));
    return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(modelMapper.entityToDto(updated));
  }

  @Override
  @PatchMapping(value = {CrudControllerUri.UPDATE_ALL},
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<D>> updateAll(@RequestBody(required = true) List<D> dtos) {

    Map<K, D> dtosById = dtos.stream().collect(Collectors.toMap(BaseDTO::getId, java.util.function.Function.identity()));

    List<D> updated = modelMapper.entityToDto(crudService.findAllByIds(dtosById.keySet())).stream()
        .map(foundDto -> modelMapper.setDirtyFields(dtosById.get(foundDto.getId()), foundDto, new CyclicMappingHandler())).toList();

    List<E> entities = modelMapper.dtoToEntity(updated);
    entities = crudService.update(entities);
    return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(modelMapper.entityToDto(entities));
  }

  @Override
  @DeleteMapping(value = {CrudControllerUri.DELETE})
  public ResponseEntity<Void> deleteById(@RequestParam(name = "id", required = true) K id) {
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