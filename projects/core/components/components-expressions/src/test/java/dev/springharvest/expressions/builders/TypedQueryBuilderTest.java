package dev.springharvest.expressions.builders;

import dev.springharvest.expressions.helpers.Operation;
import dev.springharvest.expressions.mappers.GenericEntityMapper;
import dev.springharvest.shared.constants.*;
import dev.springharvest.shared.domains.base.models.entities.BaseEntity;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TypedQueryBuilderTest {

    private class Author extends BaseEntity<UUID> {
        public String name;
        public Pet pet;
        public Author(String name, Pet pet){
            this.name = name;
            this.pet = pet;
        }
    }
    private class Pet extends BaseEntity<UUID>{
        public String name;
        public Pet(String name) {
            this.name = name;
        }

    }

    @Mock
    private EntityManagerFactory entityManagerFactory;

    @Mock
    private EntityManager entityManager;

    @Mock
    private CriteriaBuilder criteriaBuilder;

    @Mock
    private CriteriaQuery<Tuple> criteriaQuery;

    @Mock
    private CriteriaQuery<Long> countQuery;

    @Mock
    private Root<?> countRoot;

    @Mock
    private Root<Author> root;

    @Mock
    private Path<Object> authorPath;

    @Mock
    private Path<Object> petPath;

    @Mock
    private Path<Object> petNamePath;

    @Mock
    Subquery<Integer> subquery;

    @Mock
    private Root<?> subRoot;

    @Mock
    private TypedQuery<Long> typedCountQuery;

    @Mock
    private TypedQuery<Tuple> query;

    @Mock
    GenericEntityMapper<?> mapper;

    @Mock
    From<?, ?> currentFrom;

    @Mock
    Join<?, ?> petJoin;

    @Mock
    private Join<?, ?> petNameJoinPath;

    @Mock
    private Expression<Long> countExpression;

    @Mock
    Predicate mockPredicate;

    private Class<Author> entityClass = Author.class;
    private Class<UUID> keyClass = UUID.class;

    @InjectMocks
    private TypedQueryBuilder typedQueryBuilder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        typedQueryBuilder = new TypedQueryBuilder() {};
        typedQueryBuilder.setEntityManagerFactory(entityManagerFactory);

        when(entityManagerFactory.createEntityManager()).thenReturn(entityManager);
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Tuple.class)).thenReturn(criteriaQuery);
        when(criteriaBuilder.createQuery(Long.class)).thenReturn(countQuery);
        when(criteriaQuery.from(entityClass)).thenReturn(root);
        when(countQuery.from(root.getJavaType())).thenReturn((Root)countRoot);
        //when(countQuery.subquery(Tuple.class)).thenReturn(subquery);
        when(countQuery.subquery(Integer.class)).thenReturn(subquery);
        when(subquery.from(countRoot.getJavaType())).thenReturn((Root)subRoot);
        when(countQuery.select(any())).thenReturn(countQuery);
        when(countQuery.where(any(Expression.class))).thenReturn(countQuery);
        when(subquery.select(any())).thenReturn(subquery);
        when(subquery.where(any(Expression.class))).thenReturn(subquery);
        when(entityManager.createQuery(countQuery)).thenReturn(typedCountQuery);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(query);
        when(criteriaBuilder.count(any(Path.class))).thenReturn(countExpression);
        when(countExpression.alias(anyString())).thenReturn(countExpression);
        when(criteriaBuilder.not(any(Predicate.class))).thenReturn(mockPredicate);
    }

    @Test
    void parseFilterExpression_withValidInputs_returnsExpectedResult() {
        Map<String, Object> filterMap = new HashMap<>();
        Map<String, Object> clauseMap = new HashMap<>();
        Map<String, Object> equals = new LinkedHashMap<>();
        equals.put("containsic", "Paulo");
        filterMap.put("name", equals);
        List<String> fields = Arrays.asList("Author.name", "Author.pet.name");
        List<Author> resultList = List.of(new Author("Paulo Coelo", new Pet("Jiji")), new Author("De la Fontaine", new Pet("Patrache")));
        Map<String, JoinType> joins = new HashMap<>();
        Aggregates aggregates = null;
        DataPaging paging = new DataPaging(1, 10, Collections.emptyList());


        when(root.get("name")).thenReturn(authorPath);
        when(root.get("pet")).thenReturn(petPath);
        when(petPath.get("name")).thenReturn(petNamePath);

        when(subRoot.get("name")).thenReturn(authorPath);
        when(subRoot.get("pet")).thenReturn(petPath);
        when(petPath.get("name")).thenReturn(petNamePath);

        when(typedCountQuery.getSingleResult()).thenReturn(1L);

        when(mapper.mapTuplesToEntity(anyList(), any())).thenReturn((List)(List.of(resultList.get(0))));

        PageData<Author> result = (PageData<Author>) typedQueryBuilder.parseFilterExpression(Operation.SEARCH, entityClass, keyClass, filterMap, clauseMap, fields, joins, aggregates, paging);

        assertNotNull(result);
    }

    @Test
    void parseFilterExpression_withNullFilterMap_returnsExpectedResult() {
        Map<String, Object> clauseMap = new HashMap<>();
        clauseMap.put("distinct", "true");
        List<String> fields = Arrays.asList("Author.name", "Author.pet.name");
        Map<String, JoinType> joins = new HashMap<>();
        joins.put("Author.pet", JoinType.LEFT);
        Aggregates aggregates = null;
        DataPaging paging = new DataPaging(1, 10, List.of(new Sort("pet.name", SortDirection.DESC)));
        List<Author> resultList = List.of(new Author("Paulo Coelo", new Pet("Jiji")), new Author("De la Fontaine", new Pet("Patrache")));

        when(root.get("name")).thenReturn(authorPath);
        when(root.get("pet")).thenReturn(petPath);
        when(petPath.get("name")).thenReturn(petNamePath);

        when(subRoot.get("name")).thenReturn(authorPath);
        when(subRoot.get("pet")).thenReturn(petPath);
        when(petPath.get("name")).thenReturn(petNamePath);

        when(((From<?, ?>) root).join("pet", JoinType.LEFT)).thenReturn((Join)petJoin);
        when(((From<?, ?>) subRoot).join("pet", JoinType.LEFT)).thenReturn((Join)petJoin);
        when(petJoin.get("name")).thenReturn((Path) petNameJoinPath);
        when(typedCountQuery.getSingleResult()).thenReturn(2L);
        when(mapper.mapTuplesToEntity(anyList(), any())).thenReturn((List)resultList);

        PageData<Author> result = (PageData<Author>) typedQueryBuilder.parseFilterExpression(Operation.SEARCH, entityClass, keyClass, null, clauseMap, fields, joins, aggregates, paging);

        assertNotNull(result);
    }

    @Test
    void parseFilterExpression_withEmptyFilterMap_returnsExpectedResult() {
        Map<String, Object> filter = new HashMap<>();
        Map<String, Object> clauseMap = new HashMap<>();
        clauseMap.put("distinct", "true");
        List<String> fields = Arrays.asList("Author.name", "Author.pet.name");
        Map<String, JoinType> joins = new HashMap<>();
        Aggregates aggregates = new Aggregates(List.of("Author.name"), null, null, null, null, List.of("Author.pet.name"));
        DataPaging paging = new DataPaging(1, 10, List.of(new Sort("pet.name", SortDirection.DESC)));
        List<Object> results = getObjects();

        when(root.get("name")).thenReturn(authorPath);
        when(root.get("pet")).thenReturn(petPath);
        when(petPath.get("name")).thenReturn(petNamePath);

        when(subRoot.get("name")).thenReturn(authorPath);
        when(subRoot.get("pet")).thenReturn(petPath);
        when(petPath.get("name")).thenReturn(petNamePath);

        when(typedCountQuery.getSingleResult()).thenReturn(2L);
        when(mapper.mapTuplesToMap(anyList())).thenReturn((List)results);

        Object result = typedQueryBuilder.parseFilterExpression(Operation.SEARCH, entityClass, keyClass, filter, clauseMap, fields, joins, aggregates, paging);

        assertInstanceOf(List.class, result);
    }

    @NotNull
    private static List<Object> getObjects() {
        Map<String, Object> authorsDetail1 = new HashMap<>();
        authorsDetail1.put("name", "Paulo Coelo");
        authorsDetail1.put("pet.name", "Jiji");
        authorsDetail1.put("count_name", 2);

        Map<String, Object> authorsDetail2 = new HashMap<>();
        authorsDetail1.put("name", "De la Fontaine");
        authorsDetail1.put("pet.name", "Patrache");
        authorsDetail1.put("count_name", 2);

        // Second map with nested map
        Map<String, Object> pagingDetails = new HashMap<>();
        Map<String, Object> _paging = new HashMap<>();
        _paging.put("size", 10);
        _paging.put("currentPageCount", 2);
        _paging.put("totalPages", 1);
        _paging.put("page", 1);
        _paging.put("totalCount", 2);

        pagingDetails.put("paging", _paging);

        // Add maps to the list
        List<Object> resultsList = new ArrayList<>();
        resultsList.add(authorsDetail1);
        resultsList.add(authorsDetail2);
        resultsList.add(pagingDetails);
        return resultsList;
    }

    @Test
    void parseFilterExpression_withNullFields_returnsExpectedResult() {
        Map<String, Object> filterMap = new HashMap<>();
        Map<String, Object> clauseMap = new HashMap<>();
        Map<String, JoinType> joins = new HashMap<>();
        Aggregates aggregates =  null;
        DataPaging paging = new DataPaging(1, 10, Collections.emptyList());
        List<Author> resultList = List.of(new Author("Paulo Coelo", new Pet("Jiji")), new Author("De la Fontaine", new Pet("Patrache")));

        when(typedCountQuery.getSingleResult()).thenReturn(2L);
        when(mapper.mapTuplesToEntity(anyList(), any())).thenReturn((List)resultList);

        PageData<Author> result = (PageData<Author>) typedQueryBuilder.parseFilterExpression(Operation.SEARCH, entityClass, entityClass, filterMap, clauseMap, null, joins, aggregates, paging);

        assertNotNull(result);
    }

    @Test
    void parseFilterExpression_withNullPaging_returnsExpectedResult() {
        Map<String, Object> filterMap = new HashMap<>();
        Map<String, Object> clauseMap = new HashMap<>();
        List<String> fields = List.of("Author.name");
        Map<String, JoinType> joins = new HashMap<>();
        Aggregates aggregates = null;

        List<Author> resultList = List.of(new Author("Paulo Coelo", null), new Author("De la Fontaine", null));

        when(root.get("name")).thenReturn(authorPath);
        when(typedCountQuery.getSingleResult()).thenReturn(2L);
        when(mapper.mapTuplesToEntity(anyList(), any())).thenReturn((List)resultList);

        PageData<Author> result = (PageData<Author>) typedQueryBuilder.parseFilterExpression(Operation.SEARCH, entityClass, keyClass, filterMap, clauseMap, fields, joins, aggregates, null);

        assertNotNull(result);
    }

    @Test
    void parseFilterExpression_withCountOperation_returnsExpectedResult() {
        Map<String, Object> filterMap = new HashMap<>();
        Map<String, Object> clauseMap = new HashMap<>();
        clauseMap.put("distinct", "true");
        List<String> fields = List.of("Author.id");
        Map<String, JoinType> joins = new HashMap<>();
        Aggregates aggregates = null;
        DataPaging paging = new DataPaging(1, 10, Collections.emptyList());
        when(typedCountQuery.getSingleResult()).thenReturn(2L);

        Long result = (Long)typedQueryBuilder.parseFilterExpression(Operation.COUNT, entityClass, keyClass, filterMap, clauseMap, fields, joins, aggregates, paging);

        assertEquals(2, (long) result);
    }

    @Test
    void parseFilterExpression_withNullEntityManager_throwsException() {
        when(entityManagerFactory.createEntityManager()).thenReturn(null);

        Map<String, Object> filterMap = new HashMap<>();
        Map<String, Object> clauseMap = new HashMap<>();
        List<String> fields = Arrays.asList("field1", "field2");
        Map<String, JoinType> joins = new HashMap<>();
        Aggregates aggregates = new Aggregates(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        DataPaging paging = new DataPaging(1, 10, Collections.emptyList());

        assertThrows(NullPointerException.class, () -> {
            typedQueryBuilder.parseFilterExpression(Operation.SEARCH, entityClass, keyClass, filterMap, clauseMap, fields, joins, aggregates, paging);
        });
    }

    @Test
    void parseFilterExpression_withInvalidFieldThrowsException() {
        Map<String, Object> filter = new HashMap<>();
        Map<String, Object> clauseMap = new HashMap<>();
        clauseMap.put("distinct", "true");
        List<String> fields = Arrays.asList("Author.car", "Author.pet.name");
        Map<String, JoinType> joins = new HashMap<>();
        Aggregates aggregates = new Aggregates(List.of("Author.name"), null, null, null, null, List.of("Author.pet.name"));
        DataPaging paging = new DataPaging(1, 10, List.of(new Sort("pet.name", SortDirection.DESC)));

        assertThrows(RuntimeException.class, () -> {
            typedQueryBuilder.parseFilterExpression(Operation.SEARCH, entityClass, keyClass, filter, clauseMap, fields, joins, aggregates, paging);
        });
    }

    @Test
    void testCreateTypedQuery() throws Exception {
        Method method = TypedQueryBuilder.class.getDeclaredMethod("createTypedQuery", Map.class, CriteriaBuilder.class, Class.class, Class.class, Root.class, String.class, String.class, List.class, CriteriaQuery.class);
        method.setAccessible(true);

        Map<String, Object> filterMap = new HashMap<>();
        Map<String, Object> equals = new LinkedHashMap<>();
        equals.put("containsic", "Paulo");
        filterMap.put("name", equals);
        String parentPath = "";
        String rootOperator = "and";
        List<String> fields = List.of("Author.name");
        when(root.get("name")).thenReturn(authorPath);
        when(criteriaBuilder.lower((Path)authorPath)).thenReturn(authorPath);
        when(criteriaBuilder.like(eq((Path)authorPath), anyString())).thenReturn(mockPredicate);


        Predicate result = (Predicate) method.invoke(null, filterMap, criteriaBuilder, entityClass, keyClass, root, parentPath, rootOperator, fields, criteriaQuery);
        assertNotNull(result);
    }

    @Test
    void createTypedQuery_withValidSimpleField_returnsPredicate() throws Exception {
        Map<String, Object> filterMap = Map.of("name", "Paulo");
        CriteriaBuilder builder = mock(CriteriaBuilder.class);
        Root<Author> root = mock(Root.class);
        Path<Object> path = mock(Path.class);
        Predicate predicate = mock(Predicate.class);

        when(root.get("name")).thenReturn(path);
        when(builder.equal(path, "Paulo")).thenReturn(predicate);

        Method method = TypedQueryBuilder.class.getDeclaredMethod("createTypedQuery", Map.class, CriteriaBuilder.class, Class.class, Class.class, Root.class, String.class, String.class, List.class, CriteriaQuery.class);
        method.setAccessible(true);

        Predicate result = (Predicate) method.invoke(null, filterMap, builder, Author.class, UUID.class, root, "", "and", List.of("Author.name"), mock(CriteriaQuery.class));

        assertNotNull(result);
        assertEquals(predicate, result);
    }

    @Test
    void createTypedQuery_withLogicalOperator_returnsCombinedPredicate() throws Exception {
        Map<String, Object> filterMap = Map.of("or", List.of(Map.of("name", "Paulo"), Map.of("name", "John")));
        CriteriaBuilder builder = mock(CriteriaBuilder.class);
        Root<Author> root = mock(Root.class);
        Path<Object> path = mock(Path.class);
        Predicate predicate1 = mock(Predicate.class);
        Predicate predicate2 = mock(Predicate.class);
        Predicate combinedPredicate = mock(Predicate.class);

        when(root.get("name")).thenReturn(path);
        when(builder.equal(path, "Paulo")).thenReturn(predicate1);
        when(builder.equal(path, "John")).thenReturn(predicate2);
        when(builder.or(predicate1, predicate2)).thenReturn(combinedPredicate);

        Method method = TypedQueryBuilder.class.getDeclaredMethod("createTypedQuery", Map.class, CriteriaBuilder.class, Class.class, Class.class, Root.class, String.class, String.class, List.class, CriteriaQuery.class);
        method.setAccessible(true);

        Predicate result = (Predicate) method.invoke(null, filterMap, builder, Author.class, UUID.class, root, "", "or", List.of("Author.name"), mock(CriteriaQuery.class));

        assertNotNull(result);
        assertEquals(combinedPredicate, result);
    }

    @Test
    void createTypedQuery_withComplexField_returnsNestedPredicate() throws Exception {
        Map<String, Object> filterMap = Map.of("pet", Map.of("name", "Jiji"));
        CriteriaBuilder builder = mock(CriteriaBuilder.class);
        Root<Author> root = mock(Root.class);
        Path<Object> petPath = mock(Path.class);
        Path<Object> petNamePath = mock(Path.class);
        Predicate predicate = mock(Predicate.class);

        when(root.get("pet")).thenReturn(petPath);
        when(petPath.get("name")).thenReturn(petNamePath);
        when(builder.equal(petNamePath, "Jiji")).thenReturn(predicate);

        Method method = TypedQueryBuilder.class.getDeclaredMethod("createTypedQuery", Map.class, CriteriaBuilder.class, Class.class, Class.class, Root.class, String.class, String.class, List.class, CriteriaQuery.class);
        method.setAccessible(true);

        Predicate result = (Predicate) method.invoke(null, filterMap, builder, Author.class, UUID.class, root, "", "and", List.of("Author.pet.name"), mock(CriteriaQuery.class));

        assertNotNull(result);
        assertEquals(predicate, result);
    }

    @Test
    void createTypedQuery_withEmptyFilterMap_returnsTruePredicate() throws Exception {
        Map<String, Object> filterMap = new HashMap<>();
        Predicate truePredicate = mock(Predicate.class);

        when(root.get("name")).thenReturn(authorPath);
        when(criteriaBuilder.lower((Path)authorPath)).thenReturn(authorPath);
        when(criteriaBuilder.like(eq((Path)authorPath), anyString())).thenReturn(mockPredicate);
        when(criteriaBuilder.conjunction()).thenReturn(truePredicate);

        Method method = TypedQueryBuilder.class.getDeclaredMethod("createTypedQuery", Map.class, CriteriaBuilder.class, Class.class, Class.class, Root.class, String.class, String.class, List.class, CriteriaQuery.class);
        method.setAccessible(true);

        Predicate result = (Predicate) method.invoke(null, filterMap, criteriaBuilder, entityClass, keyClass, root, "", "and", List.of("Author.name"), criteriaQuery);

        assertNotNull(result);
        assertEquals(truePredicate, result);
    }

    @Test
    void createTypedQuery_withNullFilterMap_returnsTruePredicate() throws Exception {
        CriteriaBuilder builder = mock(CriteriaBuilder.class);
        Root<Author> root = mock(Root.class);
        Predicate truePredicate = mock(Predicate.class);

        when(builder.conjunction()).thenReturn(truePredicate);

        Method method = TypedQueryBuilder.class.getDeclaredMethod("createTypedQuery", Map.class, CriteriaBuilder.class, Class.class, Class.class, Root.class, String.class, String.class, List.class, CriteriaQuery.class);
        method.setAccessible(true);

        Predicate result = (Predicate) method.invoke(null, null, builder, Author.class, UUID.class, root, "", "and", List.of("Author.name"), mock(CriteriaQuery.class));

        assertNotNull(result);
        assertEquals(truePredicate, result);
    }

    @Test
    void testCreateLogicalOperatorTypedQuery() throws Exception {
        Method method = TypedQueryBuilder.class.getDeclaredMethod("createLogicalOperatorTypedQuery", String.class, List.class, CriteriaBuilder.class, Class.class, Class.class, Root.class, String.class, List.class, CriteriaQuery.class);
        method.setAccessible(true);

        String operator = "or";
        List<Map<String, Object>> value = new ArrayList<>();
        Map<String, Object> filterMap = new HashMap<>();
        Map<String, Object> equals = new LinkedHashMap<>();
        equals.put("containsic", "Paulo");
        filterMap.put("name", equals);
        value.add(filterMap);
        String parentPath = "";
        List<String> fields = List.of("Author.name");
        when(root.get("name")).thenReturn(authorPath);
        when(criteriaBuilder.lower((Path)authorPath)).thenReturn(authorPath);
        when(criteriaBuilder.like(eq((Path)authorPath), anyString())).thenReturn(mockPredicate);

        Predicate result = (Predicate) method.invoke(null, operator, value, criteriaBuilder, entityClass, keyClass, root, parentPath, fields, criteriaQuery);
        assertNotNull(result);
    }

    @Test
    void testCreateUnaryOperatorTypedQuery() throws Exception {
        Method method = TypedQueryBuilder.class.getDeclaredMethod("createUnaryOperatorTypedQuery", Map.class, CriteriaBuilder.class, Class.class, Class.class, Root.class, String.class, List.class, CriteriaQuery.class);
        method.setAccessible(true);

        Map<String, Object> filterMap = new HashMap<>();
        Map<String, Object> equals = new LinkedHashMap<>();
        equals.put("containsic", "Paulo");
        filterMap.put("name", equals);
        String parentPath = "";
        List<String> fields = List.of("Author.name");
        when(root.get("name")).thenReturn(authorPath);
        when(criteriaBuilder.lower((Path)authorPath)).thenReturn(authorPath);
        when(criteriaBuilder.like(eq((Path)authorPath), anyString())).thenReturn(mockPredicate);

        Predicate result = (Predicate) method.invoke(null, filterMap, criteriaBuilder, entityClass, keyClass, root, parentPath, fields, criteriaQuery);
        assertNotNull(result);
    }

    @Test
    void isOverriddenField_withOverriddenField_returnsTrue() {
        @AttributeOverride(name = "fieldName", column = @Column(name = "overriddenFieldName"))
        class TestClass {
            private String fieldName;
        }

        boolean result = TypedQueryBuilder.isOverriddenField(TestClass.class, "fieldName");
        assertTrue(result);
    }

    @Test
    void isOverriddenField_withNonOverriddenField_returnsFalse() {
        class TestClass {
            private String fieldName;
        }

        boolean result = TypedQueryBuilder.isOverriddenField(TestClass.class, "fieldName");
        assertFalse(result);
    }

    @Test
    void isOverriddenField_withSuperclassOverriddenField_returnsTrue() {
        class SuperClass {
            private String fieldName;
        }

        @AttributeOverride(name = "fieldName", column = @Column(name = "overriddenFieldName"))
        class SubClass extends SuperClass {
        }

        boolean result = TypedQueryBuilder.isOverriddenField(SubClass.class, "fieldName");
        assertTrue(result);
    }

    @Test
    void isOverriddenField_withNullClass_returnsFalse() {
        boolean result = TypedQueryBuilder.isOverriddenField(null, "fieldName");
        assertFalse(result);
    }

    @Test
    void getActualFieldName_withoutAttributeOverride_returnsOriginalName() {
        String actualFieldName = TypedQueryBuilder.getActualFieldName(Author.class, "id");
        assertEquals("id", actualFieldName);
    }

    @Test
    void getActualFieldName_withSuperclassAttributeOverride_returnsOverriddenName() {
        class SuperEntity {
            private String fieldName;
        }

        @AttributeOverride(name = "fieldName", column = @Column(name = "overriddenFieldName"))
        class SubEntity extends SuperEntity {
        }

        String actualFieldName = TypedQueryBuilder.getActualFieldName(SubEntity.class, "fieldName");
        assertEquals("overriddenFieldName", actualFieldName);
    }

    @Test
    void getActualFieldName_withNullClass_returnsOriginalName() {
        String actualFieldName = TypedQueryBuilder.getActualFieldName(null, "fieldName");
        assertEquals("fieldName", actualFieldName);
    }

    @Test
    void getActualFieldType_withExistingField_returnsFieldType() throws NoSuchFieldException {
        class TestClass {
            private String testField;
        }

        Class<?> fieldType = TypedQueryBuilder.getActualFieldType(TestClass.class, "testField");
        assertEquals(String.class, fieldType);
    }

    @Test
    void getActualFieldType_withInheritedField_returnsFieldType() throws NoSuchFieldException {
        class SuperClass {
            private String superField;
        }

        class SubClass extends SuperClass {
        }

        Class<?> fieldType = TypedQueryBuilder.getActualFieldType(SubClass.class, "superField");
        assertEquals(String.class, fieldType);
    }

    @Test
    void getActualFieldType_withNonExistentField_throwsNoSuchFieldException() {
        class TestClass {
            private String testField;
        }

        assertThrows(NoSuchFieldException.class, () -> {
            TypedQueryBuilder.getActualFieldType(TestClass.class, "nonExistentField");
        });
    }

    @Test
    void getActualFieldType_withNullClass_throwsNoSuchFieldException() {
        assertThrows(NoSuchFieldException.class, () -> {
            TypedQueryBuilder.getActualFieldType(null, "testField");
        });
    }

    @Test
    void getRealTypeParameter_withParameterizedSuperclass_returnsTypeParameter() {
        class SuperClass<T> {}
        class SubClass extends SuperClass<String> {}

        Class<?> result = TypedQueryBuilder.getRealTypeParameter(SubClass.class, Object.class);
        assertEquals(String.class, result);
    }

    @Test
    void getRealTypeParameter_withParameterizedInterface_returnsTypeParameter() {
        interface Interface<T> {}
        class ImplementingClass implements Interface<Integer> {}

        Class<?> result = TypedQueryBuilder.getRealTypeParameter(ImplementingClass.class, Object.class);
        assertEquals(Integer.class, result);
    }

    @Test
    void getRealTypeParameter_withNonParameterizedClass_throwsException() {
        class NonParameterizedClass {}

        assertThrows(IllegalArgumentException.class, () -> {
            TypedQueryBuilder.getRealTypeParameter(NonParameterizedClass.class, Object.class);
        });
    }

    @Test
    void getRealTypeParameter_withNullClass_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            TypedQueryBuilder.getRealTypeParameter(null, Object.class);
        });
    }

    @Test
    void resolveTypeArgument_withClassTypeArgument_returnsClass() {
        Class<?> result = TypedQueryBuilder.resolveTypeArgument(String.class, Object.class);
        assertEquals(String.class, result);
    }

    @Test
    void resolveTypeArgument_withParameterizedTypeArgument_returnsRawType() {
        class GenericClass<T> {}
        Type parameterizedType = GenericClass.class.getGenericSuperclass();

        Class<?> result = TypedQueryBuilder.resolveTypeArgument(parameterizedType, Object.class);
        assertEquals(Object.class, result);
    }

    @Test
    void resolveTypeArgument_withUnknownTypeArgument_returnsKeyClass() {
        Type unknownType = new Type() {};
        Class<?> result = TypedQueryBuilder.resolveTypeArgument(unknownType, keyClass);
        assertEquals(UUID.class, result);
    }


    @Test
    void CleanFields_withNullFields_returnsEmptyList() throws NoSuchFieldException {
        List<String> result = TypedQueryBuilder.CleanFields(Author.class, UUID.class, null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void CleanFields_withEmptyFields_returnsEmptyList() throws NoSuchFieldException {
        List<String> result = TypedQueryBuilder.CleanFields(Author.class, UUID.class, new ArrayList<>());
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void CleanFields_withSimpleFields_returnsSameList() throws NoSuchFieldException {
        List<String> fields = Arrays.asList("Author.name", "Author.pet.name");
        List<String> result = TypedQueryBuilder.CleanFields(Author.class, UUID.class, fields);
        assertNotNull(result);
        assertEquals(fields, result);
    }

    @Test
    void CleanFields_withComplexFields_removesComplexFields() throws NoSuchFieldException {
        List<String> fields = Arrays.asList("Author.name", "Author.pet", "Author.pet.name");
        List<String> expected = Arrays.asList("Author.name", "Author.pet.name");
        List<String> result = TypedQueryBuilder.CleanFields(Author.class, UUID.class, fields);
        assertNotNull(result);
        assertEquals(expected, result);
    }

    @Test
    void CleanFields_withPagingElements_addsToPagingElementsToInclude() throws NoSuchFieldException {
        List<String> fields = Arrays.asList("Author.name", "currentPage", "pageSize");
        List<String> expected = Arrays.asList("Author.name");
        List<String> result = TypedQueryBuilder.CleanFields(Author.class, UUID.class, fields);
        assertNotNull(result);
        assertEquals(expected, result);
        assertTrue(TypedQueryBuilder.pagingElementsToInclude.contains("currentPage"));
        assertTrue(TypedQueryBuilder.pagingElementsToInclude.contains("pageSize"));
    }

    @Test
    void findPrimaryKeyField_withIdAnnotation_returnsFieldName() {
        class TestEntity {
            @Id
            private String id;
        }

        String result = TypedQueryBuilder.findPrimaryKeyField(TestEntity.class);
        assertEquals("id", result);
    }

    @Test
    void findPrimaryKeyField_withAttributeOverride_returnsOverriddenName() {
        @AttributeOverride(name = "id", column = @Column(name = "overriddenId"))
        class TestEntity {
            @Id
            private String id;
        }

        String result = TypedQueryBuilder.findPrimaryKeyField(TestEntity.class);
        assertEquals("overriddenId", result);
    }

    @Test
    void findPrimaryKeyField_withSuperclassIdAnnotation_returnsFieldName() {
        class SuperEntity {
            @Id
            private String id;
        }

        class SubEntity extends SuperEntity {}

        String result = TypedQueryBuilder.findPrimaryKeyField(SubEntity.class);
        assertEquals("id", result);
    }

    @Test
    void findPrimaryKeyField_withNoIdAnnotation_returnsNull() {
        class TestEntity {
            private String id;
        }

        String result = TypedQueryBuilder.findPrimaryKeyField(TestEntity.class);
        assertNull(result);
    }

    @Test
    void findPrimaryKeyField_withNullClass_returnsNull() {
        String result = TypedQueryBuilder.findPrimaryKeyField(null);
        assertNull(result);
    }
}