package dev.springharvest.library.domains.books.models.queries;

import dev.springharvest.search.model.queries.parameters.filters.FilterParameterBO;
import dev.springharvest.search.model.queries.requests.filters.BaseFilterBO;
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
public class BookFilterBO extends BaseFilterBO {

    private FilterParameterBO id;
    private FilterParameterBO title;

}
