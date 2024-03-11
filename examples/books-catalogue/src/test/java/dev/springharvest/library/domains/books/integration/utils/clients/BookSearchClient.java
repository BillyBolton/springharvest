package dev.springharvest.library.domains.books.integration.utils.clients;

import dev.springharvest.library.domains.books.constants.BookConstants;
import dev.springharvest.library.domains.books.models.dtos.BookDTO;
import dev.springharvest.library.domains.books.models.queries.BookFilterRequestDTO;
import dev.springharvest.testing.domains.integration.search.clients.AbstractSearchClientImpl;
import dev.springharvest.testing.domains.integration.search.clients.uri.SearchUriFactory;
import dev.springharvest.testing.domains.integration.shared.domains.base.clients.RestClientImpl;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookSearchClient extends AbstractSearchClientImpl<BookDTO, UUID, BookFilterRequestDTO> {

  @Autowired
  protected BookSearchClient(RestClientImpl restClient) {
    super(restClient, new SearchUriFactory("/api" + BookConstants.Controller.DOMAIN_CONTEXT), BookDTO.class);
  }

}
