package dev.springharvest.library.domains.publishers.models.queries;

import dev.springharvest.search.model.queries.requests.filters.BaseFilterRequestDTO;
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
public class PublisherFilterRequestDTO extends BaseFilterRequestDTO {

    private PublisherFilterDTO publisher;

}
