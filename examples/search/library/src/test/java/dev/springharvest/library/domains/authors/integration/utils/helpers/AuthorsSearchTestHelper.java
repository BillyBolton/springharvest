package dev.springharvest.library.domains.authors.integration.utils.helpers;

import dev.springharvest.library.config.TestComponentScanningConfig;
import dev.springharvest.library.domains.authors.models.entities.AuthorEntityMetadata;
import dev.springharvest.library.domains.authors.models.queries.AuthorFilterDTO;
import dev.springharvest.library.domains.authors.models.queries.AuthorFilterRequestDTO;
import dev.springharvest.search.model.queries.parameters.filters.CriteriaOperator;
import dev.springharvest.search.model.queries.parameters.filters.FilterParameterDTO;
import dev.springharvest.search.model.queries.parameters.selections.SelectionDTO;
import dev.springharvest.shared.domains.authors.constants.AuthorConstants;
import dev.springharvest.shared.domains.authors.models.dtos.AuthorDTO;
import dev.springharvest.shared.domains.authors.models.entities.AuthorEntity;
import dev.springharvest.testing.integration.search.helpers.AbstractSearchTestHelperImpl;
import dev.springharvest.testing.integration.shared.clients.RestClientImpl;
import dev.springharvest.testing.integration.shared.uri.UriFactory;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.SoftAssertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Component
@Import(value = {TestComponentScanningConfig.class})
public class AuthorsSearchTestHelper
        extends AbstractSearchTestHelperImpl<AuthorDTO, AuthorEntity, UUID, AuthorFilterRequestDTO> {

    @Autowired(required = true)
    protected AuthorsSearchTestHelper(RestClientImpl clientHelper) {
        super(clientHelper, new UriFactory(AuthorConstants.Controller.DOMAIN_CONTEXT));
    }


    @Override
    public String getIdPath() {
        return AuthorEntityMetadata.Paths.AUTHOR_ID;
    }

    @Override
    public AuthorFilterRequestDTO buildValidFilters() {
        return AuthorFilterRequestDTO.builder()
                                     .author(AuthorFilterDTO.builder()
                                                            .id(FilterParameterDTO.builder()
                                                                                  .values(Set.of(UUID.fromString(
                                                                                          "00000000-0000-0000-0000" +
                                                                                          "-000000000001")))
                                                                                  .operator(CriteriaOperator.EQUALS)
                                                                                  .build())
                                                            .build())
                                     .build();
    }

    @Override
    public List<SelectionDTO> buildValidSelections(boolean selectAll) {

        if (selectAll) {
            return List.of();
        }
        List<SelectionDTO> selections = new ArrayList<>();
        selections.addAll(List.of(SelectionDTO.builder().alias(AuthorEntityMetadata.Paths.AUTHOR_ID).build(),
                                  SelectionDTO.builder().alias(AuthorEntityMetadata.Paths.AUTHOR_NAME).build()));

        return selections;
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
