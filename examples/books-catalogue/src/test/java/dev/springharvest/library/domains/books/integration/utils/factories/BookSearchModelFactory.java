package dev.springharvest.library.domains.books.integration.utils.factories;

import dev.springharvest.library.config.TestComponentScanningConfig;
import dev.springharvest.library.domains.books.models.dtos.BookDTO;
import dev.springharvest.library.domains.books.models.entities.BookEntity;
import dev.springharvest.library.domains.books.models.queries.BookFilterDTO;
import dev.springharvest.library.domains.books.models.queries.BookFilterRequestDTO;
import dev.springharvest.search.domains.base.models.entities.EntityMetadata;
import dev.springharvest.search.domains.base.models.queries.parameters.filters.CriteriaOperator;
import dev.springharvest.search.domains.base.models.queries.parameters.filters.FilterParameterDTO;
import dev.springharvest.testing.domains.integration.search.factories.AbstractSearchModelFactoryImpl;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@Component
@Import(value = {TestComponentScanningConfig.class})
public class BookSearchModelFactory
    extends AbstractSearchModelFactoryImpl<BookDTO, BookEntity, BookFilterRequestDTO> {

  @Autowired
  public BookSearchModelFactory(EntityMetadata<BookEntity> entityMetadata) {
    super(entityMetadata);
  }

  @Override
  public Set<BookFilterRequestDTO> buildValidUniqueFilters(CriteriaOperator operator, List<BookDTO> models, boolean explodeRequest) {
    if (explodeRequest) {
      return models.stream().map(model -> BookFilterRequestDTO.builder()
          .book(BookFilterDTO.builder()
                    .id(FilterParameterDTO.builder()
                            .values(List.of(model.getId()))
                            .operator(operator)
                            .build())
                    .title(FilterParameterDTO.builder()
                               .values(List.of(model.getTitle()))
                               .operator(operator)
                               .build())
                    .build())
          .build()).collect(Collectors.toSet());
    } else {
      List<UUID> ids = new LinkedList<>();
      List<String> titles = new LinkedList<>();
      models.forEach(model -> {
        ids.add(model.getId());
        titles.add(model.getTitle());
      });
      return Set.of(BookFilterRequestDTO.builder()
                        .book(BookFilterDTO.builder()
                                  .id(FilterParameterDTO.builder()
                                          .values(ids)
                                          .operator(operator)
                                          .build())
                                  .title(FilterParameterDTO.builder()
                                             .values(titles)
                                             .operator(operator)
                                             .build())
                                  .build())
                        .build());
    }
  }

}