package dev.springharvest.library.domains.publishers.integration.utils.helpers;


import dev.springharvest.library.config.TestComponentScanningConfig;
import dev.springharvest.shared.domains.publishers.constants.PublisherConstants;
import dev.springharvest.shared.domains.publishers.models.dtos.PublisherDTO;
import dev.springharvest.shared.domains.publishers.models.entities.PublisherEntity;
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
public class PublishersTestHelper extends AbstractBaseCrudTestHelperImpl<PublisherDTO, PublisherEntity, UUID> {

    @Autowired(required = true)
    protected PublishersTestHelper(RestClientImpl clientHelper) {
        super(clientHelper, new UriFactory(PublisherConstants.Controller.DOMAIN_CONTEXT));
    }

    @Override
    public Class<PublisherDTO> getClassType() {
        return PublisherDTO.class;
    }

    @Override
    public UUID getRandomId() {
        return UUID.randomUUID();
    }

    @Override
    public PublisherDTO buildValidDto() {
        return PublisherDTO.builder().id(getRandomId()).name(RandomStringUtils.randomAlphabetic(5)).build();
    }

    @Override
    public PublisherDTO buildValidUpdatedDto(UUID id) {
        PublisherDTO dto = buildValidDto();
        dto.setId(id);
        return dto;
    }

    @Override
    public PublisherDTO buildValidUpdatedDto(PublisherDTO dto) {
        dto.setName(RandomStringUtils.randomAlphabetic(5));
        return dto;
    }

    @Override
    public PublisherDTO buildInvalidDto() {
        return PublisherDTO.builder().build();
    }

    @Override
    public PublisherEntity buildValidEntity() {
        return PublisherEntity.builder().name(RandomStringUtils.randomAlphabetic(5)).build();
    }

    @Override
    public PublisherEntity buildInvalidEntity() {
        return PublisherEntity.builder().build();
    }

    @Override
    public void softlyAssert(SoftAssertions softly, PublisherDTO actual, PublisherDTO expected) {
        super.softlyAssert(softly, actual, expected);
        softly.assertThat(actual.getId()).isEqualTo(expected.getId());
        softly.assertThat(actual.getName()).isEqualToIgnoringCase(expected.getName());
    }

}
