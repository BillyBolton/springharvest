package dev.springharvest.search.domains.embeddables.traces.trace.models.queries;

import dev.springharvest.search.domains.base.models.queries.parameters.filters.FilterParameterBO;
import dev.springharvest.search.domains.base.models.queries.requests.filters.BaseFilterBO;
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
public class TraceDataFilterBO extends BaseFilterBO {

  private FilterParameterBO dateCreated;
  private FilterParameterBO createdBy;
  private FilterParameterBO dateUpdated;
  private FilterParameterBO updatedBy;

}
