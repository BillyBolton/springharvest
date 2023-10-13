package dev.springharvest.search.domains.embeddables.traces.trace.models.queries;

import dev.springharvest.search.domains.base.models.queries.requests.filters.BaseFilterRequestBO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TraceDataFilterRequestBO extends BaseFilterRequestBO {

  private TraceDataFilterBO traceData;

}
