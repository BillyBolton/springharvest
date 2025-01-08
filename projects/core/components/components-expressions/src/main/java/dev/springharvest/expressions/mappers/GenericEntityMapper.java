package dev.springharvest.expressions.mappers;

import jakarta.persistence.Tuple;
import jakarta.persistence.TupleElement;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A generic entity mapper that maps a list of tuples to an entity of type T.
 * The entity class must have a no-args constructor and the fields must be accessible.
 *
 * @param <T> the type of the entity to map to
 * @see EntityMapper
 *
 * @author Moctar
 * @author Gilles Djawa (NeroNemesis)
 */
public class GenericEntityMapper<T> implements EntityMapper<T> {

    @Override
    public List<T> mapTuplesToEntity(List<Tuple> rawList, Class<T> entityClass) {
        List<T> entities = new ArrayList<>();

        try {
            if (!rawList.isEmpty()) {
                for (Tuple tuple : rawList) {
                    // Create a new instance of the entity for each tuple
                    T entity = entityClass.getDeclaredConstructor().newInstance();

                    for (TupleElement<?> element : tuple.getElements()) {
                        String elementName = element.getAlias(); // e.g., "author.pet.name"
                        if (elementName.contains(".")) {
                            // Handle nested fields
                            String[] nestedFields = elementName.split("\\.");
                            Object parentObject = entity;

                            for (int i = 0; i < nestedFields.length; i++) {
                                String fieldName = nestedFields[i];
                                Field field = getFieldInHierarchy(parentObject.getClass(), fieldName);
                                if (field == null) {
                                    throw new NoSuchFieldException("Field not found: " + fieldName);
                                }
                                field.setAccessible(true);

                                if (i == nestedFields.length - 1) {
                                    // Last field: set the value
                                    field.set(parentObject, tuple.get(elementName, element.getJavaType()));
                                } else {
                                    // Intermediate field: get or initialize nested object
                                    Object childObject = field.get(parentObject);
                                    if (childObject == null) {
                                        childObject = field.getType().getDeclaredConstructor().newInstance();
                                        field.set(parentObject, childObject);
                                    }
                                    parentObject = childObject;
                                }
                            }
                        } else {
                            // Handle direct fields
                            Field field = getFieldInHierarchy(entityClass, elementName);
                            if (field == null) {
                                throw new NoSuchFieldException("Field not found: " + elementName);
                            }
                            field.setAccessible(true);
                            field.set(entity, tuple.get(elementName, element.getJavaType()));
                        }
                    }

                    // Add the mapped entity to the list
                    entities.add(entity);
                }
            }
            else {
                return new ArrayList<>();
            }
        } catch (InstantiationException | IllegalAccessException | NoSuchFieldException |
                 InvocationTargetException | NoSuchMethodException e) {
            System.err.println("Error while mapping tuples to entities: " + e.getMessage());
        }

        return entities;
    }

    @Override
    public List<Map<String, Object>> mapTuplesToMap(List<Tuple> rawList) {
        List<Map<String, Object>> result = new ArrayList<>();

        if (rawList.isEmpty()) {
            return result;
        }
        for (Tuple tuple : rawList) {
            Map<String, Object> map = new HashMap<>();

            // Iterate over the tuple and dynamically add entries to the map
            for (int i = 0; i < tuple.getElements().size(); i++) {
                String alias = tuple.getElements().get(i).getAlias();  // Get the alias of the field
                Object value = tuple.get(i);  // Get the value of the field by index

                map.put(alias, value);  // Map the alias to the field value
            }

            result.add(map);
        }

        return result;
    }

    private Field getFieldInHierarchy(Class<?> clazz, String fieldName) {
        while (clazz != null) {
            try {
                return clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        return null; // Field not found in hierarchy
    }
}
