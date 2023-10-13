package dev.springharvest.shared.domains.embeddables.traces.users.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import dev.springharvest.shared.domains.DomainModel;
import dev.springharvest.shared.domains.embeddables.traces.users.ITraceUsersAware;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AbstractTraceUsersDTO<K extends Serializable> extends DomainModel implements ITraceUsersAware<K> {

  protected K createdBy;
  protected K updatedBy;

  @JsonIgnore
  @Override
  public boolean isEmpty() {
    return createdBy == null && updatedBy == null;
  }
}
