package dev.springharvest.expressions.builders;

import dev.springharvest.expressions.helpers.Operation;
import dev.springharvest.expressions.helpers.Operator;
import dev.springharvest.expressions.helpers.PathObject;
import dev.springharvest.expressions.mappers.GenericEntityMapper;
import dev.springharvest.shared.constants.*;
import jakarta.persistence.*;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.*;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Function;

/**
 * This class is responsible for helping build Typed queries from a filter map.
 * It provides methods to parse filter expressions and create typed queries for querying entities.
 *
 * @author NeroNemesis
 *
 * @since 1.0
 * @see Operation
 * @see Operator
 * @see Aggregates
 * @see DataPaging
 * @see Sort
 * @see SortDirection
 * @see EntityManagerFactory
 * @see EntityManager
 */
@Component
public class TypedQueryBuilder {

    /**
     * The entity manager factory.
     * This is used to create an entity manager.
     * The entity manager is used to create a criteria builder.
     * The criteria builder is used to create a criteria query.
     * The criteria query is used to create a root.
     * The root is used to create a path.
     * The path is used to create a predicate.
     * The predicate is used to create a typed query.
     * The typed query is used to execute the query.
     * The query is used to get the result list.
     */
    @Autowired
    @Setter
    private EntityManagerFactory entityManagerFactory;

    /**
     * A map containing joins to be applied to the query.
     */
    private static Map<String, Join<?, ?>> joinsMap;

    /**
     * A list of paging elements to include in the query result.
     */
    public static List<String> pagingElementsToInclude = new ArrayList<>();

    /**
     * Parses a filter expression and creates a typed query for querying entities.
     * Examples of parameter values can be fount in the tests classes (TypedQueryBuilderTest, AbstractGraphQlControllerTest, BookGraphQlControllerTest, etc)
     *
     *
     * @param operation The operation to be performed (e.g., SEARCH, COUNT).
     * @param rootClass The class of the root entity.
     * @param keyClass The class of the key entity.
     * @param filterMap A map containing filter criteria.
     * @param clauseMap A map containing additional clauses.
     * @param fields A list of fields to be included in the query.
     * @param aggregatesFilter Aggregates to be applied to the query.
     * @param paging Paging information for the query.
     * @param <T> The type of the root entity.
     * @return The result of the query, which can be a list of entities or a count.
     * @throws RuntimeException If a field specified in the fields list is not found in the root class.
     */
    public <T, K> Object parseFilterExpression(Operation operation,
                                            Class<T> rootClass,
                                            Class<K> keyClass,
                                            Map<String, Object> filterMap,
                                            Map<String, Object> clauseMap,
                                            List<String> fields,
                                            Map<String, JoinType> joins,
                                            Aggregates aggregatesFilter,
                                            DataPaging paging) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            try {
                fields = CleanFields(rootClass, keyClass, fields);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException("Field not found.\n For each field make sure the field's name exists in the class corresponding to your schema definition and follows this format 'Schema type name' + '.' + 'fieldName.\n e.g: 'Book.title', 'Book.author.pet.name'.");
            }
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createQuery(Tuple.class);
            Root<T> root = criteriaQuery.from(rootClass);
            applyJoins(root, joins);

            if (filterMap == null || filterMap.isEmpty()) {
                return applyOperations(criteriaBuilder, criteriaQuery, entityManager, clauseMap, root, fields, joins, operation, null, rootClass, keyClass, null, aggregatesFilter, paging);
            }

            String rootOperator = determineRootOperator(filterMap);
            Predicate predicate = createTypedQuery(filterMap, criteriaBuilder, rootClass, keyClass, root, "", rootOperator, fields, criteriaQuery);
            criteriaQuery.where(predicate);

            return applyOperations(criteriaBuilder, criteriaQuery, entityManager, clauseMap, root, fields, joins, operation, filterMap, rootClass, keyClass, rootOperator, aggregatesFilter, paging);

        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    /**
     * Applies joins to the root entity based on the provided map of join paths and join types.
     * <p>
     * Expects a map of join paths and join types.
     * The join path is a string representing the path to the field to be joined. The join type is the type of join to be applied.
     * e.g., "data.Author.pet" -> JoinType.INNER"
     * @param root The root entity in the query.
     * @param joins A map containing join paths as keys and join types as values.
     */
    private void applyJoins(Root<?> root, Map<String, JoinType> joins) {
        joinsMap = new HashMap<>();

        if (joins == null) {
            return;
        }
        // Apply joins based on the provided map
        for (Map.Entry<String, JoinType> entry : joins.entrySet()) {
            String path = entry.getKey();
            JoinType joinType = entry.getValue();

            String[] pathParts = path.split("\\.");
            From<?, ?> currentFrom = root;
            StringBuilder currentPath = new StringBuilder(pathParts[1]); // Start from root (e.g., "Book")

            for (int i = 1; i < pathParts.length; i++) {
                // Build the current path incrementally
                if (i > 1) {
                    currentPath.append(".").append(pathParts[i]);
                }
                String currentPathStr = currentPath.toString();
                // Check if a join for this path already exists
                if (joinsMap.containsKey(currentPathStr)) {
                    currentFrom = joinsMap.get(currentPathStr);
                } else {
                    // Create a new join and add it to the map
                    Join<?, ?> newJoin = ((From<?, ?>) currentFrom).join(pathParts[i], joinType);
                    joinsMap.put(currentPathStr, newJoin);
                    currentFrom = newJoin;
                }
            }
        }
    }

