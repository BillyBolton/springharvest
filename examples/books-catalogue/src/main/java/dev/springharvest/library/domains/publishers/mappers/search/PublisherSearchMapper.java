package dev.springharvest.library.domains.publishers.mappers.search;


import dev.springharvest.library.beans.GlobalClazzResolver;
import dev.springharvest.library.domains.publishers.models.entities.PublisherEntityMetadata;
import dev.springharvest.library.domains.publishers.models.queries.PublisherFilterBO;
import dev.springharvest.library.domains.publishers.models.queries.PublisherFilterDTO;
import dev.springharvest.library.domains.publishers.models.queries.PublisherFilterRequestBO;
import dev.springharvest.library.domains.publishers.models.queries.PublisherFilterRequestDTO;
import dev.springharvest.search.mappers.queries.ISearchMapper;
import java.util.Set;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public abstract class PublisherSearchMapper
    implements ISearchMapper<PublisherFilterRequestDTO, PublisherFilterRequestBO, PublisherFilterDTO, PublisherFilterBO> {

  @Autowired
  private GlobalClazzResolver globalClazzResolver;

  @Autowired
  private PublisherEntityMetadata entityMetadata;


  public Class<?> getClazz(String path) {
    return globalClazzResolver.getClazz(path);
  }

  public Set<String> getRoots() {
    return entityMetadata.getRootPaths();
  }

}

