package dev.springharvest.shared.domains.embeddables.traces.users.models.entities;

import java.io.Serializable;

public interface ITraceableUsersEntityAware<K extends Serializable> {

  TraceUsersEntity<K> getTraceUsers();

  void setTraceUsers(TraceUsersEntity<K> traceUsers);

}
