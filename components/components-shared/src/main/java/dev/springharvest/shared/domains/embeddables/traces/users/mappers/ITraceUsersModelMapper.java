package dev.springharvest.shared.domains.embeddables.traces.users.mappers;

import dev.springharvest.shared.domains.base.mappers.CyclicMappingHandler;
import dev.springharvest.shared.domains.embeddables.traces.users.models.dtos.AbstractTraceUsersDTO;
import dev.springharvest.shared.domains.embeddables.traces.users.models.entities.TraceUsersEntity;
import java.io.Serializable;
import java.util.Map;
import org.mapstruct.Context;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

public interface ITraceUsersModelMapper<K extends Serializable> {

  AbstractTraceUsersDTO<K> entityToDto(TraceUsersEntity<K> entity);

  TraceUsersEntity<K> dtoToEntity(AbstractTraceUsersDTO<K> dto);

  @Mapping(target = "createdBy", nullValuePropertyMappingStrategy =
      org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "updatedBy",
           nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
  AbstractTraceUsersDTO<K> setDirtyFields(AbstractTraceUsersDTO<K> source,
                                          @MappingTarget AbstractTraceUsersDTO<K> target, @Context CyclicMappingHandler context);


  AbstractTraceUsersDTO<K> mapToDTO(Map<String, String> source, @Context CyclicMappingHandler context);

  TraceUsersEntity<K> mapToEntity(Map<String, String> source, @Context CyclicMappingHandler context);

}

