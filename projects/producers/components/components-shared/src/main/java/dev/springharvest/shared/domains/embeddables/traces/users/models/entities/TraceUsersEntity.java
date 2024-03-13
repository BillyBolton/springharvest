package dev.springharvest.shared.domains.embeddables.traces.users.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.springharvest.shared.domains.DomainModel;
import dev.springharvest.shared.domains.embeddables.traces.users.ITraceUsersAware;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
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
public class TraceUsersEntity<K extends Serializable> extends DomainModel implements ITraceUsersAware<K> {

  @Column(name = "created_by", updatable = false, nullable = false)
  protected K createdBy;

  @Column(name = "updated_by", updatable = true, nullable = false)
  protected K updatedBy;


  @JsonIgnore
  @Override
  public boolean isEmpty() {
    return createdBy == null && updatedBy == null;
  }
}
