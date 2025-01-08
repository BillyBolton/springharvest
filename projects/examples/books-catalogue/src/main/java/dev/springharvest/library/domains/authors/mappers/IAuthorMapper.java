package dev.springharvest.library.domains.authors.mappers;


import dev.springharvest.library.domains.authors.models.dtos.AuthorDTO;
import dev.springharvest.library.domains.authors.models.entities.AuthorEntity;
import dev.springharvest.library.domains.pet.mappers.IPetMapper;
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

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true), uses = {IPetMapper.class, UUIDTraceDataMapper.class})
public interface IAuthorMapper extends IBaseModelMapper<AuthorDTO, AuthorEntity, UUID> {

  @Override
  @Mapping(target = "id", nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "name", nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "traceData", nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
  AuthorDTO setDirtyFields(AuthorDTO source, @MappingTarget AuthorDTO target, @Context CyclicMappingHandler context);

  @Override
  @Mapping(target = "traceData", source = ".")
  @Mapping(target = "pet", source = ".")
  AuthorDTO toDto(Map<String, String> source, @Context CyclicMappingHandler context);

  @Override
  @Mapping(target = "traceData", source = ".")
  @Mapping(target = "pet", source = ".")
  AuthorEntity toEntity(Map<String, String> source, @Context CyclicMappingHandler context);

}
