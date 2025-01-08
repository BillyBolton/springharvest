package dev.springharvest.shared.domains.embeddables.traces.dates;

import java.time.LocalDate;

public interface ITraceableDatesAware {

  LocalDate getDateCreated();

  void setDateCreated(LocalDate date);

  LocalDate getDateUpdated();

  void setDateUpdated(LocalDate date);

}
