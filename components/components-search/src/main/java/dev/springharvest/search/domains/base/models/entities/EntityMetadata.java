package dev.springharvest.search.domains.base.models.entities;

import dev.springharvest.shared.domains.DomainModel;
import dev.springharvest.shared.utils.MetadataUtils;
import java.util.Map;
import java.util.function.BiConsumer;
import lombok.Getter;

@Getter
public abstract class EntityMetadata<M extends DomainModel> implements IEntityMetadata<M> {

  protected final Class<M> domainClazz;
  protected final String domainName;
  protected final String domainNamePlural;
  protected final Map<String, Class<?>> roots;
  protected final Map<String, Class<?>> rootPathClazzMap;
  protected final Map<String, BiConsumer<M, Object>> rootMappingFunctions;
  protected final Map<String, Class<?>> nestedPathClazzMap;
  protected final Map<String, Class<?>> pathClazzMap;

  @SafeVarargs
  protected EntityMetadata(Class<M> domainClass,
                           String domainName,
                           String domainNamePlural,
                           Map<String, Class<?>> roots,
                           Map<String, Class<?>> rootPathClazzMap,
                           Map<String, BiConsumer<M, Object>> rootMappingFunctions,
                           Map<String, Class<?>>... nestedPathClazzMap) {
    this.domainClazz = domainClass;
    this.domainName = domainName;
    this.domainNamePlural = domainNamePlural;
    this.roots = roots;
    this.rootPathClazzMap = rootPathClazzMap;
    this.rootMappingFunctions = rootMappingFunctions;
    this.nestedPathClazzMap = MetadataUtils.appendDomainPrefix(domainName, nestedPathClazzMap);
    this.pathClazzMap = MetadataUtils.union(this.rootPathClazzMap, this.nestedPathClazzMap);
  }

}
