package dev.springharvest.library.domains.authors.integration.utils.helpers;

import dev.springharvest.library.config.TestComponentScanningConfig;
import dev.springharvest.library.domains.authors.constants.AuthorConstants;
import dev.springharvest.library.domains.authors.models.dtos.AuthorDTO;
import dev.springharvest.testing.integration.utils.clients.RestClientImpl;
import dev.springharvest.testing.integration.utils.helpers.AbstractBaseCrudTestHelperImpl;
import dev.springharvest.testing.integration.utils.uri.UriFactory;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.SoftAssertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Import(value = {TestComponentScanningConfig.class})
public class AuthorsTestHelper extends AbstractBaseCrudTestHelperImpl<AuthorDTO, AuthorEntity, UUID> {

    @Autowired(required = true)
    protected AuthorsTestHelper(RestClientImpl clientHelper) {
        super(clientHelper, new UriFactory(AuthorConstants.Controller.DOMAIN_CONTEXT));
    }

    @Override
    public Class<AuthorDTO> getClassType() {
        return AuthorDTO.class;
    }

    @Override
    public UUID getRandomId() {
        return UUID.randomUUID();
    }

    @Override
    public AuthorDTO buildValidDto() {
        return AuthorDTO.builder().id(getRandomId()).name(RandomStringUtils.randomAlphabetic(5)).build();
    }

    @Override
    public AuthorDTO buildValidUpdatedDto(UUID id) {
        AuthorDTO dto = buildValidDto();
        dto.setId(id);
        return dto;
    }

    @Override
    public AuthorDTO buildValidUpdatedDto(AuthorDTO dto) {
        dto.setName(RandomStringUtils.randomAlphabetic(5));
        return dto;
    }

    @Override
    public AuthorDTO buildInvalidDto() {
        return AuthorDTO.builder().build();
    }

    @Override
    public AuthorEntity buildValidEntity() {
        return AuthorEntity.builder().name(RandomStringUtils.randomAlphabetic(5)).build();
    }

    @Override
    public AuthorEntity buildInvalidEntity() {
        return AuthorEntity.builder().build();
    }

    @Override
    public void softlyAssert(SoftAssertions softly, AuthorDTO actual, AuthorDTO expected) {
        super.softlyAssert(softly, actual, expected);
        softly.assertThat(actual.getId()).isEqualTo(expected.getId());
        softly.assertThat(actual.getName()).isEqualToIgnoringCase(expected.getName());
    }

}
