package dev.springharvest.library.domains.publishers.mappers.search;


import dev.springharvest.library.domains.publishers.models.entities.PublisherEntity;
import dev.springharvest.library.domains.publishers.models.entities.PublisherEntityMetadata;
import dev.springharvest.library.domains.publishers.models.queries.PublisherFilterBO;
import dev.springharvest.library.domains.publishers.models.queries.PublisherFilterDTO;
import dev.springharvest.library.domains.publishers.models.queries.PublisherFilterRequestBO;
import dev.springharvest.library.domains.publishers.models.queries.PublisherFilterRequestDTO;
import dev.springharvest.library.global.GlobalClazzResolver;
import dev.springharvest.search.domains.base.mappers.queries.ISearchMapper;
import java.util.UUID;
import lombok.Getter;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public abstract class PublisherSearchMapper
    implements ISearchMapper<PublisherEntity, UUID, PublisherFilterRequestDTO, PublisherFilterRequestBO, PublisherFilterDTO, PublisherFilterBO> {

  @Autowired
  private GlobalClazzResolver globalClazzResolver;

  @Autowired
  private PublisherEntityMetadata entityMetadata;

}