    /**
     * Applies operations to the criteria query based on the specified operation.
     *
     * @param criteriaBuilder The criteria builder used to construct the query.
     * @param criteriaQuery The criteria query to which operations will be applied.
     * @param entityManager The entity manager used to execute the query.
     * @param operationMap A map containing additional operation parameters.
     * @param root The root entity in the query.
     * @param fields A list of fields to be included in the query.
     * @param operation The operation to be performed (e.g., SEARCH, COUNT).
     * @param filterMap A map containing filter criteria.
     * @param rootClass The class of the root entity.
     * @param rootOperator The root operator for combining predicates.
     * @param aggregates Aggregates to be applied to the query.
     * @param paging Paging information for the query.
     * @param <T> The type of the root entity.
     * @return The result of the query, which can be a list of entities or a count.
     * @throws IllegalArgumentException If an unsupported operation is specified.
     */
    private <T, K> Object applyOperations(CriteriaBuilder criteriaBuilder, CriteriaQuery<Tuple> criteriaQuery, EntityManager entityManager, Map<String, Object> operationMap, Root<T> root, List<String> fields, Map<String, JoinType> joins, Operation operation, Map<String, Object> filterMap, Class<T> rootClass, Class<K> keyClass, String rootOperator, Aggregates aggregates, DataPaging paging)
    {
        boolean distinct = operationMap != null && Boolean.TRUE.equals(operationMap.get("distinct"));
        long total = -1L;

        switch (operation) {
            case SEARCH:
                applyFieldProjection(criteriaBuilder, criteriaQuery, root, fields, aggregates, paging);
                if (distinct) {
                    criteriaQuery.distinct(true);
                }
                TypedQuery<Tuple> query = entityManager.createQuery(criteriaQuery);
                int totalPages = -1;
                if (pagingElementsToInclude.contains("total") || pagingElementsToInclude.contains("totalPages") || (aggregates != null && pagingElementsToInclude.isEmpty()))
                    //Seems to add +1 to the real total count
                    //Might need fixing
                    total = handleCountOperation(criteriaBuilder, criteriaQuery, entityManager, filterMap, rootClass, keyClass, fields, joins, rootOperator, distinct, root, true, aggregates);
                if (paging != null) {
                    query.setFirstResult((paging.page() - 1) * paging.size());
                    query.setMaxResults(paging.size());
                    if (pagingElementsToInclude.contains("totalPages") || (aggregates != null && pagingElementsToInclude.isEmpty()))
                        totalPages = (int) Math.ceil((double) total / paging.size());
                }
                //Need the mapper here
                List<Tuple> resultsList = query.getResultList();
                int currentPageCount = -1;
                if (pagingElementsToInclude.contains("currentPageCount") || (aggregates != null && pagingElementsToInclude.isEmpty()))
                    currentPageCount = resultsList.size();
                GenericEntityMapper<T> mapper = new GenericEntityMapper<>();
                if (aggregates == null) // If no aggregates are specified, return a list of entities
                {
                    return new PageData<>(
                            mapper.mapTuplesToEntity(resultsList, rootClass),
                            paging != null && pagingElementsToInclude.contains("currentPage") ? paging.page() : -1,
                            paging != null && pagingElementsToInclude.contains("pageSize") ? paging.size() : -1,
                            total,
                            totalPages,
                            currentPageCount);
                }
                List<Map<String, Object>> dataMap = mapper.mapTuplesToMap(resultsList);
                Map<String, Object> pagingMap = new HashMap<>();
                Map<String, Object> subPagingMap = new HashMap<>();
                assert paging != null;
                subPagingMap.put("page", paging.page());
                subPagingMap.put("size", paging.size());
                subPagingMap.put("currentPageCount", currentPageCount);
                subPagingMap.put("totalCount", total);
                subPagingMap.put("totalPages", totalPages);

                pagingMap.put("paging", subPagingMap);
                dataMap.add(pagingMap);
                return dataMap;

            case COUNT:
                return handleCountOperation(criteriaBuilder, criteriaQuery, entityManager, filterMap, rootClass, keyClass, fields, joins, rootOperator, distinct, root, false, null);

            default:
                throw new IllegalArgumentException("Unsupported operation");
        }
    }

    /**
     * Handles the count operation for a criteria query.
     *
     * @param <T> The type of the root entity.
     * @param criteriaBuilder The criteria builder used to construct the query.
     * @param criteriaQuery The criteria query to which operations will be applied.
     * @param entityManager The entity manager used to execute the query.
     * @param filterMap A map containing filter criteria.
     * @param rootClass The class of the root entity.
     * @param fields A list of fields to be included in the query.
     * @param rootOperator The root operator for combining predicates.
     * @param distinct Whether the count should be distinct.
     * @param root The root entity in the query.
     * @param isSubQuery Whether the query is a subquery.
     * @param aggregates Aggregates to be applied to the query.
     * @return The result of the count operation.
     */
    private <T, K> long handleCountOperation(CriteriaBuilder criteriaBuilder, CriteriaQuery<Tuple> criteriaQuery, EntityManager entityManager, Map<String, Object> filterMap, Class<T> rootClass, Class<K> keyClass, List<String> fields, Map<String, JoinType> joins, String rootOperator, boolean distinct, Root<T> root, boolean isSubQuery, Aggregates aggregates) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<?> countRoot = countQuery.from(root.getJavaType());

