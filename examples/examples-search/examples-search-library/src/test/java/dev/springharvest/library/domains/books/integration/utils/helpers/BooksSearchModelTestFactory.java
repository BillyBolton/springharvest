package dev.springharvest.library.domains.books.integration.utils.helpers;

import dev.springharvest.library.config.TestComponentScanningConfig;
import dev.springharvest.library.domains.books.models.entities.BookEntityMetadata;
import dev.springharvest.library.domains.books.models.queries.BookFilterDTO;
import dev.springharvest.library.domains.books.models.queries.BookFilterRequestDTO;
import dev.springharvest.search.model.queries.parameters.filters.CriteriaOperator;
import dev.springharvest.search.model.queries.parameters.filters.FilterParameterDTO;
import dev.springharvest.search.model.queries.parameters.selections.SelectionDTO;
import dev.springharvest.testing.integration.search.helpers.AbstractSearchTestFactoryImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@Component
@Import(value = {TestComponentScanningConfig.class})
public class BooksSearchModelTestFactory
    extends AbstractSearchTestFactoryImpl<BookFilterRequestDTO> {

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

    return selections;
  }

}