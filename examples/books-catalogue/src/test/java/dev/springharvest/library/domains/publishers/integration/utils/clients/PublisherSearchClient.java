package dev.springharvest.library.domains.publishers.integration.utils.clients;

import dev.springharvest.library.domains.publishers.constants.PublisherConstants;
import dev.springharvest.library.domains.publishers.models.dtos.PublisherDTO;
import dev.springharvest.library.domains.publishers.models.queries.PublisherFilterRequestDTO;
import dev.springharvest.testing.domains.integration.search.clients.AbstractSearchClientImpl;
import dev.springharvest.testing.domains.integration.search.clients.uri.SearchUriFactory;
import dev.springharvest.testing.domains.integration.shared.domains.base.clients.RestClientImpl;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PublisherSearchClient extends AbstractSearchClientImpl<PublisherDTO, UUID, PublisherFilterRequestDTO> {

  @Autowired
  protected PublisherSearchClient(RestClientImpl restClient) {
    super(restClient, new SearchUriFactory("/api" + PublisherConstants.Controller.DOMAIN_CONTEXT), PublisherDTO.class);
  }

}
