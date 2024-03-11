package dev.springharvest.library.domains.authors.integration.utils.clients;

import dev.springharvest.library.domains.authors.constants.AuthorConstants;
import dev.springharvest.library.domains.authors.models.dtos.AuthorDTO;
import dev.springharvest.library.domains.authors.models.queries.AuthorFilterRequestDTO;
import dev.springharvest.testing.domains.integration.search.clients.AbstractSearchClientImpl;
import dev.springharvest.testing.domains.integration.search.clients.uri.SearchUriFactory;
import dev.springharvest.testing.domains.integration.shared.domains.base.clients.RestClientImpl;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthorSearchClient extends AbstractSearchClientImpl<AuthorDTO, UUID, AuthorFilterRequestDTO> {

  @Autowired
  protected AuthorSearchClient(RestClientImpl restClient) {
    super(restClient, new SearchUriFactory("/api" + AuthorConstants.Controller.DOMAIN_CONTEXT), AuthorDTO.class);
  }

}
