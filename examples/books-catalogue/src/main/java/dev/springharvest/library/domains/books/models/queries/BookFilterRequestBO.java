package dev.springharvest.library.domains.books.models.queries;

import dev.springharvest.library.domains.authors.models.queries.AuthorFilterBO;
import dev.springharvest.library.domains.publishers.models.queries.PublisherFilterBO;
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
public class BookFilterRequestBO extends BaseFilterRequestBO {

  private BookFilterBO book;

  private AuthorFilterBO author;

  private PublisherFilterBO publisher;

}
