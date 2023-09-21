package dev.springharvest.crud.rest;

import dev.springhavest.common.models.dtos.BaseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.io.Serializable;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * This interface is used to define the contract for a base controller. The @Operation annotation is used to define the OpenAPI specification for the
 * controller.
 *
 * @param <D> The DTO object for a domain
 * @param <K> The type of the id (primary key) field pertaining to the entity relating to the DTO
 */
public interface IBaseCrudController<D extends BaseDTO<K>, K extends Serializable> {

  @Operation(operationId = "count", summary = "Counts the entities.",
             description = "Use this API to retrieve an number of entities in its domain.",
             responses = {@ApiResponse(responseCode = "200", description = "The number of entities in its domain.")})
  ResponseEntity<Long> count();

  @Operation(operationId = "findById", summary = "Retrieves the entity.",
             description = "Use this API to retrieve an entity by their primary key id.",
             parameters = {@Parameter(description = "The primary key id of the entity to be retrieved.", name = "id",
                                      required = true)}, responses = {@ApiResponse(responseCode = "200",
                                                                                   description = "The entity with " +
                                                                                                 "the given primary" +
                                                                                                 " key id")})
  ResponseEntity<D> findById(@PathVariable(required = true) K id);

  @Deprecated(since = "1.0", forRemoval = false)
  @Operation(operationId = "findAll", summary = "Retrieves a list of entities.",
             description = "Use this API to retrieve an entity by their primary key id.",
             responses = {@ApiResponse(responseCode = "200", description = "The retrieved entities.")},
             deprecated = true)
  ResponseEntity<List<D>> findAll();

  @Operation(operationId = "create", summary = "Creates an entity.",
             description = "Use this API to create a new entity.",
             requestBody = @RequestBody(description = "The DTO used to create the entity.", required = true),
             responses = {@ApiResponse(responseCode = "200",
                                       description = "The entity with their generated primary key id.")})
  ResponseEntity<D> create(@RequestBody D dto);

  @Operation(operationId = "createAll", summary = "Creates entities.",
             description = "Use this API to create a new entity.",
             requestBody = @RequestBody(description = "A list of  DTOs used to create the entities.",
                                        required = true), responses = {@ApiResponse(responseCode = "200",
                                                                                    description = "The entities " +
                                                                                                  "created with " +
                                                                                                  "their generated " +
                                                                                                  "primary key ids" +
                                                                                                  ".")})
  ResponseEntity<List<D>> createAll(@RequestBody List<D> dtos);

  @Deprecated(since = "1.0", forRemoval = false)
  @Operation(operationId = "update", summary = "Updates an entity.",
             description = "Use this API to partially update a new entity.",
             requestBody = @RequestBody(description = """
                 The DTO used to update its entity.
                 Only the attributes that are changed need to be passed into the dto.
                 The id in the DTO is ignored.
                 """, required = true),
             responses = {@ApiResponse(responseCode = "200", description = "The updated entity.")}, deprecated = true)
  ResponseEntity<D> update(@PathVariable(required = true) K id, @RequestBody D dto);

  @Operation(operationId = "update", summary = "Updates an entity.",
             description = "Use this API to partially update a new entity.",
             requestBody = @RequestBody(description = """
                 The DTO used to update its entity.
                 Only the attributes that are changed need to be passed into the dto.
                 The id in the DTO is immutable.
                 """, required = true),
             responses = {@ApiResponse(responseCode = "200",
                                       description = "The entity with the given primary key id.")})
  ResponseEntity<List<D>> updateAll(@RequestBody List<D> dtos);

  @Operation(operationId = "deleteById", summary = "Delete an entity by its id.",
             description = "Use this API to delete an entity via its id.",
             parameters = @Parameter(name = "id", description = "The id used to delete the entity by.",
                                     required = true), responses = {@ApiResponse(responseCode = "204", description =
      "The entity was deleted by its id successfully. A redirect is" + " not required.")})
  ResponseEntity<Void> deleteById(@PathVariable(required = true) K id);

  @Operation(operationId = "deleteAllById", summary = "Delete all entities by their ids.",
             description = "Use this API to delete any entity in this domain that matches the passed ids.",
             requestBody = @RequestBody(description = "The ids used to delete the entities by.", required = true),
             responses = {@ApiResponse(responseCode = "204",
                                       description = "The entity was deleted by its id successfully. A redirect is" +
                                                     " not required.")})
  ResponseEntity<Void> deleteAllById(@RequestBody List<K> ids);

}