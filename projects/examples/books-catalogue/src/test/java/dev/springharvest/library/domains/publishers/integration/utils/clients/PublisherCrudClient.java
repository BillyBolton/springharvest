package dev.springharvest.library.domains.publishers.integration.utils.clients;

import dev.springharvest.library.config.TestComponentScanningConfig;
import dev.springharvest.library.domains.publishers.constants.PublisherConstants;
import dev.springharvest.library.domains.publishers.models.dtos.PublisherDTO;
import dev.springharvest.testing.domains.integration.crud.domains.base.clients.AbstractCrudClientImpl;
import dev.springharvest.testing.domains.integration.crud.domains.base.clients.uri.CrudUriFactory;
import dev.springharvest.testing.domains.integration.shared.domains.base.clients.RestClientImpl;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@Component
@Import(value = {TestComponentScanningConfig.class})
public class PublisherCrudClient extends AbstractCrudClientImpl<PublisherDTO, UUID> {

  @Autowired(required = true)
  protected PublisherCrudClient(RestClientImpl clientHelper) {
    super(clientHelper, new CrudUriFactory("/api" + PublisherConstants.Controller.DOMAIN_CONTEXT), PublisherDTO.class);
  }

}