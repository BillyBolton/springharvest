package dev.springharvest.shared.domains.embeddables.traces.traceable.models.dtos;

import dev.springharvest.shared.domains.embeddables.traces.trace.models.dtos.TraceDataDTO;
import java.io.Serializable;

public interface ITraceableDTO<K extends Serializable> {

  TraceDataDTO<K> getTraceData();

  void setTraceData(TraceDataDTO<K> traceData);

}
