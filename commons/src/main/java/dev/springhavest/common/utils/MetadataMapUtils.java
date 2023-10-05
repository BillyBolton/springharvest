package dev.springhavest.common.utils;

import dev.springharvest.errors.constants.ExceptionMessages;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MetadataMapUtils {

  private MetadataMapUtils() {
    throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
  }

  @SafeVarargs
  public static Map<String, Class<?>> union(Map<String, Class<?>>... maps) {
    Map<String, Class<?>> allPaths = new HashMap<>();
    for (Map<String, Class<?>> map : maps) {
      allPaths.putAll(map);
    }
    return allPaths;
  }

  @SafeVarargs
  public static Map<String, Class<?>> appendDomainPrefix(String singularDomain, Map<String, Class<?>>... foreignPaths) {
    String prefix = singularDomain + ".";
    Map<String, Class<?>> preparedForeignPaths = new HashMap<>();
    Arrays.stream(foreignPaths).forEach(pathMap -> pathMap.forEach((key, value) -> preparedForeignPaths.put(prefix + key, value)));
    return preparedForeignPaths;
  }

}
