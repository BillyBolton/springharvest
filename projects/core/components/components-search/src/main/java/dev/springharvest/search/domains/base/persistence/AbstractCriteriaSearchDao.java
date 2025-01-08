package dev.springharvest.search.domains.base.persistence;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import dev.springharvest.errors.constants.ExceptionMessages;
import dev.springharvest.errors.models.ClientException;
import dev.springharvest.errors.models.ExceptionDetail;
import dev.springharvest.search.domains.base.models.queries.parameters.base.BaseParameterBO;
import dev.springharvest.search.domains.base.models.queries.parameters.filters.CriteriaOperator;
import dev.springharvest.search.domains.base.models.queries.parameters.filters.FilterParameterBO;
import dev.springharvest.search.domains.base.models.queries.parameters.selections.SelectionBO;
import dev.springharvest.search.domains.base.models.queries.requests.filters.BaseFilterBO;
import dev.springharvest.search.domains.base.models.queries.requests.filters.BaseFilterRequestBO;
import dev.springharvest.search.domains.base.models.queries.requests.search.SearchRequest;
import dev.springharvest.shared.domains.base.models.entities.BaseEntity;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Id;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Selection;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * This class is used to provide a base implementation of the criteria search
 *
 * @param <E>  The entity domain model.
 * @param <K>  The type of the primary key contained within the entity.
 * @param <RB> The type of the filter request that the SearchRequest will contain.
 * @see ICriteriaSearchRepository
 * @see SearchRequest
 */
