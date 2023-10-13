package dev.springharvest.shared.domains.embeddables.traces.traceable.models.entities;

import dev.springharvest.shared.domains.embeddables.traces.trace.models.entities.TraceDataEntity;
import java.io.Serializable;

public interface ITraceableEntity<K extends Serializable> {

  TraceDataEntity<K> getTraceData();

  void setTraceData(TraceDataEntity<K> traceData);

}
