package dev.springharvest.library.domains.authors.integration.utils.clients;

import dev.springharvest.library.config.TestComponentScanningConfig;
import dev.springharvest.shared.domains.authors.constants.AuthorConstants;
import dev.springharvest.shared.domains.authors.models.dtos.AuthorDTO;
import dev.springharvest.testing.integration.crud.clients.AbstractCrudClientImpl;
import dev.springharvest.testing.integration.shared.clients.RestClientImpl;
import dev.springharvest.testing.integration.shared.uri.UriFactory;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@Component
@Import(value = {TestComponentScanningConfig.class})
public class AuthorsCrudClient extends AbstractCrudClientImpl<AuthorDTO, UUID> {

  @Autowired(required = true)
  protected AuthorsCrudClient(RestClientImpl clientHelper) {
    super(clientHelper, new UriFactory(AuthorConstants.Controller.DOMAIN_CONTEXT), AuthorDTO.class);
  }

}