@Slf4j
public abstract class AbstractCriteriaSearchDao<E extends BaseEntity<K>, K extends Serializable,
    RB extends BaseFilterRequestBO>
    implements ICriteriaSearchRepository<E, RB> {

  @Getter
  private final Class<E> clazz;

  @Getter
  private final String rootPath;
  private final Function<Tuple, E> tupleTransformer;
  @PersistenceContext
  protected EntityManager entityManager;
  
  public void setEntityManager(EntityManager entityManager) {
      this.entityManager = entityManager;
  }


  @Autowired
  protected AbstractCriteriaSearchDao(String rootPath, Function<Tuple, E> tupleTransformer) {

    this.rootPath = rootPath;

    this.tupleTransformer = tupleTransformer;

    // Setting clazz
    ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
    @SuppressWarnings("unchecked") Class<E> entityClass = (Class<E>) parameterizedType.getActualTypeArguments()[0];
    this.clazz = entityClass;

  }

  @Override
  public boolean existsByUnique(SearchRequest<RB> searchRequest) {
    return count(searchRequest) > 0;
  }

  @Override
  public boolean exists(SearchRequest<RB> searchRequest) {
    return count(searchRequest) > 0;
  }

  @Override
  public int count(SearchRequest<RB> searchRequest) {
    List<E> results = search(searchRequest);
    return results.size();
  }

  @SuppressWarnings("unchecked")
  public List<E> search(SearchRequest<RB> searchRequest) {

    boolean isSelectAll = CollectionUtils.isEmpty(searchRequest.getSelections());
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    Map<String, Path<E>> joinMap = new HashMap<>();

    CriteriaQuery<E> entityQuery = cb.createQuery(getClazz());
    CriteriaQuery<Tuple> tupleQuery = cb.createQuery(Tuple.class);

    Root<E> root = isSelectAll ? entityQuery.from(getClazz()) : tupleQuery.from(getClazz());

    // SELECT
    if (isSelectAll) {
      entityQuery.select(root);
      setSortingOrder(cb, searchRequest, root, rootPath, joinMap, entityQuery);
    } else {
      tupleQuery.multiselect(buildSelections(root, searchRequest.getSelections(), joinMap));
      setSortingOrder(cb, searchRequest, root, rootPath, joinMap, tupleQuery);
    }

    // WHERE
    CriteriaBuilderHelper.buildAndSetPredicates(cb, searchRequest, root, rootPath, joinMap,
                                                isSelectAll ? entityQuery : tupleQuery);

    // Handle Pagination
    TypedQuery<?> typedQuery =
        isSelectAll ? entityManager.createQuery(entityQuery) : entityManager.createQuery(tupleQuery);
    CriteriaBuilderHelper.setPagination(typedQuery, searchRequest);

    if (isSelectAll) {
      return (List<E>) typedQuery.getResultList();
    } else {
      return aggregateAssociatedEntityLists(
          ((List<Tuple>) typedQuery.getResultList()).stream().map(tupleTransformer).toList());
    }

  }

  private void setSortingOrder(CriteriaBuilder cb, SearchRequest<RB> searchRequest, Root<E> root, String rootPath,
                               Map<String, Path<E>> joinMap, CriteriaQuery<?> query) {

    List<Order> orderList = new LinkedList<>();
    PriorityQueue<SelectionBO> selectionsByPriority = searchRequest.getSelectionsByPriority(true);
    while (!selectionsByPriority.isEmpty()) {
      SelectionBO selection = selectionsByPriority.poll();
      if (selection.getIsAscending() != null) {
        orderList.add(Boolean.TRUE.equals(selection.getIsAscending()) ? cb.asc(
            CriteriaBuilderHelper.getPath(selection, root, rootPath, joinMap)) : cb.desc(
            CriteriaBuilderHelper.getPath(selection, root, rootPath, joinMap)));
      }
    }
    query.orderBy(orderList);

  }

  /**
   * A helper method that will build the selection list for the query based on the search request.
   *
   * @param root       The root of the query.
   * @param selections The list of selections that will be used to build the selection list.
   * @param joinMap    The map of joins that will be used to build the selection list.
   * @return The selection list.
   * @see Root
   * @see SelectionBO
   * @see Path
   */
  private Selection<?>[] buildSelections(Root<E> root, List<SelectionBO> selections, Map<String, Path<E>> joinMap) {
    return selections.stream().map(selection -> buildSelection(root, selection, joinMap)).toArray(Selection[]::new);
  }

  /**
   * A helper method that will build a selection for the query based on the search request.
   *
   * @param root      The root of the query.
   * @param selection The selection that will be used to build the selection list.
   * @param joinMap   The map of joins that will be used to build the selection list.
   * @return The selection list.
   * @see Root
   * @see SelectionBO
   * @see Path
   */
  private Selection<?> buildSelection(Root<E> root, SelectionBO selection, Map<String, Path<E>> joinMap) {
    CriteriaBuilderHelper.debugPaths(true, getClazz());

    String fullPath = selection.getPath();

    if (StringUtils.isBlank(fullPath)) {
      throw ClientException.builder()
          .message("Path is required")
          .details(List.of(ExceptionDetail.builder()
                               .field("SelectionBO selection")
                               .message(selection.getAlias())
                               .build()))
          .build();
    }

    return CriteriaBuilderHelper.getPath(selection, root, rootPath, joinMap);

  }

  /**
   * A helper method to aggregate a list of associated entities to the entity that owns this many-to-one relationship.
   *
   * @param entities The list of entities that match the search request and need to aggregate the associated entities.
   * @return Entities that been processed.
   * @see BaseEntity
   */
  protected List<E> aggregateAssociatedEntityLists(List<E> entities) {
    return entities;
  }

  /**
   * A helper class that will holds the utility methods needed to support building a query.
   */
  private static class CriteriaBuilderHelper {

    private CriteriaBuilderHelper() {
      throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
    }

    /**
     * A utility debugging method that will recursively iterate the entity class and build a list of paths to each attribute it has.
     *
     * @param debug A flag that identifies whether to debug.
     * @return A list of paths to each attribute.
     * @see Class
     */
    private static <E> List<String> debugPaths(boolean debug, Class<E> entityClass) {

      if (!debug) {
        return Collections.emptyList();
      }

      List<String> paths = new ArrayList<>();
      Field[] fields = entityClass.getDeclaredFields();
      for (Field field : fields) {
        if (field.isAnnotationPresent(Id.class)) {
          paths.add(field.getName());
        } else if (field.isAnnotationPresent(Embedded.class) || field.isAnnotationPresent(Entity.class)) {
          Class<?> fieldType = field.getType();
          for (String subPath : debugPaths(true, fieldType)) {
            paths.add(field.getName() + "." + subPath);
          }
        } else {
          paths.add(field.getName());
        }
      }
      return paths;
    }


    /**
     * A utility method that will build a map of the filter parameters in a search query, with the key as the variable name that holds the FilterParameterBO,
     * and the value as the FilterParameterBO. Java Reflection is used to identify the key, so naming conventions is important.
     *
     * @param filterRequestBO The filter parameter business object that is contained in a Search Request and will be used to build the map for this method.
     * @return A map of the filter parameters in a search query.
     * @see BaseFilterRequestBO
     * @see FilterParameterBO
     * @see SearchRequest
     * @see #aggregateFilterParameters(BaseFilterBO)
     */
    private static Map<String, FilterParameterBO> getParameters(BaseFilterRequestBO filterRequestBO) {
      Map<String, FilterParameterBO> filterParametersMap = new LinkedHashMap<>();

      // Iterate through all fields in the filterRequestBO object
      Field[] fields = filterRequestBO.getClass().getDeclaredFields();
      for (Field field : fields) {
        // Check if the field is a BaseFilterBO object
        if (BaseFilterBO.class.isAssignableFrom(field.getType())) {
          try {
            // Get the value of the field
            field.setAccessible(true);
            BaseFilterBO filterBO = (BaseFilterBO) field.get(filterRequestBO);
            if (filterBO != null) {
              // Recursively aggregate the FilterParameterBOs in the filterBO object
              filterParametersMap.putAll(aggregateFilterParameters(filterBO));
            }
          } catch (IllegalAccessException e) {
            // Handle exception
          }
        }
      }

      return filterParametersMap;
    }

    /**
     * A utility method that will recursively iterate through a BaseFilterBO object and aggregate them into a map.
     *
     * @param filterBO The filter parameter business object that will be used to build the map for this method.
     * @return A map of the filter parameters in a search query.
     * @see BaseFilterBO
     * @see FilterParameterBO
     * @see #getParameters(BaseFilterRequestBO)
     */
    private static Map<String, FilterParameterBO> aggregateFilterParameters(BaseFilterBO filterBO) {
      Map<String, FilterParameterBO> filterParametersMap = new LinkedHashMap<>();

      // Iterate through all fields in the filterBO object
      Field[] fields = filterBO.getClass().getDeclaredFields();
      for (Field field : fields) {
        // Check if the field is a BaseFilterBO object
        if (BaseFilterBO.class.isAssignableFrom(field.getType())) {
          try {
            // Get the value of the field
            field.setAccessible(true);
            BaseFilterBO subFilterBO = (BaseFilterBO) field.get(filterBO);
            if (subFilterBO != null) {
              // Recursively aggregate the FilterParameterBOs in the sub-filterBO
              // object
              filterParametersMap.putAll(aggregateFilterParameters(subFilterBO));
            }
          } catch (IllegalAccessException e) {
            // Handle exception
          }
        }
      }

      // Aggregate the FilterParameterBOs in the current filterBO object
      Field[] filterParameterFields = filterBO.getClass().getDeclaredFields();
      for (Field field : filterParameterFields) {
        if (FilterParameterBO.class.isAssignableFrom(field.getType())) {
          try {
            // Get the value of the field
            field.setAccessible(true);
            FilterParameterBO filterParameterBO = (FilterParameterBO) field.get(filterBO);
            if (filterBO != null && filterParameterBO != null && filterParameterBO.getPath() != null) {
              filterParametersMap.put(filterParameterBO.getPath(), filterParameterBO);
            }

          } catch (IllegalAccessException e) {
            // Handle exception
          }
        }
      }

      return filterParametersMap;
    }

    /**
     * A utility method that will build a Path object used for a query.
     *
     * @param parameterBO The parameter business object that will be used to build the Path object for this method.
     * @param root        The root object that will be used to build the Path object for this method.
     * @param rootPath    The root path that will be used to build the Path object for this method.
     * @param joinMap     The join map that will be used to build the Path object for this method.
     * @param <E>         The entity type being queried.
     * @param <K>         The type of the primary key of the entity.
     * @return A Path object used for a query.
     * @see BaseParameterBO
     * @see Root
     * @see Path
     */
    private static <E extends BaseEntity<K>, K extends Serializable> Path<E> getPath(BaseParameterBO parameterBO,
                                                                                     Root<E> root, String rootPath,
                                                                                     Map<String, Path<E>> joinMap) {

      String[] pathArray = getPathArray(parameterBO.getPath());

      if (pathArray.length == 1) {
        throw new IllegalArgumentException("Path must be at least 2 levels deep");
      }

      boolean isJoined = parameterBO.isJoined();
      int rootIndex = getRootPathIndex(pathArray, Set.of(rootPath), isJoined);

      if (isJoined) {
        joinMap.computeIfAbsent(pathArray[0], value -> root.join(pathArray[rootIndex]));
      }

      Path<E> curr = isJoined ? joinMap.get(pathArray[0]) : root.get(pathArray[rootIndex]);

      for (int i = rootIndex + 1; i < pathArray.length; i++) {
        curr = curr.get(pathArray[i]);
      }
      curr.alias(parameterBO.getAlias());
      return curr;
    }


    /**
     * A utility method that will split a path string into an array of strings.
     *
     * @param path The path string that will be split into an array of strings.
     * @return An array of strings that represents the path.
     */
    private static String[] getPathArray(String path) {
      return path.split("\\.");
    }

    /**
     * A utility method that will get the index of the root path in the path array.
     *
     * @param pathArray The path array that will be used to get the index of the root path.
     * @param rootPaths The root paths that will be used to get the index of the root path.
     * @param isJoined  The boolean flag that is used to identify whether the path is from a joined object with respect to the Root object.
     * @return
     * @see #getPath(BaseParameterBO, Root, String, Map)
     * @see Path
     * @see Root
     */
    private static int getRootPathIndex(String[] pathArray, Set<String> rootPaths, boolean isJoined) {
      if (isJoined) {
        return 0;
      }
      int i = 0;
      for (; i < pathArray.length; i++) {
        if (CollectionUtils.containsAny(rootPaths, Set.of(pathArray[i]))) {
          break;
        }
      }
      return ++i;
    }

    /**
     * A utility method that will build a Predicate object used for a query.
     *
     * @param entry    The entry of the variable name that holds the FilterParameterBO object, and the value as the actual FilterParameterBO object.
     * @param root     The root object of the query.
     * @param rootPath The root path of the query.
     * @param joinMap  The map of joined entities with respect to the root entity.
     * @param cb       The CriteriaBuilder object used to build the Predicate object.
     * @param <E>      The entity type being queried.
     * @param <K>      The type of the primary key of the entity.
     * @return A List of Predicate objects used for a query.
     * @see Predicate
     * @see FilterParameterBO
     * @see Root
     * @see Path
     * @see CriteriaBuilder
     */
    private static <E extends BaseEntity<K>, K extends Serializable> List<Predicate> getPredicateValues(
        Map.Entry<String, FilterParameterBO> entry, Root<E> root, String rootPath, Map<String, Path<E>> joinMap,
        CriteriaBuilder cb) {

      FilterParameterBO parameter = entry.getValue();

      Path<?> curr = getPath(parameter, root, rootPath, joinMap);

      List<Object> convertedValues = new LinkedList<>();
      List<Predicate> valuePredicates = new ArrayList<>();

      if (CollectionUtils.isEmpty(parameter.getValues())) {
        return valuePredicates;
      }
      for (Object value : parameter.getValues()) {
        if (value == null) {
          continue;
        }
        log.debug("Param Clazz: {}", parameter.getClazz());
        if (String.class.equals(parameter.getClazz())) {
          convertedValues.add(value.toString());
        } else if (Integer.class.equals(parameter.getClazz())) {
          convertedValues.add(Integer.parseInt(value.toString()));
        } else if (Long.class.equals(parameter.getClazz())) {
          convertedValues.add(Long.parseLong(value.toString()));
        } else if (Double.class.equals(parameter.getClazz())) {
          convertedValues.add(Double.parseDouble(value.toString()));
        } else if (Float.class.equals(parameter.getClazz())) {
          convertedValues.add(Float.parseFloat(value.toString()));
        } else if (BigDecimal.class.equals(parameter.getClazz())) {
          convertedValues.add(BigDecimal.valueOf(Long.parseLong(value.toString())));
        } else if (Boolean.class.equals(parameter.getClazz())) {
          convertedValues.add(Boolean.parseBoolean(value.toString()));
        } else if (UUID.class.equals(parameter.getClazz())) {
          try {
            convertedValues.add(UUID.fromString(value.toString()));
          } catch (IllegalArgumentException e) {
            log.error("Failed to parse UUID from value: " + value);
            // Handle the error or continue with an appropriate fallback value
            continue; // Skip this value and move to the next one
          }
        } else {
          log.error("Invalid value type: " + parameter.getClazz());
          log.error("Invalid value type with alias: " + parameter.getAlias());
          log.error("Ensure parameter.getClazz(path) exists in GlobalClassResolver.java");
          throw new IllegalArgumentException("Invalid value type: " + parameter.getClazz());
        }
      }

      Predicate predicate = null;
      CriteriaOperator operator = parameter.getOperator() == null ? CriteriaOperator.IN : parameter.getOperator();
      boolean isOperatorSingular = List.of(CriteriaOperator.EQUALS, CriteriaOperator.NOT_EQUALS).contains(operator);
      if (isOperatorSingular) {
        convertedValues = convertedValues.subList(0, 1);
      }
      switch (operator) {
        case CONTAINS -> {
          // TODO:
        }
        case NOT_CONTAINS -> {
          // TODO:
        }
        case STARTS_WITH -> {
          // TODO:
        }
        case ENDS_WITH -> {
          // TODO:
        }
        case LESS_THAN -> {

          // TODO:
        }
        case LESS_THAN_OR_EQUAL -> {
          // TODO:
        }
        case GREATER_THAN -> {
          // TODO:
        }
        case GREATER_THAN_OR_EQUAL -> {
          // TODO:
        }
        case EQUALS -> {
          if (!convertedValues.isEmpty()) {
            predicate = cb.equal(curr, convertedValues.stream().findFirst().get());
          }
        }
        case NOT_EQUALS -> {
          if (!convertedValues.isEmpty()) {
            predicate = cb.notEqual(curr, convertedValues.stream().findFirst().get());
          }
        }
        case IN -> predicate = curr.in(convertedValues);
        case NOT_IN -> predicate = cb.not(curr.in(convertedValues));
        case IS_NULL -> {
          // TODO:
        }
        case IS_NOT_NULL -> {
          // TODO:
        }
        case IS_EMPTY -> {
          // TODO:
        }
        case IS_NOT_EMPTY -> {
          // TODO:
        }
        case BEFORE -> {
          // TODO:
        }
        case AFTER -> {
          // TODO:
        }
        case LIKE -> {
          // TODO:
        }
      }
      valuePredicates.add(predicate);
      return valuePredicates;
    }

    /**
     * A utility method that will set the pagination for a query.
     *
     * @param typedQuery    The TypedQuery object that will be used to set the pagination.
     * @param searchRequest The SearchRequest object that will be used to set the pagination.
     * @param <RB>          The type of the BaseFilterRequestBO object.
     * @see TypedQuery
     * @see SearchRequest
     */
    private static <RB extends BaseFilterRequestBO> void setPagination(TypedQuery<?> typedQuery,
                                                                       SearchRequest<RB> searchRequest) {

      if (searchRequest.isPageable()) {
        typedQuery.setFirstResult(searchRequest.getPage().getFirstResult());
        typedQuery.setMaxResults(searchRequest.getPage().getMaxResults());
      }
    }

    /**
     * A utility method that will build and set the predicates for a query.
     *
     * @param cb            CriteriaBuilder
     * @param searchRequest SearchRequest
     * @param root          Root
     * @param rootPath      The path to the root entity
     * @param joinMap       The map of joined entities with respect to the root entity
     * @param query         CriteriaQuery
     * @param <RB>          The type of the BaseFilterRequestBO object.
     * @param <E>           The entity type being queried.
     * @param <K>           The type of the primary key of the entity.
     * @see CriteriaBuilder
     * @see SearchRequest
     * @see Root
     * @see Path
     * @see CriteriaQuery
     */
    private static <RB extends BaseFilterRequestBO, E extends BaseEntity<K>, K extends Serializable> void buildAndSetPredicates(
        CriteriaBuilder cb, SearchRequest<RB> searchRequest, Root<E> root, String rootPath,
        Map<String, Path<E>> joinMap, CriteriaQuery<?> query) {

      if (CollectionUtils.isEmpty(searchRequest.getFilters())) {
        return;
      }

      List<List<Predicate>> orPredicates = new ArrayList<>();

      Set<RB> filters = searchRequest.getFilters();
      for (RB filter : filters) {
        List<Predicate> andPredicates = new ArrayList<>();
        for (Map.Entry<String, FilterParameterBO> entry : CriteriaBuilderHelper.getParameters(filter).entrySet()) {
          List<Predicate> predicates = CriteriaBuilderHelper.getPredicateValues(entry, root, rootPath, joinMap, cb);
          if (!predicates.isEmpty()) {
            andPredicates.add(cb.and(predicates.toArray(new Predicate[0])));
          }
        }

        orPredicates.add(andPredicates);
      }
      query.where(cb.or(orPredicates.stream().map(predicates -> cb.and(predicates.toArray(new Predicate[0]))).toArray(Predicate[]::new)));
    }

  }



}
