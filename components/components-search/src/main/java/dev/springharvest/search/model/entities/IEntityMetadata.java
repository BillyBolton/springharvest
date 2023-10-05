package dev.springharvest.search.model.entities;

import dev.springhavest.common.models.domains.DomainModel;
import java.util.Map;
import java.util.Set;

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


}
