package dev.springhavest.common.models.dtos.embeddable;

import dev.springhavest.common.models.domains.DomainModel;
import dev.springhavest.common.models.domains.embeddable.ITraceable;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Embeddable
public class TraceDataDTO<K extends Serializable> extends DomainModel implements ITraceable<K> {

  protected K createdBy;
  protected Date dateCreated;
  protected K updatedBy;
  protected Date dateUpdated;

  @Override
  public boolean isEmpty() {
    return createdBy == null && dateCreated == null && updatedBy == null && dateUpdated == null;
  }
}
