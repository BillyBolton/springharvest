package dev.springharvest.shared.domains.base.mappers;

import java.util.HashMap;
import java.util.Map;
import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.TargetType;

/**
 * Ensures that Mapstruct will not create a StackOverflowError when mapping cyclic dependencies.
 *
 * @link https://javadoc.io/static/org.mapstruct/mapstruct-jdk8/1.2.0.Final/org/mapstruct/Context.html
 */
public class CyclicMappingHandler {

  private final Map<Object, Object> knownInstances = new HashMap<>();

  /**
   * Gets mapped instance.
   *
   * @param <T>        the type parameter
   * @param source     the source
   * @param targetType the target type
   * @return the mapped instance
   */
  @BeforeMapping
  public <T> T getMappedInstance(Object source, @TargetType Class<T> targetType) {
    return targetType.cast(knownInstances.get(source));
  }

  /**
   * Put if absent.
   *
   * @param <T>    the type parameter
   * @param source the source
   * @param target the target
   */
  @BeforeMapping
  public <T> void putIfAbsent(Object source, @MappingTarget T target) {
    knownInstances.putIfAbsent(source, target);
  }

}