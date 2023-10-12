package dev.springharvest.shared.domains.embeddables.traces.dates.mappers;

import dev.springharvest.shared.domains.base.mappers.CyclicMappingHandler;
import dev.springharvest.shared.domains.embeddables.traces.dates.models.entities.TraceDatesEntity;
import dev.springharvest.shared.domains.embeddables.traces.dates.models.dtos.TraceDatesDTO;
import java.util.Map;
import org.mapstruct.Builder;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface ITraceDatesModelMapper {

  TraceDatesDTO entityToDto(TraceDatesEntity entity);

  TraceDatesEntity dtoToEntity(TraceDatesDTO dto);

  @Mapping(target = "dateCreated", nullValuePropertyMappingStrategy =
      org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "dateUpdated",
           nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
  TraceDatesDTO setDirtyFields(TraceDatesDTO source,
                               @MappingTarget TraceDatesDTO target, @Context CyclicMappingHandler context);


  TraceDatesDTO mapToDTO(Map<String, String> source, @Context CyclicMappingHandler context);

  TraceDatesEntity mapToEntity(Map<String, String> source, @Context CyclicMappingHandler context);

}

