package dev.springharvest.library.domains.books.integration.utils.factories;

import dev.springharvest.library.config.TestComponentScanningConfig;
import dev.springharvest.library.domains.books.models.entities.BookEntity;
import dev.springharvest.library.domains.books.models.queries.BookFilterDTO;
import dev.springharvest.library.domains.books.models.queries.BookFilterRequestDTO;
import dev.springharvest.search.domains.base.models.entities.EntityMetadata;
import dev.springharvest.search.domains.base.models.queries.parameters.filters.CriteriaOperator;
import dev.springharvest.search.domains.base.models.queries.parameters.filters.FilterParameterDTO;
import dev.springharvest.testing.domains.integration.search.factories.AbstractSearchModelFactoryImpl;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@Component
@Import(value = {TestComponentScanningConfig.class})
public class BookSearchModelFactory
    extends AbstractSearchModelFactoryImpl<BookEntity, BookFilterRequestDTO> {

  @Autowired
  public BookSearchModelFactory(EntityMetadata<BookEntity> entityMetadata) {
    super(entityMetadata);
  }

  @Override
  public BookFilterRequestDTO buildValidFilters() {
    return BookFilterRequestDTO.builder()
        .book(BookFilterDTO.builder()
                  .id(FilterParameterDTO.builder()
                          .values(Set.of(UUID.fromString(
                              "00000000-0000-0000-0000-000000000001")))
                          .operator(CriteriaOperator.EQUALS)
                          .build())
                  .build())
        .build();
  }

}