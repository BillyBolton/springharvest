package dev.springharvest.library.domains.publishers.integration.utils.helpers;


import dev.springharvest.library.config.TestComponentScanningConfig;
import dev.springharvest.shared.domains.publishers.models.dtos.PublisherDTO;
import dev.springharvest.shared.domains.publishers.models.entities.PublisherEntity;
import dev.springharvest.testing.integration.shared.helpers.AbstractModelTestFactory;
import dev.springharvest.testing.integration.shared.helpers.IModelTestFactory;
import java.util.UUID;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@Component
@Import(value = {TestComponentScanningConfig.class})
public class PublishersModelFactoryImpl extends AbstractModelTestFactory<PublisherDTO, PublisherEntity, UUID>
    implements IModelTestFactory<PublisherDTO, UUID> {

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

}
