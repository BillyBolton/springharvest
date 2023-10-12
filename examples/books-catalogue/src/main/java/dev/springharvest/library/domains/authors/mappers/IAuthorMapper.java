package dev.springharvest.library.domains.authors.mappers;


import dev.springharvest.library.domains.authors.models.dtos.AuthorDTOAbstract;
import dev.springharvest.library.domains.authors.models.entities.AuthorEntity;
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
public interface IAuthorMapper extends IBaseModelMapper<AuthorDTOAbstract, AuthorEntity, UUID> {

  @Override
  @Mapping(target = "id", nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "name", nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
  AuthorDTOAbstract setDirtyFields(AuthorDTOAbstract source, @MappingTarget AuthorDTOAbstract target, @Context CyclicMappingHandler context);

  @Override
  @Mapping(target = "traceData", source = ".")
  AuthorDTOAbstract toDto(Map<String, String> source, @Context CyclicMappingHandler context);

  @Override
  @Mapping(target = "traceData", source = ".")
  AuthorEntity toEntity(Map<String, String> source, @Context CyclicMappingHandler context);

}
