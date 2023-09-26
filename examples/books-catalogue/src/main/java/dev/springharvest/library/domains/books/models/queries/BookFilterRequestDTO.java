package dev.springharvest.library.domains.books.models.queries;

import dev.springharvest.library.domains.authors.models.queries.AuthorFilterDTO;
import dev.springharvest.library.domains.publishers.models.queries.PublisherFilterDTO;
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
public class BookFilterRequestDTO extends BaseFilterRequestDTO {

  private BookFilterDTO book;

  private AuthorFilterDTO author;

  private PublisherFilterDTO publisher;

}
