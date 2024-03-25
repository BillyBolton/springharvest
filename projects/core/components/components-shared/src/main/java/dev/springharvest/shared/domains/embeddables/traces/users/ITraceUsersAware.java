package dev.springharvest.shared.domains.embeddables.traces.users;

import java.io.Serializable;

public interface ITraceUsersAware<K extends Serializable> {

  K getUpdatedBy();

  void setUpdatedBy(K updatedBy);

  K getCreatedBy();

  void setCreatedBy(K createdBy);

}
