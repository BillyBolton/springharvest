package dev.springharvest.crud.domains.base.graphql;

import dev.springharvest.expressions.helpers.Operation;
import dev.springharvest.shared.constants.Aggregates;
import dev.springharvest.shared.constants.DataPaging;
import dev.springharvest.shared.constants.PageData;
import dev.springharvest.shared.domains.base.models.dtos.BaseDTO;
import dev.springharvest.shared.domains.base.models.entities.BaseEntity;
import graphql.schema.DataFetchingEnvironment;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import dev.springharvest.expressions.builders.TypedQueryBuilder;
import jakarta.persistence.criteria.JoinType;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * A generic implementation of the IGraphQLCrudController interface.
 * This class provides CRUD operations for GraphQL endpoints using generics to handle different types of DTOs, entities, and primary key fields.
 *
 * @param <E> The entity type, which extends BaseEntity<K>
 * @param <K> The type of the primary key field, which extends Serializable
 *
 * @see IGraphQLCrudController
 * @see BaseDTO
 * @see BaseEntity
 * @see TypedQueryBuilder
 * @author Gilles Djawa (NeroNemesis)
 * @since 1.0
 */
public class AbstractGraphQLCrudController<E extends BaseEntity<K>, K extends Serializable>
        implements IGraphQLCrudController<E, K> {

    /**
     * The class type of the entity.
     */
    protected Class<E> entityClass;

    /**
     * The class type of the primary key field.
     */
    protected Class<K> keyClass;

    /**
     * The list of joins to be performed.
     */
    protected Map<String, JoinType> joins;

    /**
     * The TypedQueryBuilder to parse filter expressions.
     */
    @Setter
    @Autowired
    private TypedQueryBuilder typedQueryBuilder;

    /**
     * Constructs an AbstractGraphQLCrudController with the specified mapper, service, and entity class.
     *
     * @param entityClass the class type of the entity
     */
    protected AbstractGraphQLCrudController(Class<E> entityClass, Class<K> keyClass) {
        this.entityClass = entityClass;
        this.keyClass = keyClass;
        this.joins = new HashMap<>();
    }

    @SuppressWarnings("unchecked")
    @Override
    public PageData<E> search(Map<String, Object> filter, Map<String, Object> clause, DataPaging paging, DataFetchingEnvironment environment) {
        try {
            List<String> fields = extractFieldsFromEnvironment(environment);
            processJoins();

            return (PageData<E>) typedQueryBuilder.parseFilterExpression(Operation.SEARCH, entityClass, keyClass, filter, clause, getFormattedFields(fields), joins, null, paging);
        }
        finally {
            if (joins != null && !joins.isEmpty()) {
                joins.clear();
            }
        }
    }

    @Override
    public Object search(Map<String, Object> filter, Map<String, Object> clause, List<String> fields, Aggregates aggregatesFilter, DataPaging paging) {
        List<String> formattedFields = fields != null ? getFormattedFields(fields) : null;
        Aggregates aggregates = getFormattedAggregates(aggregatesFilter, formattedFields);
        return typedQueryBuilder.parseFilterExpression(Operation.SEARCH, entityClass, keyClass, filter, clause, formattedFields, null, aggregates, paging);
    }

    @Override
    public long count(Map<String, Object> filter, Map<String, Object> clause, List<String> fields) {
        return (long) typedQueryBuilder.parseFilterExpression(Operation.COUNT, entityClass, keyClass, filter, clause, getFormattedFields(fields), null, null, null);
    }

    /**
     * Formats the provided aggregates and fields.
     * <p>
     * This method formats the aggregates by converting their fields to a standardized format.
     * If the fields are not specified, it ensures that the fields not in the aggregates are included in the groupBy list.
     * Expects the fields in the aggregates list to be in the format "field1.field2.field3" or "field1_field2_field3" and the fields list to be in the format "field1.field_x", "field2.field_z", "field3.field_y".
     *
     * @param aggregates The aggregates to be formatted.
     * @param fields The list of fields to be included in the groupBy list if not already present.
     * @return A new Aggregates object with formatted fields, or null if the input aggregates are null.
     */
    static Aggregates getFormattedAggregates(Aggregates aggregates, List<String> fields) {
        if (aggregates == null) {
            return null;
        }

        // Helper method to get formatted fields or null
        Function<List<String>, List<String>> formatFields = list ->
                (list != null && !list.isEmpty()) ? getFormattedFields(list) : null;

        List<String> count = formatFields.apply(aggregates.count());
        List<String> sum = formatFields.apply(aggregates.sum());
        List<String> avg = formatFields.apply(aggregates.avg());
        List<String> min = formatFields.apply(aggregates.min());
        List<String> max = formatFields.apply(aggregates.max());
        List<String> groupBy = formatFields.apply(aggregates.groupBy());

        // If fields are specified, ensure groupBy includes missing fields
        if (fields != null && !fields.isEmpty()) {
            List<String> allFields = Stream.of(count, sum, avg, min, max)
                    .filter(Objects::nonNull)
                    .flatMap(List::stream)
                    .toList();

            if (groupBy == null) {
                groupBy = new ArrayList<>();
            }

            List<String> finalGroupBy = groupBy;
            fields.stream()
                    .filter(field -> !allFields.isEmpty() && !finalGroupBy.contains(field))
                    .forEach(groupBy::add);

            if (groupBy.isEmpty()) {
                groupBy = null;
            }
        }

        return new Aggregates(count, sum, avg, min, max, groupBy);
    }

    /**
     * Formats the provided list of fields.
     * <p>
     * This method processes each field in the provided list and converts it to a standardized format.
     * It handles fields containing ".data/", "/", or "_" by splitting and reformatting them.
     * "/" come from the DataFetchingEnvironment, "_" come from custom fields in the GraphQL query.
     * Fields related to pagination (e.g., "currentPage", "pageSize") are handled separately.
     *
     * @param fields The list of fields to be formatted.
     * @return A list of formatted fields.
     */
    static List<String> getFormattedFields(List<String> fields) {
        List<String> formattedFields = new ArrayList<>();
        if (fields == null || fields.isEmpty()) {
            return formattedFields;
        }

        for (String field : fields) {
            if (field.contains(".data/")) {
                field = field.substring(field.indexOf(".") + 1); // Remove the prefix before ".data/"
            }

            if (field.contains("/")) {
                formattedFields.add(formatNestedField(field, "/"));
            } else if (field.contains("_")) {
                formattedFields.add(formatNestedField(field, "_"));
            } else {
                // Handle special cases
                if (!isExcludedField(field)) {
                    formattedFields.add(field);
                } else {
                    addSpecialField(formattedFields, field);
                }
            }
        }

        return formattedFields;
    }

    private static String formatNestedField(String field, String delimiter) {
        String[] parts = field.split(delimiter);
        StringBuilder formatted = new StringBuilder(parts[0]);

        for (int i = 1; i < parts.length; i++) {
            String[] subParts = parts[i].split("\\.");
            formatted.append(".").append(subParts[subParts.length - 1]);
        }

        return formatted.toString();
    }

    private static boolean isExcludedField(String field) {
        String[] excludedKeywords = {
                "currentPage", "pageSize", "totalPages", "currentPageCount", "total"
        };

        for (String keyword : excludedKeywords) {
            if (field.contains(keyword)) {
                return true;
            }
        }

        return field.split("\\.").length == 2 && field.endsWith(".data");
    }

    private static void addSpecialField(List<String> formattedFields, String field) {
        if (field.contains("currentPage") && !formattedFields.contains("currentPage")) {
            String[] parts = field.split("\\.");
            if (parts.length == 2 && "currentPage".equals(parts[1]) && !formattedFields.contains("currentPage")) {
                formattedFields.add("currentPage");
            }
        }
        if (field.contains("pageSize") && !formattedFields.contains("pageSize")) {
            formattedFields.add("pageSize");
        }
        if (field.contains("totalPages") && !formattedFields.contains("totalPages")) {
            formattedFields.add("totalPages");
        }
        if (field.contains("currentPageCount") && !formattedFields.contains("currentPageCount")) {
            formattedFields.add("currentPageCount");
        }
        if (field.contains("total")) {
            String[] parts = field.split("\\.");
            if (parts.length == 2 && "total".equals(parts[1]) && !formattedFields.contains("total")) {
                formattedFields.add("total");
            }
        }
    }


    /**
     * Extracts the list of fields from the DataFetchingEnvironment.
     * <p>
     * This method retrieves the fields from the provided DataFetchingEnvironment and returns them as a list of strings.
     * It also processes any join arguments and adds them to the joins map.
     *
     * @param environment The DataFetchingEnvironment containing the selection set.
     * @return A list of field names extracted from the environment, or null if the environment or its selection set is null.
     */
    List<String> extractFieldsFromEnvironment(DataFetchingEnvironment environment) {
        if (environment == null || environment.getSelectionSet() == null || environment.getSelectionSet().getFields() == null) {
            return new ArrayList<>();
        }
        if ( environment.getSelectionSet().getFields().isEmpty() ) {
            return new ArrayList<>();
        }
        List<String> fields = new ArrayList<>();
        environment.getSelectionSet().getFields().forEach(x -> {
            fields.add(x.getFullyQualifiedName());
            if (x.getArguments() != null && !x.getArguments().isEmpty()) {
                x.getArguments().forEach((y, z) -> {
                    if (y.contains("join"))
                        joins.put(x.getFullyQualifiedName(), JoinType.valueOf(z.toString()));
                });
            }
        });
        return fields;
    }

    /**
     * Processes the joins by formatting the join keys.
     * <p>
     * This method updates the `joins` map by formatting the keys using the `getFormattedFields` method.
     * It creates a new map with the formatted keys and the corresponding join types, then replaces the original `joins` map with the updated one.
     */
    void processJoins() {
        if (joins == null || joins.isEmpty()) {
            return;
        }

        Map<String, JoinType> updatedJoins = new LinkedHashMap<>();
        List<String> formattedFields = getFormattedFields(new ArrayList<>(joins.keySet()));
        Iterator<String> keyIterator = joins.keySet().iterator();
        Iterator<String> formattedFieldIterator = formattedFields.iterator();

        while (keyIterator.hasNext() && formattedFieldIterator.hasNext()) {
            String oldKey = keyIterator.next();
            String newKey = formattedFieldIterator.next();
            updatedJoins.put(newKey, joins.get(oldKey));
        }

        joins.clear();
        joins.putAll(updatedJoins);
    }
}
