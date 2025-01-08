package dev.springharvest.crud.domains.base.graphql;

import dev.springharvest.shared.constants.Aggregates;
import dev.springharvest.shared.constants.DataPaging;
import dev.springharvest.shared.constants.PageData;
import dev.springharvest.shared.domains.base.models.dtos.BaseDTO;
import dev.springharvest.shared.domains.base.models.entities.BaseEntity;
import graphql.schema.DataFetchingEnvironment;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * This interface defines the contract for a base GraphQL controller.
 * It provides methods for performing search queries on entities.
 * The @Operation annotation is used to define the OpenAPI specification for the controller.
 *
 * @param <K> The type of the id (primary key) field pertaining to the entity relating to the DTO
 *
 * @see PageData
 * @see DataFetchingEnvironment
 * @see BaseDTO
 * @see DataPaging
 * @since 1.0
 *
 * @author Gilles Djawa (NeroNemesis)
 */
public interface IGraphQLCrudController<E extends BaseEntity<K>, K extends Serializable> {

    /**
     * Performs a search query on the entity based on the provided filter and paging information.
     * The fields to fetch are extracted from the DataFetchingEnvironment.
     *
     * @throws NoSuchFieldException if the fields do not exist
     * @param filter the filter criteria as a map of field names to values
     * @param paging the paging information
     * @param environment the GraphQL data fetching environment
     * @param clause the type of clause to be performed on the query
     * @return a list of DTOs matching the filter criteria
     */
    @Operation(operationId = "search", summary = "Performs any search query on the entity.",
            description = "Use this API to retrieve entities corresponding to the passed query inside the filter and ordered according to the requested paging.",
            parameters = {
                    @Parameter(description = "The map containing the query",
                            name = "filter",
                            required = true),
                    @Parameter(description = "The map containing the type of clause to be performed on the query",
                            name = "clause",
                            required = true),
                    @Parameter(description = "The paging request, determining the number of entities that should be returned, their sort orders and their sort direction as well",
                            name = "paging",
                            required = true),
                    @Parameter(description = "The GraphQL data fetching environment that allows us to retrieve what fields where selected to be displayed by the user",
                            name = "environment",
                            required = true),
            },
            responses = {@ApiResponse(responseCode = "200", description = "The queried entities ordered according to the paging or their count.")})
    PageData<E> search(@RequestParam(name = "filter") Map<String, Object> filter,
                       @RequestParam(name = "clause") Map<String, Object> clause,
                       @RequestParam(name = "paging") DataPaging paging,
                       @RequestParam(name = "environment") DataFetchingEnvironment environment) throws NoSuchFieldException;

    /**
     * Performs a complex search query on the entity based on the provided filter, paging information, fields to select and aggregates operations.
     *
     * @param filter @description the filter criteria as a map of field names to values
     * @param clause @description the type of clause to be performed on the query
     * @param fields @description the fields to select. The expected format is data_typeName or data_data2_fieldName...
     * @param aggregatesFilter @description the filter criteria for the aggregates. The expected format for the aggregates lists values is fieldName or data_data2_fieldName...
     * @param paging @description the paging information
     * @return an object (JSON format) containing the search results
     * @throws NoSuchFieldException if the fields do not exist
     */
    @Operation(operationId = "complexSearch", summary = "Performs a complex search query on the entity.",
            description = "Use this API to retrieve entities corresponding to the passed query inside the filter and ordered according to the requested paging.",
            parameters = {
                    @Parameter(description = "The map containing the query",
                            name = "filter",
                            required = true),
                    @Parameter(description = "The map containing the type of clause to be performed on the query",
                            name = "clause",
                            required = true),
                    @Parameter(description = "The fields to select",
                            name = "fields",
                            required = true),
                    @Parameter(description = "The object containing the aggregation operations",
                            name = "aggregatesFilter",
                            required = true),
                    @Parameter(description = "The paging request, determining the number of entities that should be returned, their sort orders and their sort direction as well",
                            name = "paging",
                            required = true)
            },
            responses = {@ApiResponse(responseCode = "200", description = "The queried entities ordered according to the paging or their count.")})
    Object search(Map<String, Object> filter, Map<String, Object> clause, List<String> fields, Aggregates aggregatesFilter, DataPaging paging) throws NoSuchFieldException;

    /**
     * Performs a count query on the entity based on the provided filter and paging information.
     *
     * @param filter @description the filter criteria as a map of field names to values
     * @param clause @description the type of clause to be performed on the query
     * @param fields @description the fields to apply the distinct clause on. The expected format is data_typeName or data_data2_fieldName...
     * @return the count of entities corresponding to the filter criteria
     * @throws NoSuchFieldException if the fields do not exist
     */
    @Operation(operationId = "count", summary = "Performs count operations on an entity.",
            description = "Use this API to retrieve entities count corresponding to the passed query inside the filter.",
            parameters = {
                    @Parameter(description = "The map containing the query",
                            name = "filter",
                            required = true),
                    @Parameter(description = "The map containing the type of clause to be performed on the query",
                            name = "clause",
                            required = true),
                    @Parameter(description = "The fields to apply the distinct clause on",
                            name = "fields",
                            required = true)
            },
            responses = {@ApiResponse(responseCode = "200", description = "The queried entities count.")})
    long count(@RequestParam(name = "filter") Map<String, Object> filter, @RequestParam(name = "clause") Map<String, Object> clause, @RequestParam(name = "fields") List<String> fields) throws NoSuchFieldException;
}