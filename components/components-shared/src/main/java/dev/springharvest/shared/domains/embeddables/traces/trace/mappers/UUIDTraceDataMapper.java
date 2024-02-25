package dev.springharvest.shared.domains.embeddables.traces.trace.mappers;

import dev.springharvest.shared.domains.base.mappers.CyclicMappingHandler;
import dev.springharvest.shared.domains.embeddables.traces.dates.mappers.ITraceDatesModelMapper;
import dev.springharvest.shared.domains.embeddables.traces.trace.models.dtos.TraceDataDTO;
import dev.springharvest.shared.domains.embeddables.traces.trace.models.entities.TraceDataEntity;
import dev.springharvest.shared.domains.embeddables.traces.users.mappers.IUUIDTraceUsersModelMapper;
import java.util.Map;
import java.util.UUID;
import org.mapstruct.Builder;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true), uses = {ITraceDatesModelMapper.class, IUUIDTraceUsersModelMapper.class})
public interface UUIDTraceDataMapper extends ITraceDataModelMapper<UUID> {

  @Override
  @Mapping(target = "traceDates", source = ".")
  @Mapping(target = "traceUsers", source = ".")
  TraceDataDTO<UUID> mapToDTO(Map<String, String> source, @Context CyclicMappingHandler context);

  @Override
  @Mapping(target = "traceDates", source = ".")
  @Mapping(target = "traceUsers", source = ".")
  TraceDataEntity<UUID> mapToEntity(Map<String, String> source, @Context CyclicMappingHandler context);

}
