package dev.springharvest.shared.domains.embeddables.traces.traceable.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.springharvest.shared.domains.base.models.entities.BaseEntity;
import dev.springharvest.shared.domains.embeddables.traces.trace.models.entities.TraceDataEntity;
import jakarta.persistence.Embedded;
import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AbstractTraceableEntity<K extends Serializable> extends BaseEntity<K> implements ITraceableEntity<K> {

  @Embedded
  protected TraceDataEntity<K> traceData;

  @JsonIgnore
  @Override
  public boolean isEmpty() {
    return super.isEmpty() && (traceData == null || traceData.isEmpty());
  }

}