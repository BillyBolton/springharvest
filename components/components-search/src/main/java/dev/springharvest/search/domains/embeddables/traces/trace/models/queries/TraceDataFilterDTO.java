package dev.springharvest.search.domains.embeddables.traces.trace.models.queries;

import dev.springharvest.search.domains.base.models.queries.parameters.filters.FilterParameterDTO;
import dev.springharvest.search.domains.base.models.queries.requests.filters.BaseFilterDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class TraceDataFilterDTO extends BaseFilterDTO {

  private FilterParameterDTO dateCreated;
  private FilterParameterDTO createdBy;
  private FilterParameterDTO dateUpdated;
  private FilterParameterDTO updatedBy;

}
