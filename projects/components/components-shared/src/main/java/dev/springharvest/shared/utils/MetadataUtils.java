package dev.springharvest.shared.utils;

import dev.springharvest.errors.constants.ExceptionMessages;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MetadataUtils {

  private MetadataUtils() {
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

  public static String scrubPrefix(String path, String domainSingular) {
    String[] pathArray = path.split("\\.");
    if (!pathArray[0].equals(domainSingular)) {
      String[] newPathArray = new String[pathArray.length - 1];
      System.arraycopy(pathArray, 1, newPathArray, 0, pathArray.length - 1);
      path = String.join(".", newPathArray);
    }
    return path;
  }

}
