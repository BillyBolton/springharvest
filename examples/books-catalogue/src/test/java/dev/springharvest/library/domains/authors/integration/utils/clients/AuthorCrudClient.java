package dev.springharvest.library.domains.authors.integration.utils.clients;

import dev.springharvest.library.config.TestComponentScanningConfig;
import dev.springharvest.library.domains.authors.constants.AuthorConstants;
import dev.springharvest.library.domains.authors.models.dtos.AuthorDTOAbstract;
import dev.springharvest.testing.domains.integration.crud.clients.AbstractCrudClientImpl;
import dev.springharvest.testing.domains.integration.crud.clients.uri.CrudUriFactory;
import dev.springharvest.testing.domains.integration.shared.clients.RestClientImpl;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@Component
@Import(value = {TestComponentScanningConfig.class})
public class AuthorCrudClient extends AbstractCrudClientImpl<AuthorDTOAbstract, UUID> {

  @Autowired(required = true)
  protected AuthorCrudClient(RestClientImpl clientHelper) {
    super(clientHelper, new CrudUriFactory(AuthorConstants.Controller.DOMAIN_CONTEXT), AuthorDTOAbstract.class);
  }

}
