package dev.springharvest.library.domains.books.services;

import dev.springharvest.library.domains.books.mappers.BookSearchMapper;
import dev.springharvest.library.domains.books.models.queries.BookFilterBO;
import dev.springharvest.library.domains.books.models.queries.BookFilterDTO;
import dev.springharvest.library.domains.books.models.queries.BookFilterRequestBO;
import dev.springharvest.library.domains.books.models.queries.BookFilterRequestDTO;
import dev.springharvest.library.domains.books.persistence.BookSearchRepository;
import dev.springharvest.library.domains.publishers.models.queries.PublisherFilterDTO;
import dev.springharvest.search.model.queries.parameters.filters.FilterParameterDTO;
import dev.springharvest.search.service.AbstractSearchService;
import dev.springharvest.shared.domains.books.mappers.IBookMapper;
import dev.springharvest.shared.domains.books.models.dtos.BookDTO;
import dev.springharvest.shared.domains.books.models.entities.BookEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class BookSearchService
        extends AbstractSearchService<BookDTO, BookEntity, UUID, BookFilterRequestDTO, BookFilterRequestBO,
        BookFilterDTO, BookFilterBO> {

    @Autowired
    protected BookSearchService(IBookMapper baseMapper, BookSearchMapper filterMapper,
                                BookSearchRepository searchRepository) {
        super(baseMapper, filterMapper, searchRepository);
    }

    @Override
    protected Set<BookFilterRequestDTO> buildUniqueFilters(BookDTO dto) {
        Set<BookFilterRequestDTO> filters = new HashSet<>();

        if (dto.getId() != null) {
            filters.add(BookFilterRequestDTO.builder()
                                            .book(BookFilterDTO.builder()
                                                               .id(FilterParameterDTO.builder()
                                                                                     .values(Set.of(dto.getId()))
                                                                                     .build())
                                                               .build())
                                            .publisher(PublisherFilterDTO.builder()
                                                                         .id(FilterParameterDTO.builder()
                                                                                               .values(Set.of(
                                                                                                       dto.getPublisher()
                                                                                                          .getId()))
                                                                                               .build())
                                                                         .build())
                                            .build());
        }

        return filters;
    }

}
