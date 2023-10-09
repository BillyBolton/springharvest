package dev.springharvest.library.domains.authors.integration.utils.factories;

import dev.springharvest.library.config.TestComponentScanningConfig;
import dev.springharvest.library.domains.authors.models.dtos.AuthorDTO;
import dev.springharvest.testing.integration.shared.factories.AbstractModelFactory;
import dev.springharvest.testing.integration.shared.factories.IPKModelFactory;
import java.util.UUID;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@Component
@Import(value = {TestComponentScanningConfig.class})
public class AuthorModelFactory extends AbstractModelFactory<AuthorDTO, UUID>
    implements IPKModelFactory<AuthorDTO, UUID> {

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

}
