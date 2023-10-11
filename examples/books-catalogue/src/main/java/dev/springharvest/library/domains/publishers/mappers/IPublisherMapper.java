package dev.springharvest.library.domains.publishers.mappers;

import dev.springharvest.library.domains.publishers.models.dtos.PublisherDTO;
import dev.springharvest.library.domains.publishers.models.entities.PublisherEntity;
import dev.springhavest.common.mappers.CyclicMappingHandler;
import dev.springhavest.common.mappers.ITraceableModelMapper;
import java.util.UUID;
import org.mapstruct.Builder;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface IPublisherMapper extends ITraceableModelMapper<PublisherDTO, PublisherEntity, UUID> {

  @Override
  @Mapping(target = "id", nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "name", nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
  PublisherDTO setDirtyFields(PublisherDTO source, @MappingTarget PublisherDTO target,
                              @Context CyclicMappingHandler context);

}