package dev.springharvest.library.domains.books.rest;


import dev.springharvest.library.domains.books.constants.BookConstants;
import dev.springharvest.library.domains.books.mappers.IBookMapper;
import dev.springharvest.library.domains.books.models.dtos.BookDTO;
import dev.springharvest.library.domains.books.models.entities.BookEntity;
import dev.springharvest.library.domains.books.models.queries.BookFilterBO;
import dev.springharvest.library.domains.books.models.queries.BookFilterDTO;
import dev.springharvest.library.domains.books.models.queries.BookFilterRequestBO;
import dev.springharvest.library.domains.books.models.queries.BookFilterRequestDTO;
import dev.springharvest.library.domains.books.services.BookSearchService;
import dev.springharvest.search.domains.base.rest.AbstractSearchController;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Tag(name = BookConstants.Controller.TAG)
@RequestMapping(BookConstants.Controller.DOMAIN_CONTEXT)
public class BookSearchControllerImpl
    extends AbstractSearchController<BookDTO, BookEntity, UUID, BookFilterRequestDTO, BookFilterRequestBO,
    BookFilterDTO, BookFilterBO> {

  protected BookSearchControllerImpl(IBookMapper baseModelMapper, BookSearchService baseService) {
    super(baseModelMapper, baseService);
  }

}

