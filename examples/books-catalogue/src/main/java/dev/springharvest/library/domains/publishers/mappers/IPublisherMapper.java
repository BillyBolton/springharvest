package dev.springharvest.library.domains.publishers.mappers;

import dev.springharvest.library.domains.publishers.models.dtos.PublisherDTO;
import dev.springharvest.library.domains.publishers.models.entities.PublisherEntity;
import dev.springharvest.shared.domains.base.mappers.CyclicMappingHandler;
import dev.springharvest.shared.domains.base.mappers.IBaseModelMapper;
import dev.springharvest.shared.domains.embeddables.traces.trace.mappers.UUIDTraceDataMapper;
import java.util.Map;
import java.util.UUID;
import org.mapstruct.Builder;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true), uses = {UUIDTraceDataMapper.class})
public interface IPublisherMapper extends IBaseModelMapper<PublisherDTO, PublisherEntity, UUID> {

  @Override
  @Mapping(target = "id", nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "name", nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
  PublisherDTO setDirtyFields(PublisherDTO source, @MappingTarget PublisherDTO target,
                              @Context CyclicMappingHandler context);

  @Override
  @Mapping(target = "traceData", source = ".")
  PublisherDTO toDto(Map<String, String> source, @Context CyclicMappingHandler context);

  @Override
  @Mapping(target = "traceData", source = ".")
  PublisherEntity toEntity(Map<String, String> source, @Context CyclicMappingHandler context);

}