package dev.springhavest.common.models.domains.embeddable;

import java.io.Serializable;
import java.util.Date;

public interface ITraceable<K extends Serializable> {

  Date getDateCreated();

  void setDateCreated(Date date);

  K getUpdatedBy();

  void setUpdatedBy(K updatedBy);

  Date getDateUpdated();

  void setDateUpdated(Date date);

  K getCreatedBy();

  void setCreatedBy(K createdBy);

}
