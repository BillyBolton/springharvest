package dev.springharvest.shared.domains.embeddables.traces.trace.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.springharvest.shared.domains.DomainModel;
import dev.springharvest.shared.domains.embeddables.traces.dates.models.dtos.ITraceableDatesDTOAware;
import dev.springharvest.shared.domains.embeddables.traces.dates.models.dtos.TraceDatesDTO;
import dev.springharvest.shared.domains.embeddables.traces.users.models.dtos.AbstractTraceUsersDTO;
import dev.springharvest.shared.domains.embeddables.traces.users.models.dtos.ITraceableUsersDTO;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.ObjectUtils;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TraceDataDTO<K extends Serializable> extends DomainModel implements ITraceableDatesDTOAware, ITraceableUsersDTO<K> {

  protected TraceDatesDTO traceDates;
  protected AbstractTraceUsersDTO<K> traceUsers;

  @JsonIgnore
  @Override
  public boolean isEmpty() {
    return (ObjectUtils.isEmpty(traceDates) || traceDates.isEmpty()) && (ObjectUtils.isEmpty(traceUsers) || traceUsers.isEmpty());
  }
}
