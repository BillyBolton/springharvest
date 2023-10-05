package dev.springharvest.search.model.entities;

import dev.springhavest.common.models.domains.DomainModel;
import dev.springhavest.common.utils.MetadataMapUtils;
import java.util.Map;
import lombok.Getter;

@Getter
public abstract class EntityMetadata<M extends DomainModel> implements IEntityMetadata<M> {

  protected final String domainName;
  protected final String domainNamePlural;
  protected final Map<String, Class<?>> roots;
  protected final Map<String, Class<?>> rootPathClazzMap;
  protected final Map<String, Class<?>> nestedPathClazzMap;
  protected final Map<String, Class<?>> pathClazzMap;

  @SafeVarargs
  protected EntityMetadata(String domainName,
                           String domainNamePlural,
                           Map<String, Class<?>> roots,
                           Map<String, Class<?>> rootPathClazzMap,
                           Map<String, Class<?>>... nestedPathClazzMap) {
    this.domainName = domainName;
    this.domainNamePlural = domainNamePlural;
    this.roots = roots;
    this.rootPathClazzMap = rootPathClazzMap;
    this.nestedPathClazzMap = MetadataMapUtils.appendDomainPrefix(domainName, nestedPathClazzMap);
    this.pathClazzMap = MetadataMapUtils.union(this.rootPathClazzMap, this.nestedPathClazzMap);
  }

}
