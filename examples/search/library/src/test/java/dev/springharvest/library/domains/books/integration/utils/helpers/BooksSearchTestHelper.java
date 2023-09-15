package dev.springharvest.library.domains.books.integration.utils.helpers;

import dev.springharvest.library.config.TestComponentScanningConfig;
import dev.springharvest.library.domains.authors.models.entities.AuthorEntityMetadata;
import dev.springharvest.library.domains.books.models.entities.BookEntityMetadata;
import dev.springharvest.library.domains.books.models.queries.BookFilterDTO;
import dev.springharvest.library.domains.books.models.queries.BookFilterRequestDTO;
import dev.springharvest.search.model.queries.parameters.filters.CriteriaOperator;
import dev.springharvest.search.model.queries.parameters.filters.FilterParameterDTO;
import dev.springharvest.search.model.queries.parameters.selections.SelectionDTO;
import dev.springharvest.shared.domains.books.constants.BookConstants;
import dev.springharvest.shared.domains.books.models.dtos.BookDTO;
import dev.springharvest.shared.domains.books.models.entities.BookEntity;
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
public class BooksSearchTestHelper
        extends AbstractSearchTestHelperImpl<BookDTO, BookEntity, UUID, BookFilterRequestDTO> {

    @Autowired(required = true)
    protected BooksSearchTestHelper(RestClientImpl clientHelper) {
        super(clientHelper, new UriFactory(BookConstants.Controller.DOMAIN_CONTEXT));
    }


    @Override
    public String getIdPath() {
        return BookEntityMetadata.Paths.BOOK_ID;
    }

    @Override
    public BookFilterRequestDTO buildValidFilters() {
        return BookFilterRequestDTO.builder()
                                   .book(BookFilterDTO.builder()
                                                      .id(FilterParameterDTO.builder()
                                                                            .values(Set.of(UUID.fromString(
                                                                                    "00000000" + "-0000" + "-0000" +
                                                                                    "-0000" + "-000000000001")))
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
        List<SelectionDTO> selections = new ArrayList<>(
                List.of(SelectionDTO.builder().alias(BookEntityMetadata.Paths.BOOK_ID).build(),
                        SelectionDTO.builder().alias(BookEntityMetadata.Paths.BOOK_TITLE).build()));

        AuthorEntityMetadata.Paths.

        return selections;
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
        return BookDTO.builder().id(getRandomId()).title(RandomStringUtils.randomAlphabetic(5)).build();
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
        return BookEntity.builder().title(RandomStringUtils.randomAlphabetic(5)).build();
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