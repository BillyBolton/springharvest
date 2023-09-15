package dev.springharvest.search.rest;

import dev.springharvest.search.model.queries.requests.filters.BaseFilterRequestDTO;
import dev.springharvest.search.model.queries.requests.search.SearchRequestDTO;
import dev.springhavest.common.models.dtos.BaseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * This interface is used to define the contract for a base controller. The @Operation annotation is used to define the
 * OpenAPI specification for the controller.
 *
 * @param <RD> FilterRequestDTO held by the SearchRequest
 * @param <D>  The DTO object for a domain
 * @param <K>  The type of the id (primary key) field pertaining to the entity relating to the DTO
 */
public interface ISearchController<RD extends BaseFilterRequestDTO, D extends BaseDTO<K>, K> {

    @Operation(operationId = "search", summary = "Retrieves entities by a filtered search.",
               description = "Use this API to retrieve entities by a filtered search.",
               requestBody = @RequestBody(description = """
                                                        A list of maps with their keys as the parameter to search by, and the value being the value to match on.
                                                        Each parameter pair will be AND-ed, where each individual map will be OR-ed.
                                                        In this version, the parameter must be the path to access the attribute in the entity.
                                                        """, required = false),
               responses = {@ApiResponse(responseCode = "200",
                                         description = "The entities found from the provided search filters.")})
    ResponseEntity<List<D>> search(@RequestBody SearchRequestDTO<RD> searchQuery);

}
