package dev.springharvest.library.domains.publishers.integration.utils.helpers;


import dev.springharvest.library.config.TestComponentScanningConfig;
import dev.springharvest.library.domains.publishers.models.entities.PublisherEntityMetadata;
import dev.springharvest.library.domains.publishers.models.queries.PublisherFilterDTO;
import dev.springharvest.library.domains.publishers.models.queries.PublisherFilterRequestDTO;
import dev.springharvest.search.model.queries.parameters.filters.CriteriaOperator;
import dev.springharvest.search.model.queries.parameters.filters.FilterParameterDTO;
import dev.springharvest.search.model.queries.parameters.selections.SelectionDTO;
import dev.springharvest.shared.domains.publishers.constants.PublisherConstants;
import dev.springharvest.shared.domains.publishers.models.dtos.PublisherDTO;
import dev.springharvest.shared.domains.publishers.models.entities.PublisherEntity;
import dev.springharvest.testing.integration.search.helpers.AbstractSearchTestFactoryImpl;
import dev.springharvest.testing.integration.shared.clients.RestClientImpl;
import dev.springharvest.testing.integration.shared.uri.UriFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.SoftAssertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@Component
@Import(value = {TestComponentScanningConfig.class})
public class PublishersSearchTestFactory
    extends AbstractSearchTestFactoryImpl<PublisherDTO, PublisherEntity, UUID, PublisherFilterRequestDTO> {

  @Autowired(required = true)
  protected PublishersSearchTestFactory(RestClientImpl clientHelper) {
    super(clientHelper, new UriFactory(PublisherConstants.Controller.DOMAIN_CONTEXT));
  }


  @Override
  public String getIdPath() {
    return PublisherEntityMetadata.Paths.PUBLISHER_ID;
  }

  @Override
  public PublisherFilterRequestDTO buildValidFilters() {
    return PublisherFilterRequestDTO.builder()
        .publisher(PublisherFilterDTO.builder()
                       .id(FilterParameterDTO.builder()
                               .values(Set.of(
                                   UUID.fromString(
                                       "00000000" +
                                       "-0000" +
                                       "-0000" +
                                       "-0000" +
                                       "-000000000001")))
                               .operator(
                                   CriteriaOperator.EQUALS)
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
    selections.addAll(List.of(SelectionDTO.builder().alias(PublisherEntityMetadata.Paths.PUBLISHER_ID).build(),
                              SelectionDTO.builder().alias(PublisherEntityMetadata.Paths.PUBLISHER_NAME).build()));

    return selections;
  }

  @Override
  public Class<PublisherDTO> getClazz() {
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