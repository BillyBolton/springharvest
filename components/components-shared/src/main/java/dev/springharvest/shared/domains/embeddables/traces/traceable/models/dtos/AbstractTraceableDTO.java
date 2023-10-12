package dev.springharvest.shared.domains.embeddables.traces.traceable.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import dev.springharvest.shared.domains.base.models.dtos.BaseDTO;
import dev.springharvest.shared.domains.embeddables.traces.trace.models.entities.TraceDataEntity;
import dev.springharvest.shared.domains.embeddables.traces.traceable.models.entities.ITraceableEntity;
import jakarta.annotation.Nullable;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class AbstractTraceableDTO<K extends Serializable> extends BaseDTO<K> implements ITraceableEntity<K> {

  @Nullable
  @Embedded
  protected TraceDataEntity<K> traceData;

  @JsonIgnore
  @Override
  public boolean isEmpty() {
    return super.isEmpty() && (traceData == null || traceData.isEmpty());
  }

}