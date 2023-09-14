package dev.springharvest.library.domains.authors.integration.utils.uri;

import dev.springharvest.library.authors.constants.AuthorConstants;
import dev.springharvest.testing.integration.utils.uri.AbstractBaseUriFactoryImpl;
import org.springframework.stereotype.Component;

@Component
public class AuthorsUriFactory extends AbstractBaseUriFactoryImpl {
    public String getDomainContext() {
        return AuthorConstants.Controller.DOMAIN_CONTEXT;
    }

}