        if (isSubQuery)
        {
            Subquery<Integer> subquery = countQuery.subquery(Integer.class);
            Root<?> subRoot = subquery.from(countRoot.getJavaType());
            applyJoins(subRoot, joins);

            Predicate filterPredicate = filterMap != null ? createTypedQuery(filterMap, criteriaBuilder, rootClass, keyClass, (Root) subRoot, "", rootOperator, fields, criteriaQuery) : null;
            String primaryKeyField = findPrimaryKeyField(rootClass);
            if (primaryKeyField == null)
                throw new RuntimeException("Primary key not found. Please make sure to add @Id annotation to the primary key field in your entity class.");
            if (filterPredicate != null) {
                subquery.where(
                        criteriaBuilder.and(
                                filterPredicate,
                                criteriaBuilder.equal(subRoot.get(primaryKeyField), countRoot.get(primaryKeyField))
                        )
                );
            }
            else {
                subquery.where(criteriaBuilder.equal(subRoot.get(primaryKeyField), countRoot.get(primaryKeyField)));
            }

            joinsMap.clear();
            if (aggregates != null) {
                List<Selection<?>> selections = new ArrayList<>();
                List<Path<?>> groupByPaths = new ArrayList<>();

                for (String field : fields) {
                    PathObject pathAndAlias = getPathFromField(subRoot, field);
                    Path<?> path = pathAndAlias.path();
                    selections.add(path);
                }
                addAggregateSelections(criteriaBuilder, subRoot, aggregates, selections);
                addGroupByPaths(subRoot, aggregates, groupByPaths);
                if (!groupByPaths.isEmpty()) {
                    subquery.groupBy(groupByPaths.toArray(new Path[0]));
                }
            }

            subquery.select(criteriaBuilder.literal(1));
            countQuery.select(criteriaBuilder.count(subRoot)).where(criteriaBuilder.exists(subquery));
            countQuery.select(criteriaBuilder.count(countRoot));
        }
        else {
            /* PLEASE READ
             * The following code is for now the only stable way to implement Count and CountDistinct operations with CriteriaBuilder.
             * The Count and CountDistinct methods only support selecting 1 field.
             * Counting multiple fields is supported through Concatenation in Postgres and MySQL but not in T-SQL. Not sure about H2 and Oracle.
             * So I think it is preferable to avoid using Concatenation for now.
             */

            Predicate filterPredicate = filterMap != null ? createTypedQuery(filterMap, criteriaBuilder, rootClass, keyClass, (Root) countRoot, "", rootOperator, fields, criteriaQuery) : null;
            if (filterPredicate != null) {
                countQuery.where(filterPredicate);
            }

            if (distinct) {
                if (fields == null || fields.isEmpty()) {
                    countQuery.select(criteriaBuilder.countDistinct(countRoot));
                } else {
                    countQuery.select(criteriaBuilder.countDistinct(getPathFromField(countRoot, fields.getFirst()).path()));
                }
            } else {
                if (fields == null || fields.isEmpty())
                    countQuery.select(criteriaBuilder.count(countRoot));
                else
                    countQuery.select(criteriaBuilder.count(getPathFromField(countRoot, fields.getFirst()).path()));
            }
        }

