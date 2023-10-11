package dev.springhavest.common.models.entities.embeddable;

import dev.springhavest.common.models.domains.DomainModel;
import dev.springhavest.common.models.domains.embeddable.ITraceable;
import jakarta.persistence.Column;
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
public class TraceDataEntity<K extends Serializable> extends DomainModel implements ITraceable<K> {

  @Column(name = "created_by", updatable = false, nullable = false)
  protected K createdBy;

  @Column(name = "date_created", updatable = false, nullable = false)
  protected Date dateCreated;

  @Column(name = "updated_by", updatable = true, nullable = false)
  protected K updatedBy;

  @Column(name = "date_updated", updatable = true, nullable = false)
  protected Date dateUpdated;

  @Override
  public boolean isEmpty() {
    return createdBy == null && dateCreated == null && updatedBy == null && dateUpdated == null;
  }
}
