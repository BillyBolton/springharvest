package dev.springharvest.library.domains.books.services;

import dev.springharvest.library.domains.books.mappers.IBookMapper;
import dev.springharvest.library.domains.books.mappers.search.BookSearchMapper;
import dev.springharvest.library.domains.books.models.entities.BookEntity;
import dev.springharvest.library.domains.books.models.queries.BookFilterBO;
import dev.springharvest.library.domains.books.models.queries.BookFilterDTO;
import dev.springharvest.library.domains.books.models.queries.BookFilterRequestBO;
import dev.springharvest.library.domains.books.models.queries.BookFilterRequestDTO;
import dev.springharvest.library.domains.books.persistence.BookSearchRepository;
import dev.springharvest.library.domains.publishers.models.queries.PublisherFilterDTO;
import dev.springharvest.search.model.queries.parameters.filters.FilterParameterDTO;
import dev.springharvest.search.service.AbstractSearchService;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookSearchService
    extends AbstractSearchService<BookEntity, UUID, BookFilterRequestDTO, BookFilterRequestBO, BookFilterDTO,
    BookFilterBO> {

  @Autowired
  protected BookSearchService(IBookMapper baseMapper, BookSearchMapper filterMapper,
                              BookSearchRepository searchRepository) {
    super(filterMapper, searchRepository);
  }

  @Override
  protected Set<BookFilterRequestDTO> buildUniqueFilters(BookEntity entity) {
    Set<BookFilterRequestDTO> filters = new HashSet<>();

    if (ObjectUtils.isNotEmpty(entity.getId())) {
      filters.add(BookFilterRequestDTO.builder()
                      .book(BookFilterDTO.builder()
                                .id(FilterParameterDTO.builder()
                                        .values(Set.of(entity.getId()))
                                        .build())
                                .build())
                      .publisher(PublisherFilterDTO.builder()
                                     .id(FilterParameterDTO.builder()
                                             .values(Set.of(
                                                 entity.getPublisher()
                                                     .getId()))
                                             .build())
                                     .build())
                      .build());
    }

    return filters;
  }

}