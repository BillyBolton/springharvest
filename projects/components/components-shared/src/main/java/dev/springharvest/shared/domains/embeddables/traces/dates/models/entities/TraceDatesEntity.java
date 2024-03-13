package dev.springharvest.shared.domains.embeddables.traces.dates.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.springharvest.shared.domains.DomainModel;
import dev.springharvest.shared.domains.embeddables.traces.dates.ITraceableDatesAware;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
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
@Embeddable
public class TraceDatesEntity extends DomainModel implements ITraceableDatesAware {

  @Column(name = "date_created", updatable = false, nullable = false)
  protected Date dateCreated;

  @Column(name = "date_updated", updatable = true, nullable = false)
  protected Date dateUpdated;

  @JsonIgnore
  @Override
  public boolean isEmpty() {
    return ObjectUtils.isEmpty(dateCreated) && ObjectUtils.isEmpty(dateUpdated);
  }

}
