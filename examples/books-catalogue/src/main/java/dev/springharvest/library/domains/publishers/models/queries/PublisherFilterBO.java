package dev.springharvest.library.domains.publishers.models.queries;

import dev.springharvest.search.domains.base.models.queries.parameters.filters.FilterParameterBO;
import dev.springharvest.search.domains.base.models.queries.requests.filters.BaseFilterBO;
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
public class PublisherFilterBO extends BaseFilterBO {

  private FilterParameterBO id;
  private FilterParameterBO name;

}
