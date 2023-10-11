package dev.springhavest.common.models.entities.embeddable;

import java.io.Serializable;

public interface ITraceableEntity<K extends Serializable> {

  TraceDataEntity<K> getTraceData();

  void setTraceData(TraceDataEntity<K> traceData);

}
