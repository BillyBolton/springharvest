package dev.springharvest.shared.domains.embeddables.traces.traceable.mappers;

import dev.springharvest.shared.domains.base.mappers.CyclicMappingHandler;
import dev.springharvest.shared.domains.embeddables.traces.trace.mappers.ITraceDataModelMapper;
import dev.springharvest.shared.domains.embeddables.traces.trace.models.dtos.TraceDataDTO;
import dev.springharvest.shared.domains.embeddables.traces.trace.models.entities.TraceDataEntity;
import java.util.Map;
import java.util.UUID;
import org.mapstruct.Builder;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface IUUIDTraceableModelMapper extends ITraceDataModelMapper<UUID> {

  @Override
  @Mapping(target = "traceDates", source = ".")
  @Mapping(target = "traceUsers", source = ".")
  TraceDataDTO<UUID> mapToDTO(Map<String, String> source, @Context CyclicMappingHandler context);

  @Override
  @Mapping(target = "traceDates", source = ".")
  @Mapping(target = "traceUsers", source = ".")
  TraceDataEntity<UUID> mapToEntity(Map<String, String> source, @Context CyclicMappingHandler context);

}
