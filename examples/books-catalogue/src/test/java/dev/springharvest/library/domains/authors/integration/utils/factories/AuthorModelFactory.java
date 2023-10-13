package dev.springharvest.library.domains.authors.integration.utils.factories;

import dev.springharvest.library.config.TestComponentScanningConfig;
import dev.springharvest.library.domains.authors.models.dtos.AuthorDTO;
import dev.springharvest.testing.domains.integration.crud.domains.embeddables.traces.factories.TraceDataModelFactory;
import dev.springharvest.testing.domains.integration.shared.domains.base.factories.AbstractModelFactory;
import java.util.UUID;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.SoftAssertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@Component
@Import(value = {TestComponentScanningConfig.class})
public class AuthorModelFactory extends AbstractModelFactory<AuthorDTO, UUID> {

  private final TraceDataModelFactory traceDataModelFactory;

  @Autowired
  public AuthorModelFactory(TraceDataModelFactory traceDataModelFactory) {
    this.traceDataModelFactory = traceDataModelFactory;
  }

  @Override
  public void softlyAssert(SoftAssertions softly, AuthorDTO actual, AuthorDTO expected) {
    super.softlyAssert(softly, actual, expected);
    softly.assertThat(actual.getId()).isEqualTo(expected.getId());
    softly.assertThat(actual.getName()).isEqualToIgnoringCase(expected.getName());
  }

  @Override
  public UUID getRandomId() {
    return UUID.randomUUID();
  }

  @Override
  public AuthorDTO buildValidDto() {
    return AuthorDTO.builder()
        .id(getRandomId())
        .name(RandomStringUtils.randomAlphabetic(5))
        .traceData(traceDataModelFactory.buildValidDto())
        .build();
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

}
