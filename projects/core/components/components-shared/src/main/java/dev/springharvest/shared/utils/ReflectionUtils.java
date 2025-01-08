package dev.springharvest.shared.utils;

import dev.springharvest.errors.constants.ExceptionMessages;
import dev.springharvest.shared.domains.DomainModel;
import jakarta.annotation.Nullable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

/**
 * This class contains utility methods for Java Reflection This class is beta for now. JavaDocs for each method will be updated once they have been more
 * rigorously tested.
 */
@Slf4j
public class ReflectionUtils {

  private ReflectionUtils() {
    throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
  }

  public static Set<String> getAllAttributePaths(Class<?> clazz, String prefix, Map<Class<?>, Integer> visited) {
    Set<String> paths = new HashSet<>();

    // Get fields from the super class and recursively call the method with the super class
    Class<?> superClass = clazz.getSuperclass();
    if (superClass != null) {
      Set<String> superPaths = getAllAttributePaths(superClass, prefix, visited);
      paths.addAll(superPaths);
    }

    // Get fields from the current class
    Field[] fields = clazz.getDeclaredFields();
    for (Field field : fields) {
      field.setAccessible(true);
      String fieldName = field.getName();
      Class<?> fieldType = field.getType();
      String path = prefix + fieldName;

      // Add path to set
      paths.add(path);

      // Recursively add nested paths
      if (!(visited.getOrDefault(fieldType, 0) == 2) && !fieldType.isPrimitive() &&
          !fieldType.equals(Boolean.class) && !fieldType.equals(Long.class) && !fieldType.equals(String.class)) {

        visited.put(fieldType, (visited.getOrDefault(fieldType, 0)) + 1);

        if (List.class.isAssignableFrom(fieldType) || Collection.class.isAssignableFrom(fieldType)) {
          Type genericType = field.getGenericType();
          if (genericType instanceof ParameterizedType parameterizedType) {
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            if (actualTypeArguments.length > 0) {
              Class<?> listType = (Class<?>) actualTypeArguments[0];
              Set<String> nestedPaths = getAllAttributePaths(listType, path + "[].", visited);
              paths.addAll(nestedPaths);
            }
          }
        } else {
          Set<String> nestedPaths = getAllAttributePaths(fieldType, path + ".", visited);
          paths.addAll(nestedPaths);
        }
      }
    }

    return paths;
  }

  public static List<Map<String, Object>> transform(List<?> objects, boolean includeNulls) {
    List<Map<String, Object>> result = new ArrayList<>();

    for (Object object : objects) {
      if (object == null) {
        continue;
      }

      Class<?> objectType = object.getClass();

      Map<String, Object> map = new LinkedHashMap<>();

      Class<?> superClass = object.getClass().getSuperclass();

      if (superClass != null) {
        Field[] superFields = superClass.getDeclaredFields();
        for (Field superField : superFields) {
          superField.setAccessible(true);
          try {
            Object value = superField.get(object);
            if (value != null && !isSimpleType(value.getClass())) {
              transformObject(value, includeNulls);
            }
            if (value == null && !includeNulls) {
              continue;
            } else {
              putToMap(map, superField.getName(), value, includeNulls);
            }
          } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
          }
        }
      }

      Field[] fields = object.getClass().getDeclaredFields();
      for (Field field : fields) {
        try {
          log.info("Field: {}", field.getName());
          field.setAccessible(true);
          Object value = field.get(object);
          if (value == null && !includeNulls) {
            //                        putToMap(map, field.getName(), value, includeNulls);
            continue;
          }
          if (value instanceof List<?> nestedObjects) {
            List<Map<String, Object>> nestedResult = transform(nestedObjects, includeNulls);
            //                        map.put(field.getName(), nestedResult);
            if (CollectionUtils.isEmpty(nestedResult) && !includeNulls) {
              continue;
            } else {
              putToMap(map, field.getName(), nestedResult, includeNulls);
            }

          } else if (value instanceof Map<?, ?> nestedMap) {
            Map<String, Object> nestedResult = new LinkedHashMap<>();
            for (Map.Entry<?, ?> entry : nestedMap.entrySet()) {
              if (entry.getValue() == null && !includeNulls) {
                continue;
              }
              nestedResult.put(entry.getKey().toString(), entry.getValue());
            }
            putToMap(map, field.getName(), nestedResult, includeNulls);
          } else if (value != null && !isSimpleType(value.getClass())) {
            Map<String, Object> nestedResult = transformObject(value, includeNulls);
            if (CollectionUtils.isEmpty(nestedResult) && !includeNulls) {
              continue;
            } else {
              boolean isDomainModel = value instanceof DomainModel;
              putToMap(map, field.getName(), nestedResult, includeNulls);
            }

          } else if (value == null && includeNulls) {
            //                        map.put(field.getName(), null);
            putToMap(map, field.getName(), value, includeNulls);
          } else if (value != null) {
            putToMap(map, field.getName(), value, includeNulls);
          }
        } catch (IllegalAccessException e) {
          // log the exception and continue processing other fields
          System.err.println("Failed to access field: " + e.getMessage());
        }
      }
      result.add(map);
    }

