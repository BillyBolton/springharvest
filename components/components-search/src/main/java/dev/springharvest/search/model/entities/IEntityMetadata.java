package dev.springharvest.search.model.entities;

import dev.springhavest.common.models.domains.DomainModel;
import java.util.Set;
import org.apache.commons.collections4.SetUtils;

public interface IEntityMetadata<M extends DomainModel> {

  String getDomainName();

  /**
   * This method is used to get the domain name of the entity that is being transformed so that the transformer is aware whether the path in the TupleElement
   * corresponds to a List of an entity or a single entity.
   *
   * @param isPlural Whether the domain name is plural or singular.
   * @return The domain name of the entity that is being transformed.
   */
  String getDomainName(boolean isPlural);

  Class<?> getClazz(String path);

  default Set<String> getAllPaths() {
    return SetUtils.union(getRootPaths(), getNestedPaths());
  }

  Set<String> getRootPaths();

  Set<String> getNestedPaths();

}
