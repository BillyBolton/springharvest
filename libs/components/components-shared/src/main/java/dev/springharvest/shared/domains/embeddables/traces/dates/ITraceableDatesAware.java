package dev.springharvest.shared.domains.embeddables.traces.dates;

import java.util.Date;

public interface ITraceableDatesAware {

  Date getDateCreated();

  void setDateCreated(Date date);

  Date getDateUpdated();

  void setDateUpdated(Date date);

}
