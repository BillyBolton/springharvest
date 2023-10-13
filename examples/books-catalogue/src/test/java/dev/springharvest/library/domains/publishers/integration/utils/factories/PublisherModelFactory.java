package dev.springharvest.library.domains.publishers.integration.utils.factories;


import dev.springharvest.library.config.TestComponentScanningConfig;
import dev.springharvest.library.domains.publishers.models.dtos.PublisherDTO;
import dev.springharvest.testing.domains.integration.crud.domains.embeddables.traces.factories.TraceDataModelFactory;
import dev.springharvest.testing.domains.integration.shared.domains.base.factories.AbstractModelFactory;
import dev.springharvest.testing.domains.integration.shared.domains.base.factories.IPKModelFactory;
import java.util.UUID;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.SoftAssertions;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@Component
@Import(value = {TestComponentScanningConfig.class})
public class PublisherModelFactory extends AbstractModelFactory<PublisherDTO, UUID>
    implements IPKModelFactory<PublisherDTO, UUID> {

  private final TraceDataModelFactory traceDataModelFactory;

  public PublisherModelFactory(TraceDataModelFactory traceDataModelFactory) {
    this.traceDataModelFactory = traceDataModelFactory;
  }

  @Override
  public void softlyAssert(SoftAssertions softly, PublisherDTO actual, PublisherDTO expected) {
    super.softlyAssert(softly, actual, expected);
    softly.assertThat(actual.getId()).isEqualTo(expected.getId());
    softly.assertThat(actual.getName()).isEqualToIgnoringCase(expected.getName());
  }

  @Override
  public UUID getRandomId() {
    return UUID.randomUUID();
  }

  @Override
  public PublisherDTO buildValidDto() {
    return PublisherDTO.builder()
        .id(getRandomId())
        .name(RandomStringUtils.randomAlphabetic(5))
        .traceData(traceDataModelFactory.buildValidDto())
        .build();
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


}
