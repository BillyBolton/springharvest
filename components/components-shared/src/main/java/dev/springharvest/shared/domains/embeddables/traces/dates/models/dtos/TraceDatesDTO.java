package dev.springharvest.shared.domains.embeddables.traces.dates.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.springharvest.shared.domains.DomainModel;
import dev.springharvest.shared.domains.embeddables.traces.dates.ITraceableDatesAware;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.ObjectUtils;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TraceDatesDTO extends DomainModel implements ITraceableDatesAware {

  protected Date dateCreated;

  protected Date dateUpdated;

  @JsonIgnore
  @Override
  public boolean isEmpty() {
    return ObjectUtils.isEmpty(dateCreated) && ObjectUtils.isEmpty(dateUpdated);
  }

}
