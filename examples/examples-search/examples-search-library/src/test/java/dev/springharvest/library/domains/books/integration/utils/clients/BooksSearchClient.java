package dev.springharvest.library.domains.books.integration.utils.clients;

import dev.springharvest.library.domains.books.models.queries.BookFilterRequestDTO;
import dev.springharvest.shared.domains.books.constants.BookConstants;
import dev.springharvest.shared.domains.books.models.dtos.BookDTO;
import dev.springharvest.shared.domains.books.models.entities.BookEntity;
import dev.springharvest.testing.integration.search.clients.AbstractSearchClientImpl;
import dev.springharvest.testing.integration.shared.clients.RestClientImpl;
import dev.springharvest.testing.integration.shared.uri.UriFactory;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BooksSearchClient extends AbstractSearchClientImpl<BookDTO, BookEntity, UUID, BookFilterRequestDTO> {

  @Autowired
  protected BooksSearchClient(RestClientImpl restClient) {
    super(restClient, new UriFactory(BookConstants.Controller.DOMAIN_CONTEXT), BookDTO.class);
  }

}
