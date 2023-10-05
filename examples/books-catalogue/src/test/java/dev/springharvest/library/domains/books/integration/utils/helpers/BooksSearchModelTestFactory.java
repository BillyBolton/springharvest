package dev.springharvest.library.domains.books.integration.utils.helpers;

import dev.springharvest.library.config.TestComponentScanningConfig;
import dev.springharvest.library.domains.books.models.entities.BookEntity;
import dev.springharvest.library.domains.books.models.queries.BookFilterDTO;
import dev.springharvest.library.domains.books.models.queries.BookFilterRequestDTO;
import dev.springharvest.search.model.entities.EntityMetadata;
import dev.springharvest.search.model.queries.parameters.filters.CriteriaOperator;
import dev.springharvest.search.model.queries.parameters.filters.FilterParameterDTO;
import dev.springharvest.testing.integration.search.helpers.AbstractSearchTestFactoryImpl;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@Component
@Import(value = {TestComponentScanningConfig.class})
public class BooksSearchModelTestFactory
    extends AbstractSearchTestFactoryImpl<BookEntity, BookFilterRequestDTO> {

  @Autowired
  public BooksSearchModelTestFactory(EntityMetadata<BookEntity> entityMetadata) {
    super(entityMetadata);
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

}