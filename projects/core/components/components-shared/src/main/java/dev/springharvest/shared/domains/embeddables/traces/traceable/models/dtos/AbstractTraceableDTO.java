package dev.springharvest.shared.domains.embeddables.traces.traceable.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import dev.springharvest.shared.domains.base.models.dtos.BaseDTO;
import dev.springharvest.shared.domains.embeddables.traces.dates.models.dtos.TraceDatesDTO;
import dev.springharvest.shared.domains.embeddables.traces.trace.models.dtos.TraceDataDTO;
import dev.springharvest.shared.domains.embeddables.traces.users.models.dtos.AbstractTraceUsersDTO;
import jakarta.annotation.Nullable;
import jakarta.persistence.Embedded;
import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
public class AbstractTraceableDTO<K extends Serializable> extends BaseDTO<K> implements ITraceableDTO<K> {

  @Nullable
  @Embedded
  @Builder.Default
  protected TraceDataDTO<K> traceData = (TraceDataDTO<K>) TraceDataDTO.builder()
      .traceDates(TraceDatesDTO.builder().build())
      .traceUsers(AbstractTraceUsersDTO.builder().build())
      .build();

  @JsonIgnore
  @Override
  public boolean isEmpty() {
    return super.isEmpty() && (traceData == null || traceData.isEmpty());
  }

}