package dev.springharvest.library.domains.pet.mappers;


import dev.springharvest.library.domains.authors.mappers.IAuthorMapper;
import dev.springharvest.library.domains.pet.models.dtos.PetDTO;
import dev.springharvest.library.domains.pet.models.entities.PetEntity;
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


@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true),
        uses = {UUIDTraceDataMapper.class})
public interface IPetMapper extends IBaseModelMapper<PetDTO, PetEntity, UUID> {

    @Override
    @Mapping(target = "id", nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "name", nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "traceData", nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    PetDTO setDirtyFields(PetDTO source, @MappingTarget PetDTO target, @Context CyclicMappingHandler context);

    @Override
    @Mapping(target = "traceData", source = ".")
    PetDTO toDto(Map<String, String> source, @Context CyclicMappingHandler context);

    @Override
    @Mapping(target = "traceData", source = ".")
    PetEntity toEntity(Map<String, String> source, @Context CyclicMappingHandler context);

}
