package ca.billybolton.crud.rest;

import ca.billybolton.common.models.dtos.BaseDTO;
import ca.billybolton.common.models.entities.BaseEntity;
import ca.billybolton.crud.rest.constants.ControllerUri;
import ca.billybolton.crud.service.AbstractBaseCrudService;
import com.billybolton.errors.models.ClientException;
import com.billybolton.errors.models.ExceptionDetail;
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

import java.util.List;

/**
 * A generic implementation of the IBaseCrudController interface.
 *
 * @param <D> The DTO type
 * @param <E> The entity type
 * @param <K> The type of the id (primary key) field
 *
 * @author Billy Bolton
 * @see IBaseCrudController
 * @see ControllerUri
 * @see AbstractBaseCrudService
 * @see BaseDTO
 * @see BaseEntity
 * @since 1.0
 */
@Slf4j
public abstract class AbstractBaseCrudController<
        D extends BaseDTO<K>,
        E extends BaseEntity<K>,
        K> implements IBaseCrudController<D, K> {

    protected AbstractBaseCrudService<D, E, K> baseService;

    protected AbstractBaseCrudController(AbstractBaseCrudService<D, E, K> baseService) {
        this.baseService = baseService;
    }

    @Override
    @GetMapping(value = {ControllerUri.COUNT},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> count() {
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(baseService.count());
    }

    @Override
    @GetMapping(value = {ControllerUri.FIND_BY_ID},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<D> findById(@PathVariable(required = true) K id) {
        D dto = baseService.findById(id);
        if (dto == null) {
            throw ClientException
                    .builder().details(List.of(ExceptionDetail.builder()
                            .field("id")
                            .message(String.format("No entity found with id: %s", id))
                            .build())).build();
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(dto);
    }

    @Override
    @GetMapping(value = {ControllerUri.FIND_ALL},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<D>> findAll() {
        List<D> dtos = baseService.findAll();
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(dtos);
    }

    @Override
    @PostMapping(value = {ControllerUri.CREATE},
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<D> create(@RequestBody(required = true) D dto) {
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(baseService.create(dto));
    }

    @Override
    @PostMapping(value = {ControllerUri.CREATE_ALL},
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<D>> createAll(@RequestBody(required = true) List<D> dtos) {
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(baseService.create(dtos));
    }

    @Override
    @PatchMapping(value = {ControllerUri.UPDATE_BY_ID},
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<D> update(@PathVariable(required = true) K id,
                                    @RequestBody(required = true) D dto) {
        dto.setId(id);
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(baseService.update(dto));
    }

    @Override
    @PatchMapping(value = {ControllerUri.UPDATE_ALL},
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<D>> updateAll(@RequestBody(required = true) List<D> dtos) {
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(baseService.update(dtos));
    }

    @Override
    @DeleteMapping(value = {ControllerUri.DELETE_BY_ID})
    public ResponseEntity<Void> deleteById(@PathVariable(required = true) K id) {
        baseService.deleteById(id);
        return ResponseEntity.status(204).build();
    }

    @Override
    @DeleteMapping(value = {ControllerUri.DELETE_ALL},
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteAllById(@RequestBody(required = true) List<K> ids) {
        baseService.deleteById(ids);
        return ResponseEntity.status(204).build();
    }

}