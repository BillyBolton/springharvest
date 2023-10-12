package dev.springharvest.library.domains.authors.integration.utils.factories;

import dev.springharvest.library.config.TestComponentScanningConfig;
import dev.springharvest.library.domains.authors.models.dtos.AuthorDTOAbstract;
import dev.springharvest.testing.domains.integration.shared.factories.AbstractModelFactory;
import dev.springharvest.testing.domains.integration.shared.factories.IPKModelFactory;
import java.util.UUID;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@Component
@Import(value = {TestComponentScanningConfig.class})
public class AuthorModelFactory extends AbstractModelFactory<AuthorDTOAbstract, UUID>
    implements IPKModelFactory<AuthorDTOAbstract, UUID> {

  @Override
  public UUID getRandomId() {
    return UUID.randomUUID();
  }

  @Override
  public AuthorDTOAbstract buildValidDto() {
    return AuthorDTOAbstract.builder().id(getRandomId()).name(RandomStringUtils.randomAlphabetic(5)).build();
  }

  @Override
  public AuthorDTOAbstract buildValidUpdatedDto(UUID id) {
    AuthorDTOAbstract dto = buildValidDto();
    dto.setId(id);
    return dto;
  }

  @Override
  public AuthorDTOAbstract buildValidUpdatedDto(AuthorDTOAbstract dto) {
    dto.setName(RandomStringUtils.randomAlphabetic(5));
    return dto;
  }

  @Override
  public AuthorDTOAbstract buildInvalidDto() {
    return AuthorDTOAbstract.builder().build();
  }

}
