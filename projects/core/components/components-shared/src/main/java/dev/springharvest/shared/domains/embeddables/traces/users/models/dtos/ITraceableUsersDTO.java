package dev.springharvest.shared.domains.embeddables.traces.users.models.dtos;

import java.io.Serializable;

public interface ITraceableUsersDTO<K extends Serializable> {

  AbstractTraceUsersDTO<K> getTraceUsers();

  void setTraceUsers(AbstractTraceUsersDTO<K> traceUsers);

}
