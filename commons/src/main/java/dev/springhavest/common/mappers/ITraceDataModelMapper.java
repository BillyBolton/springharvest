package dev.springhavest.common.mappers;

import dev.springhavest.common.models.dtos.embeddable.TraceDataDTO;
import dev.springhavest.common.models.entities.embeddable.TraceDataEntity;
import java.io.Serializable;
import java.util.Map;
import org.mapstruct.Context;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

public interface ITraceDataModelMapper<K extends Serializable> {

  TraceDataDTO<K> entityToDto(TraceDataEntity<K> entity);

  TraceDataEntity<K> dtoToEntity(TraceDataDTO<K> dto);

  @Mapping(target = "createdBy",
           nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "dateCreated", nullValuePropertyMappingStrategy =
      org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "updatedBy",
           nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "dateUpdated",
           nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
  TraceDataDTO<K> setDirtyFields(TraceDataDTO source,
                                 @MappingTarget TraceDataDTO target, @Context CyclicMappingHandler context);


  TraceDataDTO<K> mapToDTO(Map<String, String> source, @Context CyclicMappingHandler context);

  TraceDataEntity<K> mapToEntity(Map<String, String> source, @Context CyclicMappingHandler context);

}