        return entityManager.createQuery(countQuery).getSingleResult();
    }

    /**
     * Creates a typed query based on the provided filter map.
     *
     * @param <T> The type of the root entity.
     * @param filterMap A map containing filter criteria.
     * @param builder The criteria builder used to construct the query.
     * @param rootClass The class of the root entity.
     * @param root The root entity in the query.
     * @param parentPath The parent path for nested fields.
     * @param rootOperator The root operator for combining predicates.
     * @param fields A list of fields to be included in the query.
     * @param query The criteria query to which predicates will be applied.
     * @return A combined predicate representing the filter criteria.
     */
    @SuppressWarnings("unchecked") // Made sure these casts are safe
    private static <T, K> Predicate createTypedQuery(Map<String, Object> filterMap, CriteriaBuilder builder, Class<T> rootClass, Class<K> keyClass, Root<T> root, String parentPath, String rootOperator, List<String> fields, CriteriaQuery<?> query) {
        List<Predicate> predicates = new ArrayList<>();

        if (filterMap == null || filterMap.isEmpty()) {
            return builder.conjunction();
        }

        for (Map.Entry<String, Object> entry : filterMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (isLogicalOperator(key)) {
                if (Operator.getOperator(key).getKind() != Operator.Kind.UNARY) {
                    predicates.add(createLogicalOperatorTypedQuery(key, (List<Map<String, Object>>) value, builder, rootClass, keyClass, root, parentPath, fields, query));
                } else {
                    predicates.add(createUnaryOperatorTypedQuery((Map<String, Object>) value, builder, rootClass, keyClass, root, parentPath, fields, query));
                }
            } else {
                try {
                    if (isComplexField(rootClass, keyClass, key)) {
                        String newPath = parentPath.isEmpty() ? key : parentPath + "." + key;
                        Class<?> nestedClass = getFieldType(rootClass, key);
                        String subOperator = determineRootOperator((Map<String, Object>) value);
                        predicates.add(createTypedQuery((Map<String, Object>) value, builder, (Class) nestedClass, keyClass, root, newPath, subOperator, fields, query));
                    } else {
                        predicates.add(createSimpleFieldTypedQuery(key, value, parentPath, rootClass, builder, root));
                    }
                } catch (NoSuchFieldException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return combinePredicates(predicates, builder, rootOperator, query, root);
    }

    /**
     * Creates a typed query for logical operators (AND, OR, etc.) based on the provided filter map.
     *
     * @param <T> The type of the root entity.
     * @param operator The logical operator to be applied (e.g., AND, OR).
     * @param value A list of filter maps representing the sub-filters.
     * @param builder The criteria builder used to construct the query.
     * @param rootClass The class of the root entity.
     * @param root The root entity in the query.
     * @param parentPath The parent path for nested fields.
     * @param fields A list of fields to be included in the query.
     * @param query The criteria query to which predicates will be applied.
     * @return A combined predicate representing the logical operation.
     */
    private static <T,K> Predicate createLogicalOperatorTypedQuery(String operator, List<Map<String, Object>> value, CriteriaBuilder builder, Class<T> rootClass, Class<K> keyClass, Root<T> root, String parentPath, List<String> fields, CriteriaQuery<?> query) {
        List<Predicate> subPredicates = new ArrayList<>();

        for (Map<String, Object> subFilter : value) {
            String subOperator = determineRootOperator(subFilter);
            subPredicates.add(createTypedQuery(subFilter, builder, rootClass, keyClass, root, parentPath, subOperator, fields, query));
        }

        return combinePredicates(subPredicates, builder, operator, query, root);
    }

    /**
     * Creates a typed query for unary operators (NOT) based on the provided filter map.
     *
     * @param <T> The type of the root entity.
     * @param value A map containing filter criteria.
     * @param builder The criteria builder used to construct the query.
     * @param rootClass The class of the root entity.
     * @param root The root entity in the query.
     * @param parentPath The parent path for nested fields.
     * @param fields A list of fields to be included in the query.
     * @param query The criteria query to which predicates will be applied.
     * @return A predicate representing the NOT operation.
     */
    private static <T, K> Predicate createUnaryOperatorTypedQuery(Map<String, Object> value, CriteriaBuilder builder, Class<T> rootClass, Class<K> keyClass, Root<T> root, String parentPath, List<String> fields, CriteriaQuery<?> query) {
        Predicate predicate = createTypedQuery(value, builder, rootClass, keyClass, root, parentPath, determineRootOperator(value), fields, query);
        return builder.not(predicate);
    }

    /**
     * Creates a typed query for a simple field based on the provided key and value.
     *
     * @param <T> The type of the root entity.
     * @param key The key representing the field name.
     * @param value The value to be matched against the field.
     * @param parentPath The parent path for nested fields.
     * @param rootClass The class of the root entity.
     * @param builder The criteria builder used to construct the query.
     * @param root The root entity in the query.
     * @return A predicate representing the filter criteria for the simple field.
     */
    private static <T> Predicate createSimpleFieldTypedQuery(String key, Object value, String parentPath, Class<T> rootClass, CriteriaBuilder builder, Root<T> root) {
        Path<?> path = getPath(root, parentPath, key, rootClass);
        return createPredicate(builder, path, value, getFieldType(rootClass, key));
    }

    /**
     * Applies field projection to the criteria query based on the specified fields and aggregates.
     *
     * @param builder The criteria builder used to construct the query.
     * @param query The criteria query to which projections will be applied.
     * @param root The root entity in the query.
     * @param fields A list of fields to be included in the projection.
     * @param aggregates Aggregates to be applied to the query.
     * @param paging Paging information for the query.
     */
    private static void applyFieldProjection(CriteriaBuilder builder, CriteriaQuery<?> query, Root<?> root, List<String> fields, Aggregates aggregates, DataPaging paging) {
        List<Selection<?>> selections = new ArrayList<>();
        List<Path<?>> groupByPaths = new ArrayList<>();

        for (String field : fields) {
            PathObject pathAndAlias = getPathFromField(root, field);
            Path<?> path = pathAndAlias.path();
            String alias = pathAndAlias.alias();
            selections.add(path.alias(alias));
        }

        if (aggregates != null) {
            addAggregateSelections(builder, root, aggregates, selections);
            addGroupByPaths(root, aggregates, groupByPaths);
            if (!groupByPaths.isEmpty()) {
                query.groupBy(groupByPaths.toArray(new Path[0]));
            }
        }

        query.multiselect(selections);
        if (!groupByPaths.isEmpty()) {
            query.groupBy(groupByPaths.toArray(new Path[0]));
        }

        applySorting(builder, query, root, paging);
        joinsMap.clear(); // Clear the joins map to ensure that joins are not reused across queries or applied multiple times
    }

    /**
     * Applies sorting to the criteria query based on the specified paging information.
     *
     * @param builder The criteria builder used to construct the query.
     * @param query The criteria query to which sorting will be applied.
     * @param root The root entity in the query.
     * @param paging Paging information for the query, including sort orders.
     */
    private  static void applySorting(CriteriaBuilder builder, CriteriaQuery<?> query, Root<?> root, DataPaging paging) {
        if (paging == null)
            return;
        if (paging.sortOrders() != null && !paging.sortOrders().isEmpty()) {
            List<Order> orders = new ArrayList<>();
            for (Sort sortOrder : paging.sortOrders()) {
                PathObject pathAndAlias = getPathFromField(root, sortOrder.field());
                Path<?> path = pathAndAlias.path();
                orders.add(sortOrder.sortDirection() == SortDirection.ASC ? builder.asc(path) : builder.desc(path));
            }
            query.orderBy(orders);
        }
    }

    /**
     * Adds aggregate selections to the criteria query based on the specified aggregates.
     *
     * @param builder The criteria builder used to construct the query.
     * @param root The root entity in the query.
     * @param aggregates Aggregates to be applied to the query.
     * @param selections A list of selections to which aggregate selections will be added.
     */
    private static void addAggregateSelections(CriteriaBuilder builder, Root<?> root, Aggregates aggregates, List<Selection<?>> selections) {
        addAggregateSelection(builder, root, aggregates.avg(), selections, "avg_", builder::avg);
        addAggregateSelection(builder, root, aggregates.max(), selections, "max_", builder::max);
        addAggregateSelection(builder, root, aggregates.min(), selections, "min_", builder::min);
        addAggregateSelection(builder, root, aggregates.sum(), selections, "sum_", builder::sum);
        addCountSelections(builder, root, aggregates.count(), selections);
    }

    /**
     * Adds aggregate selections to the criteria query based on the specified aggregates.
     *
     * @param builder The criteria builder used to construct the query.
     * @param root The root entity in the query.
     * @param aggregateFunction The aggregate function to be applied.
     * @param selections A list of selections to which aggregate selections will be added.
     */
    private static void addAggregateSelection(CriteriaBuilder builder, Root<?> root, List<String> fields, List<Selection<?>> selections, String prefix, Function<Expression<Number>, Expression<?>> aggregateFunction) {
        if (fields != null) {
            for (String field : fields) {
                PathObject pathAndAlias = getPathFromField(root, field);
                Path<?> path = pathAndAlias.path();
                String alias = pathAndAlias.alias();
                selections.add(aggregateFunction.apply((Expression<Number>) path).alias(prefix + alias));
            }
        }
    }

    /**
     * Adds count selections to the criteria query based on the specified fields.
     *
     * @param builder The criteria builder used to construct the query.
     * @param root The root entity in the query.
     * @param fields A list of fields to be included in the count selection.
     * @param selections A list of selections to which count selections will be added.
     */
    private static void addCountSelections(CriteriaBuilder builder, Root<?> root, List<String> fields, List<Selection<?>> selections) {
        if (fields != null) {
            for (String field : fields) {
                PathObject pathAndAlias = getPathFromField(root, field);
                Path<?> path = pathAndAlias.path();
                String alias = pathAndAlias.alias();
                selections.add(builder.count(path).alias("count_" + alias));
            }
        }
    }

    /**
     * Adds group by paths to the criteria query based on the specified aggregates.
     *
     * @param root The root entity in the query.
     * @param aggregates Aggregates containing the group by fields.
     * @param groupByPaths A list of paths to which group by paths will be added.
     */
    private static void addGroupByPaths(Root<?> root, Aggregates aggregates, List<Path<?>> groupByPaths) {
        if (aggregates != null && aggregates.groupBy() != null) {
            for (String field : aggregates.groupBy()) {
                PathObject pathAndAlias = getPathFromField(root, field);
                Path<?> path = pathAndAlias.path();
                groupByPaths.add(path);
            }
        }
    }

    /**
     * Retrieves the path and alias from the specified field in the root entity.
     *
     * @param root The root entity in the query.
     * @param field The field for which the path and alias are to be retrieved.
     * @return An array containing the path and alias. The first element is the path, and the second element is the alias.
     */
    private static PathObject getPathFromField(Root<?> root, String field) {
        String[] pathParts = field.split("\\."); // Split the field into segments
        Path<?> currentPath = root; // Start from the root
        StringBuilder joinAlias = new StringBuilder(); // To track alias for joins

        if (!joinsMap.isEmpty()) {
            for (int i = 1; i < pathParts.length; i++) {
                String part = pathParts[i];

                // Construct the current alias for the join (e.g., "Book.author")
                if (!joinAlias.isEmpty()) {
                    joinAlias.append(".");
                }
                joinAlias.append(part);

                if (i < pathParts.length - 1) {
                    // Look for the join in the map (non-leaf nodes in the path)
                    if (joinsMap.containsKey(joinAlias.toString())) {
                        currentPath = joinsMap.get(joinAlias.toString());
                    } else {
                        currentPath = currentPath.get(part);
                    }
                } else {
                    // Handle the leaf node (field, not a join)
                    currentPath = currentPath.get(part);
                }
            }
        }
        else {
            StringBuilder alias = new StringBuilder();

            for (int i = 1; i < pathParts.length; i++) {
                currentPath = currentPath.get(pathParts[i]);
                alias.append((!alias.isEmpty()) ? "." + pathParts[i] : pathParts[i]);
            }

            return new PathObject(currentPath, alias.toString());
        }

        return new PathObject(currentPath, joinAlias.toString());
    }

    /**
     * Finds the primary key field of the specified entity class.
     *
     * @param entityClass The class of the entity for which the primary key field is to be found.
     * @return An Optional containing the name of the primary key field, or an empty Optional if no primary key field is found.
     */
     static String findPrimaryKeyField(Class<?> entityClass) {
        // Inspect superclass for @Id annotated field
        Class<?> currentClass = entityClass;
        while (currentClass != null) {
            for (Field field : currentClass.getDeclaredFields()) {
                if (field.isAnnotationPresent(Id.class)) {
                    // Check for AttributeOverride in subclass
                    AttributeOverride[] overrides = entityClass.getAnnotationsByType(AttributeOverride.class);
                    for (AttributeOverride override : overrides) {
                        if (override.name().equals(field.getName())) {
                            return override.column().name();
                        }
                    }
                    return field.getName();
                }
            }
            currentClass = currentClass.getSuperclass();
        }
        return null;
    }

    /**
     * Creates a predicate based on the provided value and field type.
     *
     * @param builder The criteria builder used to construct the query.
     * @param path The path to the field in the entity.
     * @param value The value to be matched against the field.
     * @param fieldType The type of the field.
     * @return A predicate representing the filter criteria for the field.
     */
    @SuppressWarnings("unchecked") // Made sure these casts are safe
    private static Predicate createPredicate(CriteriaBuilder builder, Path path, Object value, Class<?> fieldType) {
        if (value instanceof Map) {
            Map<String, Object> valueMap = (Map<String, Object>) value;
            /* String operations.*/
            if (valueMap.containsKey(Operator.STARTS.getName())) {
                return builder.like(path, valueMap.get(Operator.STARTS.getName()) + "%");
            }
            else if (valueMap.containsKey(Operator.STARTSIC.getName())) {
                return builder.like(builder.lower(path), ((String) valueMap.get(Operator.STARTSIC.getName())).toLowerCase() + "%");
            }
            else if (valueMap.containsKey(Operator.ENDS.getName())) {
                return builder.like(path, "%" + valueMap.get(Operator.ENDS.getName()));
            }
            else if (valueMap.containsKey(Operator.ENDSIC.getName())) {
                return builder.like(builder.lower(path), "%" + ((String) valueMap.get(Operator.ENDSIC.getName())).toLowerCase());
            }
            else if (valueMap.containsKey(Operator.CONTAINS.getName())) {
                return builder.like(path, "%" + valueMap.get(Operator.CONTAINS.getName()) + "%");
            }
            else if (valueMap.containsKey(Operator.CONTAINSIC.getName())) {
                return builder.like(builder.lower(path), "%" + ((String)valueMap.get(Operator.CONTAINSIC.getName())).toLowerCase() + "%");
            }
            else if (valueMap.containsKey(Operator.EQUALS.getName())) {
                return builder.equal(path, valueMap.get(Operator.EQUALS.getName()));
            }
            else if (valueMap.containsKey(Operator.EQUALSIC.getName())) {
                return builder.equal(builder.lower(path), ((String)valueMap.get(Operator.EQUALSIC.getName())).toLowerCase());
            }
            /* Numeric operations.*/
            else if (valueMap.containsKey(Operator.LT.getName())) {
                Comparable comparableValue = convertIfDate((Comparable) valueMap.get(Operator.LT.getName()));
                return builder.lessThan(path, comparableValue);
            }
            else if (valueMap.containsKey(Operator.LTE.getName())) {
                Comparable comparableValue = convertIfDate((Comparable) valueMap.get(Operator.LTE.getName()));
                return builder.lessThanOrEqualTo(path, comparableValue);
            }
            else if (valueMap.containsKey(Operator.EQ.getName())) {
                if (valueMap.get(Operator.EQ.getName()) == null) {
                    return builder.isNull(path);
                } else {
                    Comparable comparableValue = convertIfDate((Comparable) valueMap.get(Operator.EQ.getName()));
                    return  builder.equal(path, comparableValue);
                }
            }
            else if (valueMap.containsKey(Operator.GT.getName())) {
                Comparable comparableValue = convertIfDate((Comparable) valueMap.get(Operator.GT.getName()));
                return builder.greaterThan(path, comparableValue);
            }
            else if (valueMap.containsKey(Operator.GTE.getName())) {
                Comparable comparableValue = convertIfDate((Comparable) valueMap.get(Operator.GTE.getName()));
                return builder.greaterThanOrEqualTo(path, comparableValue);
            }
            else if (valueMap.containsKey(Operator.IN.getName())) {
                List<Comparable> expressionInValues = (List<Comparable>) valueMap.get(Operator.IN.getName());
                List<Comparable> convertedValues = new ArrayList<>();
                for (Comparable val : expressionInValues) {
                    convertedValues.add(convertIfDate(val));
                }
                return builder.in(path).value(convertedValues);
            }
            else if (valueMap.containsKey(Operator.BETWEEN.getName())) {
                List<Comparable> expressionBetweenValues = (List<Comparable>) valueMap.get(Operator.BETWEEN.getName());
                Comparable start = convertIfDate(expressionBetweenValues.get(0));
                Comparable end = convertIfDate(expressionBetweenValues.get(1));
                return builder.between(path, start, end);
            }
            else
                return builder.equal(path, value);
        }
        return builder.equal(path, convertValue(value, fieldType));
    }

    /**
     * Converts the provided value to the specified field type.
     *
     * @param value The value to be converted.
     * @param fieldType The class of the field type to which the value should be converted.
     * @return The converted value, or the original value if no conversion is needed.
     */
    private static Object convertValue(Object value, Class<?> fieldType) {
        if (fieldType.equals(UUID.class) && value instanceof String) {
            return UUID.fromString((String) value);
        }
        //We can add more types here later if needed
        return value;
    }

    /**
     * Retrieves the path to a field in the entity based on the provided parent path and key.
     *
     * @param root The root entity in the query.
     * @param parentPath The parent path for nested fields.
     * @param key The key representing the field name.
     * @param rootClass The class of the root entity.
     * @return The path to the specified field in the entity.
     */
    private static Path<?> getPath(From<?, ?> root, String parentPath, String key, Class<?> rootClass) {
        String fieldName = getActualFieldName(rootClass, key);
        if (parentPath.isEmpty()) {
            return root.get(getActualFieldName(rootClass, key));
        }
        else {
            if (joinsMap != null && !joinsMap.isEmpty()) {
                String fieldPath = parentPath + "." + fieldName;
                //String rootName = joinsMap.keySet().stream().toList().getFirst().split("\\.")[0];
                String[] paths = fieldPath.split("\\.");
                StringBuilder joinAlias = new StringBuilder();
//                if (!Objects.equals(rootName, ""))
//                    joinAlias.append(rootName);
                Path<?> path = root;

                for (int i = 0; i < paths.length; i++) {
                    String p = paths[i];
                    if (!joinAlias.isEmpty())
                        joinAlias.append(".");
                    joinAlias.append(p);

                    if (i < paths.length - 1) {
                        // Check if a join already exists for this alias
                        if (joinsMap.containsKey(joinAlias.toString())) {
                            path = joinsMap.get(joinAlias.toString());
                        } else {
                            // Traverse dynamically (shouldn't happen if joins are pre-handled)
                            path = path.get(p);
                        }
                    }
                }

                return path.get(fieldName);
            }
            else {
                String[] paths = parentPath.split("\\.");
                Path<?> path = root;
                for (String p : paths) {
                    path = path.get(p);
                }
                return path.get(fieldName);
            }
        }
    }

    /**
     * Checks if the provided key represents a logical operator.
     *
     * @param key The key to be checked.
     * @return true if the key represents a logical operator, false otherwise.
     */
    private static boolean isLogicalOperator(String key) {
        try {
            Operator operator = Operator.getOperator(key);
            return Objects.equals(operator.getType(), "Logical");
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Determines the root operator for combining predicates based on the provided filter map.
     *
     * @param filterMap A map containing filter criteria.
     * @return The root operator as a string (e.g., "and", "or"). Defaults to "and" if no logical operator is found.
     */
    private static String determineRootOperator(Map<String, Object> filterMap) {
        for (String key : filterMap.keySet()) {
            if (isLogicalOperator(key)) {
                return key.toLowerCase();
            }
        }
        return "and";
    }

    /**
     * Combines a list of predicates using the specified logical operator.
     *
     * @param <T> The type of the root entity.
     * @param predicates A list of predicates to be combined.
     * @param builder The criteria builder used to construct the query.
     * @param operator The logical operator to be applied (e.g., AND, OR, NOT).
     * @param query The criteria query to which the combined predicate will be applied.
     * @param root The root entity in the query.
     * @return A combined predicate representing the logical operation.
     */
    private static <T> Predicate combinePredicates(@NotNull List<Predicate> predicates, CriteriaBuilder builder, String operator, CriteriaQuery<?> query, Root<T> root) {
        Predicate combinedPredicate = null;

        for (Predicate predicate : predicates) {
            if (combinedPredicate == null) {
                combinedPredicate = predicate;
            } else {
                Operator op = Operator.getOperator(operator);
                combinedPredicate = switch (op) {
                    case AND -> builder.and(combinedPredicate, predicate);
                    case OR -> builder.or(combinedPredicate, predicate);
                    case NOT -> builder.not(combinedPredicate);
                    //case DISTINCT -> combinedPredicate;
                    default -> combinedPredicate;
                };
            }
        }

        return combinedPredicate;
    }

    /**
     * Retrieves the real type parameter of a class, given a key class.
     * This method traverses the class hierarchy to find the parameterized type argument.
     *
     * @param clazz The class whose type parameter is to be resolved.
     * @param keyClass The key class used for resolving the type parameter.
     * @return The resolved type parameter class.
     * @throws IllegalArgumentException If the type parameter could not be resolved.
     */
    public static Class<?> getRealTypeParameter(Class<?> clazz, Class<?> keyClass) {
        while (clazz != null) {
            // Check if the superclass is parameterized
            Type genericSuperclass = clazz.getGenericSuperclass();
            if (genericSuperclass instanceof ParameterizedType parameterizedType) {
                Type[] typeArguments = parameterizedType.getActualTypeArguments();

                // Assume the first type argument is the one we are resolving
                if (typeArguments.length > 0) {
                    return resolveTypeArgument(typeArguments[0], keyClass);
                }
            }

            // Traverse the implemented interfaces
            for (Type iface : clazz.getGenericInterfaces()) {
                if (iface instanceof ParameterizedType parameterizedInterface) {
                    Type[] typeArguments = parameterizedInterface.getActualTypeArguments();

                    if (typeArguments.length > 0) {
                        return resolveTypeArgument(typeArguments[0], keyClass);
                    }
                }
            }

            // Move up the hierarchy
            clazz = clazz.getSuperclass();
        }

        throw new IllegalArgumentException("Could not resolve the type parameter.");
    }


    /**
     * Resolves the type argument of a parameterized type.
     *
     * @param typeArgument The type argument to be resolved.
     * @param keyClass The key class used for resolving the type argument.
     * @return The resolved type argument class.
     */
    static Class<?> resolveTypeArgument(Type typeArgument, Class<?> keyClass) {
        if (typeArgument instanceof Class<?>) {
            return (Class<?>) typeArgument;
        } else if (typeArgument instanceof ParameterizedType) {
            return (Class<?>) ((ParameterizedType) typeArgument).getRawType();
        } else {
            return keyClass;
        }
    }

    /**
     * Checks if the specified field in the given class is a complex field.
     * <p>
     * A complex field is defined as a field that is not primitive, not a String,
     * not overridden, and not one of the following types: UUID, Date, LocalDate,
     * LocalDateTime, OffsetDateTime.
     *
     * @param clazz The class containing the field.
     * @param fieldName The name of the field to check.
     * @return true if the field is complex, false otherwise.
     * @throws NoSuchFieldException If the field is not found in the class.
     */
    private static boolean isComplexField(Class<?> clazz, Class<?> keyClass, String fieldName) throws NoSuchFieldException {
        try {
            Class<?> parentParamClass = null;
            Class<?> fieldType = getActualFieldType(clazz, fieldName);
            if (fieldType == Serializable.class) {
                parentParamClass = getRealTypeParameter(clazz, keyClass);
                fieldType = parentParamClass;
            }

            return !fieldType.isPrimitive()
                    && !fieldType.equals(String.class)
                    && !isOverriddenField(clazz, fieldName)
                    && !fieldType.equals(UUID.class)
                    && !fieldType.equals(Date.class)
                    && !fieldType.equals(LocalDate.class)
                    && !fieldType.equals(LocalDateTime.class)
                    && !fieldType.equals(OffsetDateTime.class);
        } catch (NoSuchFieldException e) {
            throw new NoSuchFieldException(fieldName);
        }
    }

    /**
     * Checks if the specified field in the given class is overridden.
     * <p>
     * This method traverses the class hierarchy to check for the presence of the @AttributeOverride annotation
     * on the specified field. If the annotation is found, it indicates that the field is overridden.
     *
     * @param clazz The class containing the field.
     * @param fieldName The name of the field to check.
     * @return true if the field is overridden, false otherwise.
     */
    static boolean isOverriddenField(Class<?> clazz, String fieldName) {
        // Check for @AttributeOverride
        while (clazz != null) {
            for (AttributeOverride override : clazz.getDeclaredAnnotationsByType(AttributeOverride.class)) {
                if (override.name().equals(fieldName)) {
                    return true;
                }
            }
            clazz = clazz.getSuperclass();
        }
        return false;
    }

    /**
     * Retrieves the type of the specified field in the given class.
     * <p>
     * This method attempts to get the actual field type of the specified field in the provided class.
     * If the field is not found, it throws a RuntimeException.
     *
     * @param clazz The class containing the field.
     * @param fieldName The name of the field whose type is to be retrieved.
     * @return The type of the specified field.
     * @throws RuntimeException If the field is not found in the class.
     */
    private static Class<?> getFieldType(Class<?> clazz, String fieldName) {
        try {
            return getActualFieldType(clazz, fieldName);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves the actual type of the specified field in the given class.
     * <p>
     * This method traverses the class hierarchy to find the field with the specified name
     * and returns its type. If the field is not found, it throws a NoSuchFieldException.
     *
     * @param clazz The class containing the field.
     * @param fieldName The name of the field whose type is to be retrieved.
     * @return The type of the specified field.
     * @throws NoSuchFieldException If the field is not found in the class.
     */
    static Class<?> getActualFieldType(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        // Traverse the class hierarchy to find the field
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.getName().equals(fieldName)) {
                    return field.getType();
                }
            }
            clazz = clazz.getSuperclass();
        }
        throw new NoSuchFieldException(fieldName);
    }

    /**
     * Retrieves the actual field name of the specified field in the given class.
     *
     * This method traverses the class hierarchy to find the field with the specified name
     * and checks for the presence of the @AttributeOverride annotation. If the annotation
     * is found, it returns the overridden column name. If the field is not found, it returns
     * the original field name.
     *
     * @param clazz The class containing the field.
     * @param fieldName The name of the field whose actual name is to be retrieved.
     * @return The actual field name, or the original field name if no override is found.
     */
    static String getActualFieldName(Class<?> clazz, String fieldName) {
        // Check for @AttributeOverride
        while (clazz != null) {
            for (AttributeOverride override : clazz.getDeclaredAnnotationsByType(AttributeOverride.class)) {
                if (override.name().equals(fieldName)) {
                    return override.column().name();
                }
            }
            clazz = clazz.getSuperclass();
        }
        return fieldName;
    }

    /**
     * Converts the provided value to a `Date` if it is an instance of `LocalDate`, `LocalDateTime`, or `OffsetDateTime`.
     *
     * This method checks the type of the provided value and converts it to a `Date` object if it is a date-related type.
     * If the value is `null` or not a date-related type, it returns the original value.
     *
     * @param value The value to be converted.
     * @return The converted `Date` object if the value is a date-related type, otherwise the original value.
     */
    private static Comparable convertIfDate(Comparable value) {
        if (value == null) {
            return null;
        }
        if (value instanceof LocalDate localDate) {
            value = Date.from(localDate.atStartOfDay()
                    .atZone(ZoneId.systemDefault())
                    .toInstant());
        } else if (value instanceof LocalDateTime localDateTime) {
            value = Date
                    .from(localDateTime.atZone(ZoneId.systemDefault())
                            .toInstant());
        } else if (value instanceof OffsetDateTime offsetDateTime) {
            value = Date
                    .from(offsetDateTime.toInstant());
        }
        return value;
    }

    /**
     * Cleans the list of fields by removing complex fields from the list.
     * <p>
     * This method creates a copy of the provided fields list to avoid concurrent modification issues.
     * It iterates through the fields and removes any complex fields from the list.
     *
     * @param rootClass The class of the root entity.
     * @param fields A list of fields to be cleaned.
     * @return A list of cleaned fields with complex fields removed.
     * @throws NoSuchFieldException If a field specified in the fields list is not found in the root class.
     */
    public static List<String> CleanFields(Class<?> rootClass, Class<?> keyClass, List<String> fields) throws NoSuchFieldException {
        // Create a copy of the fields list to avoid concurrent modification issues
        if (fields == null)
            return new ArrayList<>();

        List<String> cleanedFields = new ArrayList<>(fields);

        for (int i = 0; i < cleanedFields.size(); i++) {
            if (!Objects.equals(cleanedFields.get(i), "currentPage") && !Objects.equals(cleanedFields.get(i), "pageSize") && !Objects.equals(cleanedFields.get(i), "totalPages") && !Objects.equals(cleanedFields.get(i), "currentPageCount") && !Objects.equals(cleanedFields.get(i), "total")) {
                String[] temp = cleanedFields.get(i).split("\\.");
                Class<?> currentClass = rootClass;  // Reset rootClass for each field entry

                // Start from the second element (index 1)
                for (int j = 1; j < temp.length; j++) {
                    String fieldName = temp[j];

                    if (isComplexField(currentClass, keyClass, fieldName)) {
                        // Mark as complex if we encounter a complex field
                        // If we are at the last element and it's complex, remove the entry
                        if (j + 1 == temp.length) {
                            cleanedFields.remove(i);
                            i--;  // Adjust the index after removal
                            break;
                        } else {
                            // Update the current class to the complex field's type for further checks
                            currentClass = getFieldType(currentClass, fieldName);
                        }
                    }
                }
            }
            else {
                pagingElementsToInclude.add(cleanedFields.get(i));
                cleanedFields.remove(i);
                i--;
            }
        }

        return cleanedFields;
    }
}
