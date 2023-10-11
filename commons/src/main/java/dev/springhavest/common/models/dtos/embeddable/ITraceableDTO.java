package dev.springhavest.common.models.dtos.embeddable;

import java.io.Serializable;

public interface ITraceableDTO<K extends Serializable> {

  TraceDataDTO<K> getTraceData();

  void setTraceData(TraceDataDTO<K> traceData);

}
