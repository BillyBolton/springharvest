package dev.springharvest.library.domains.publishers.integration.utils.clients;

import dev.springharvest.library.domains.publishers.constants.PublisherConstants;
import dev.springharvest.library.domains.publishers.models.dtos.PublisherDTO;
import dev.springharvest.library.domains.publishers.models.queries.PublisherFilterRequestDTO;
import dev.springharvest.testing.integration.search.clients.AbstractSearchClientImpl;
import dev.springharvest.testing.integration.shared.clients.RestClientImpl;
import dev.springharvest.testing.integration.shared.uri.UriFactory;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PublishersSearchClient extends AbstractSearchClientImpl<PublisherDTO, UUID, PublisherFilterRequestDTO> {

  @Autowired
  protected PublishersSearchClient(RestClientImpl restClient) {
    super(restClient, new UriFactory(PublisherConstants.Controller.DOMAIN_CONTEXT), PublisherDTO.class);
  }

}
