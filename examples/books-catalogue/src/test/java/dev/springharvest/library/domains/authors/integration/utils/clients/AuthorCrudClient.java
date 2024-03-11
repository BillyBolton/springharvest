package dev.springharvest.library.domains.authors.integration.utils.clients;

import dev.springharvest.library.config.TestComponentScanningConfig;
import dev.springharvest.library.domains.authors.constants.AuthorConstants;
import dev.springharvest.library.domains.authors.models.dtos.AuthorDTO;
import dev.springharvest.testing.domains.integration.crud.domains.base.clients.AbstractCrudClientImpl;
import dev.springharvest.testing.domains.integration.crud.domains.base.clients.uri.CrudUriFactory;
import dev.springharvest.testing.domains.integration.shared.domains.base.clients.RestClientImpl;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@Component
@Import(value = {TestComponentScanningConfig.class})
public class AuthorCrudClient extends AbstractCrudClientImpl<AuthorDTO, UUID> {

  @Autowired(required = true)
  protected AuthorCrudClient(RestClientImpl clientHelper) {
    super(clientHelper, new CrudUriFactory("/api" + AuthorConstants.Controller.DOMAIN_CONTEXT), AuthorDTO.class);
  }

}
