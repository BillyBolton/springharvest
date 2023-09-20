package dev.springharvest.library.domains.books.integration.utils.helpers;

import dev.springharvest.library.config.TestComponentScanningConfig;
import dev.springharvest.library.domains.authors.integration.utils.clients.AuthorsCrudClient;
import dev.springharvest.library.domains.authors.integration.utils.helpers.AuthorsModelFactoryImpl;
import dev.springharvest.library.domains.publishers.integration.utils.clients.PublishersCrudClient;
import dev.springharvest.library.domains.publishers.integration.utils.helpers.PublishersModelFactoryImpl;
import dev.springharvest.shared.domains.books.models.dtos.BookDTO;
import dev.springharvest.shared.domains.books.models.entities.BookEntity;
import dev.springharvest.testing.integration.shared.helpers.AbstractModelTestFactory;
import dev.springharvest.testing.integration.shared.helpers.IModelTestFactory;
import java.util.UUID;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.SoftAssertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@Component
@Import(value = {TestComponentScanningConfig.class})
public class BooksModelFactoryImpl extends AbstractModelTestFactory<BookDTO, BookEntity, UUID>
    implements IModelTestFactory<BookDTO, BookEntity, UUID> {

  private final AuthorsCrudClient authorsCrudClient;
  private final AuthorsModelFactoryImpl authorsModelFactory;
  private final PublishersCrudClient publishersCrudClient;
  private final PublishersModelFactoryImpl publishersModelFactory;

  @Autowired(required = true)
  protected BooksModelFactoryImpl(AuthorsCrudClient authorsCrudClient, AuthorsModelFactoryImpl authorsModelFactory, PublishersCrudClient publishersCrudClient,
                                  PublishersModelFactoryImpl publishersModelFactory) {
    this.authorsCrudClient = authorsCrudClient;
    this.authorsModelFactory = authorsModelFactory;
    this.publishersCrudClient = publishersCrudClient;
    this.publishersModelFactory = publishersModelFactory;
  }

  @Override
  public Class<BookDTO> getClazz() {
    return BookDTO.class;
  }

  @Override
  public void softlyAssert(SoftAssertions softly, BookDTO actual, BookDTO expected) {
    super.softlyAssert(softly, actual, expected);
    softly.assertThat(actual.getId()).isEqualTo(expected.getId());
    softly.assertThat(actual.getTitle()).isEqualToIgnoringCase(expected.getTitle());
    softly.assertThat(actual.getAuthor()).isEqualTo(expected.getAuthor());
    softly.assertThat(actual.getPublisher()).isEqualTo(expected.getPublisher());
  }

  @Override
  public UUID getRandomId() {
    return UUID.randomUUID();
  }

  @Override
  public BookDTO buildValidDto() {
    return BookDTO.builder()
        .id(getRandomId())
        .title(RandomStringUtils.randomAlphabetic(5))
        .author(authorsCrudClient.extractCreate(authorsModelFactory.buildValidDto()))
        .publisher(publishersCrudClient.extractCreate(publishersModelFactory.buildValidDto()))
        .build();
  }


  @Override
  public BookDTO buildValidUpdatedDto(UUID id) {
    BookDTO dto = buildValidDto();
    dto.setId(id);
    return dto;
  }

  @Override
  public BookDTO buildValidUpdatedDto(BookDTO dto) {
    dto.setTitle(RandomStringUtils.randomAlphabetic(5));
    return dto;
  }

  @Override
  public BookDTO buildInvalidDto() {
    return BookDTO.builder().build();
  }

  @Override
  public BookEntity buildValidEntity() {
    return BookEntity.builder()
        .title(RandomStringUtils.randomAlphabetic(5))
        .author(authorsModelFactory.buildValidEntity())
        .publisher(publishersModelFactory.buildValidEntity())
        .build();
  }

  @Override
  public BookEntity buildInvalidEntity() {
    return BookEntity.builder().build();
  }

}