    return result;
  }

  private static Map<String, Object> transformObject(Object object, boolean includeNulls) {

    Map<String, Object> map = new LinkedHashMap<>();

    Class<?> superClass = object.getClass().getSuperclass();
    if (superClass != null) {
      Field[] superFields = superClass.getDeclaredFields();
      for (Field superField : superFields) {
        superField.setAccessible(true);
        try {
          Object value = superField.get(object);
          if (value == null && !includeNulls) {
            continue;
          } else {
            putToMap(map, superField.getName(), value, includeNulls);
          }
        } catch (IllegalAccessException e) {
          throw new RuntimeException(e);
        }
      }
    }

    Class<?> objectClass = object.getClass();
    Field[] fields = objectClass.getDeclaredFields();
    for (Field field : fields) {
      field.setAccessible(true);
      try {
        Object value = field.get(object);

        if (isPrimitive(value)) {
          if (value == null && includeNulls) {
            putToMap(map, field.getName(), value, includeNulls);
          }

        } else if (value instanceof List) {
          List<Map<String, Object>> nestedList = new ArrayList<>();
          for (Object nestedObject : (List<?>) value) {
            nestedList.add(transformObject(nestedObject, includeNulls));
          }
          //                    map.put(field.getName(), nestedList);
          putToMap(map, field.getName(), nestedList, includeNulls);
        } else if (value instanceof DomainModel) {
          Map<String, Object> nestedResult = transformObject(value, includeNulls);
          if ((nestedResult == null || nestedResult.isEmpty()) && !includeNulls) {
            continue;
          } else {
            putToMap(map, field.getName(), nestedResult, includeNulls);
          }
        } else {
          Map<String, Object> nestedResult = transformObject(value, includeNulls);
          if ((nestedResult == null || nestedResult.isEmpty()) && !includeNulls) {
            continue;
          } else {
            putToMap(map, field.getName(), nestedResult, includeNulls);
          }
        }
      } catch (IllegalAccessException e) {
        // handle the exception appropriately
      }
    }
    return map;
  }

  public static boolean isSimpleType(@Nullable Class<?> type) {
    if (type == null) {
      return true;
    }
    return type.isPrimitive() || type.equals(Boolean.class) || type.equals(Date.class) || type.equals(LocalDate.class) ||
           type.equals(String.class) || type.equals(Character.class) || type.equals(Long.class) ||
           type.equals(Integer.class) || type.equals(Double.class) || type.equals(Float.class) ||
           type.equals(Short.class) || type.equals(Byte.class) || Number.class.isAssignableFrom(type) ||
           UUID.class.isAssignableFrom(type);
  }

  public static boolean isPrimitive(@Nullable Object object) {
    if (object == null) {
      return true;
    }
    return object instanceof Boolean || object instanceof Character || object instanceof Byte ||
           object instanceof Short || object instanceof Integer || object instanceof Long ||
           object instanceof Float || object instanceof Double || object instanceof String ||
           object instanceof Enum;
  }

  private static void putToMap(Map<String, Object> map, String key, @Nullable Object value, boolean includeNulls) {
    log.info("Key: {}, Value: {}", key, value);
    if (value == null && !includeNulls) {
    } else if (value == null && includeNulls) {
      map.put(key, null);
    } else {
      map.put(key, value);
    }

  }

  public static boolean hasNullValues(List<Map<String, Object>> listMap) {
    for (Map<String, Object> map : listMap) {
      if (hasNullValues(map)) {
        return true;
      }
    }
    return false;
  }

  public static boolean hasNullValues(Map<String, Object> map) {

    boolean hasNulls = map == null || map.isEmpty();
    for (Map.Entry<String, Object> entry : map.entrySet()) {

      if (hasNulls) {
        return true;
      }

      Object value = entry.getValue();

      if (value == null) {
        return true;
      }

      Class<?> clazz = value.getClass();
      boolean isSimpleType = ReflectionUtils.isSimpleType(clazz);

      if (hasNulls) {
        return true;
      } else if (isSimpleType) {
        hasNulls = value == null;
      } else if (value instanceof List) {
        hasNulls = hasNullValues((List<Map<String, Object>>) value);
      } else {
        hasNulls = hasNullValues((Map<String, Object>) value);
      }
    }
    return hasNulls;
  }

}