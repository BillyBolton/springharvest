package dev.springharvest.shared.domains.embeddables.traces.trace.mappers;

import dev.springharvest.shared.domains.base.mappers.CyclicMappingHandler;
import dev.springharvest.shared.domains.embeddables.traces.trace.models.dtos.TraceDataDTO;
import dev.springharvest.shared.domains.embeddables.traces.trace.models.entities.TraceDataEntity;
import java.io.Serializable;
import java.util.Map;
import org.mapstruct.Context;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

public interface ITraceDataModelMapper<K extends Serializable> {

  TraceDataDTO<K> entityToDto(TraceDataEntity<K> entity);

  TraceDataEntity<K> dtoToEntity(TraceDataDTO<K> dto);

  @Mapping(target = "traceDates",
           nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "traceUsers", nullValuePropertyMappingStrategy =
      org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
  TraceDataDTO<K> setDirtyFields(TraceDataDTO source,
                                 @MappingTarget TraceDataDTO target, @Context CyclicMappingHandler context);


  TraceDataDTO<K> mapToDTO(Map<String, String> source, @Context CyclicMappingHandler context);

  TraceDataEntity<K> mapToEntity(Map<String, String> source, @Context CyclicMappingHandler context);

}
