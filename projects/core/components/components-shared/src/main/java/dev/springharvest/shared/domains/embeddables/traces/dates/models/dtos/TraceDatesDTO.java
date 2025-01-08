package dev.springharvest.shared.domains.embeddables.traces.dates.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import dev.springharvest.shared.domains.DomainModel;
import dev.springharvest.shared.domains.embeddables.traces.dates.ITraceableDatesAware;
import java.time.LocalDate;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TraceDatesDTO extends DomainModel implements ITraceableDatesAware {

  protected LocalDate dateCreated;

  protected LocalDate dateUpdated;

  @JsonIgnore
  @Override
  public boolean isEmpty() {
    return ObjectUtils.isEmpty(dateCreated) && ObjectUtils.isEmpty(dateUpdated);
  }

}
