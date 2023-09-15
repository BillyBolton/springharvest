package dev.springharvest.library.domains.authors.integration.utils.helpers;

import dev.springharvest.library.config.TestComponentScanningConfig;
import dev.springharvest.shared.domains.authors.constants.AuthorConstants;
import dev.springharvest.shared.domains.authors.models.dtos.AuthorDTO;
import dev.springharvest.shared.domains.authors.models.entities.AuthorEntity;
import dev.springharvest.testing.integration.crud.helpers.AbstractCrudTestHelperImpl;
import dev.springharvest.testing.integration.shared.clients.RestClientImpl;
import dev.springharvest.testing.integration.shared.uri.UriFactory;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.SoftAssertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Import(value = {TestComponentScanningConfig.class})
public class AuthorsCrudTestHelper extends AbstractCrudTestHelperImpl<AuthorDTO, AuthorEntity, UUID> {

    @Autowired(required = true)
    protected AuthorsCrudTestHelper(RestClientImpl clientHelper) {
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
