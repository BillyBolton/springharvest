package dev.springhavest.common.models.dtos;

import dev.springhavest.common.models.entities.BaseEntity;
import dev.springhavest.common.models.entities.embeddable.ITraceableEntity;
import dev.springhavest.common.models.entities.embeddable.TraceDataEntity;
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
public abstract class TraceableDTO<K extends Serializable> extends BaseEntity<K> implements ITraceableEntity<K> {

  @Embedded
  protected TraceDataEntity<K> traceData;

  @Override
  public boolean isEmpty() {
    return super.isEmpty() && (traceData == null || traceData.isEmpty());
  }

}