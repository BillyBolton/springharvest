package dev.springharvest.shared.domains.embeddables.traces.trace.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.springharvest.shared.domains.DomainModel;
import dev.springharvest.shared.domains.embeddables.traces.dates.models.entities.ITraceableDatesEntityAware;
import dev.springharvest.shared.domains.embeddables.traces.dates.models.entities.TraceDatesEntity;
import dev.springharvest.shared.domains.embeddables.traces.users.models.entities.ITraceableUsersEntityAware;
import dev.springharvest.shared.domains.embeddables.traces.users.models.entities.TraceUsersEntity;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
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
@Embeddable
public class TraceDataEntity<K extends Serializable> extends DomainModel implements ITraceableDatesEntityAware, ITraceableUsersEntityAware<K> {

  @Embedded
  protected TraceDatesEntity traceDates;

  @Embedded
  protected TraceUsersEntity<K> traceUsers;

  @JsonIgnore
  @Override
  public boolean isEmpty() {
    return (ObjectUtils.isEmpty(traceDates) || traceDates.isEmpty()) && (ObjectUtils.isEmpty(traceUsers) || traceUsers.isEmpty());
  }
}
