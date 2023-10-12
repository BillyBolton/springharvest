package dev.springharvest.search.domains.base.models.entities;

import dev.springharvest.shared.domains.DomainModel;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

public interface IEntityMetadata<M extends DomainModel> {

  /**
   * This method is used to get the domain name of the entity that is being transformed so that the transformer is aware whether the path in the TupleElement
   * corresponds to a List of an entity or a single entity.
   *
   * @param isPlural Whether the domain name is plural or singular.
   * @return The domain name of the entity that is being transformed.
   */

  default String getDomainName(boolean isPlural) {
    return isPlural ? getDomainNamePlural() : getDomainName();
  }

  String getDomainNamePlural();

  String getDomainName();

  Class<M> getDomainClazz();

  Map<String, Class<?>> getRoots();

  default Set<String> getPaths() {
    return getPathClazzMap().keySet();
  }

  Map<String, Class<?>> getPathClazzMap();

  default Set<String> getRootPaths() {
    return getRootPathClazzMap().keySet();
  }

  Map<String, Class<?>> getRootPathClazzMap();

  default Set<String> getNestedPaths() {
    return getNestedPathClazzMap().keySet();
  }

  Map<String, Class<?>> getNestedPathClazzMap();

  default Class<?> getClazz(String path) {
    return getPathClazzMap().getOrDefault(path, null);
  }

  Map<String, BiConsumer<M, Object>> getRootMappingFunctions();


}
