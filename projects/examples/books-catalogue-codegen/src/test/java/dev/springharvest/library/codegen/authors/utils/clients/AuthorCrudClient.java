package dev.springharvest.library.codegen.authors.utils.clients;

import dev.springharvest.library.codegen.config.TestComponentScanningConfig;
import dev.springharvest.library.codegen.domains.authors.author.models.dtos.AuthorDTO;
import dev.springharvest.library.codegen.domains.authors.author.rest.constants.AuthorConstants_;
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
    super(clientHelper, new CrudUriFactory("/api" + AuthorConstants_.Controller.DOMAIN_CONTEXT), AuthorDTO.class);
  }

}
