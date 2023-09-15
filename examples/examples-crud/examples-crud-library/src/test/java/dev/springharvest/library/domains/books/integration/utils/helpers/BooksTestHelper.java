package dev.springharvest.library.domains.books.integration.utils.helpers;

import dev.springharvest.library.config.TestComponentScanningConfig;
import dev.springharvest.library.domains.authors.integration.utils.helpers.AuthorsCrudTestHelper;
import dev.springharvest.library.domains.publishers.integration.utils.helpers.PublishersTestHelper;
import dev.springharvest.shared.domains.books.constants.BookConstants;
import dev.springharvest.shared.domains.books.models.dtos.BookDTO;
import dev.springharvest.shared.domains.books.models.entities.BookEntity;
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
public class BooksTestHelper extends AbstractCrudTestHelperImpl<BookDTO, BookEntity, UUID> {

    private final AuthorsCrudTestHelper authorsCrudTestHelper;
    private final PublishersTestHelper publishersTestHelper;

    @Autowired(required = true)
    protected BooksTestHelper(RestClientImpl clientHelper, AuthorsCrudTestHelper authorsCrudTestHelper,
                              PublishersTestHelper publishersTestHelper) {
        super(clientHelper, new UriFactory(BookConstants.Controller.DOMAIN_CONTEXT));
        this.authorsCrudTestHelper = authorsCrudTestHelper;
        this.publishersTestHelper = publishersTestHelper;
    }

    @Override
    public Class<BookDTO> getClassType() {
        return BookDTO.class;
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
                      .author(authorsCrudTestHelper.create(authorsCrudTestHelper.buildValidDto())
                                                   .statusCode(200)
                                                   .extract()
                                                   .body()
                                                   .jsonPath()
                                                   .getObject("", authorsCrudTestHelper.getClassType()))
                      .publisher(publishersTestHelper.create(publishersTestHelper.buildValidDto())
                                                     .statusCode(200)
                                                     .extract()
                                                     .body()
                                                     .jsonPath()
                                                     .getObject("", publishersTestHelper.getClassType()))
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
                         .author(authorsCrudTestHelper.buildValidEntity())
                         .publisher(publishersTestHelper.buildValidEntity())
                         .build();
    }

    @Override
    public BookEntity buildInvalidEntity() {
        return BookEntity.builder().build();
    }

    @Override
    public void softlyAssert(SoftAssertions softly, BookDTO actual, BookDTO expected) {
        super.softlyAssert(softly, actual, expected);
        softly.assertThat(actual.getId()).isEqualTo(expected.getId());
        softly.assertThat(actual.getTitle()).isEqualToIgnoringCase(expected.getTitle());
        softly.assertThat(actual.getAuthor()).isEqualTo(expected.getAuthor());
        softly.assertThat(actual.getPublisher()).isEqualTo(expected.getPublisher());
    }

}
