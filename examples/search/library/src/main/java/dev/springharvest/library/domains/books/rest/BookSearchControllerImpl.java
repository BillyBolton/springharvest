package dev.springharvest.library.domains.books.rest;


import dev.springharvest.library.domains.books.models.queries.BookFilterBO;
import dev.springharvest.library.domains.books.models.queries.BookFilterDTO;
import dev.springharvest.library.domains.books.models.queries.BookFilterRequestBO;
import dev.springharvest.library.domains.books.models.queries.BookFilterRequestDTO;
import dev.springharvest.library.domains.books.services.BookSearchService;
import dev.springharvest.search.rest.AbstractBaseSearchController;
import dev.springharvest.shared.domains.books.constants.BookConstants;
import dev.springharvest.shared.domains.books.models.dtos.BookDTO;
import dev.springharvest.shared.domains.books.models.entities.BookEntity;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@Tag(name = BookConstants.Controller.TAG)
@RequestMapping(BookConstants.Controller.DOMAIN_CONTEXT)
public class BookSearchControllerImpl
        extends AbstractBaseSearchController<BookDTO, BookEntity, UUID, BookFilterRequestDTO, BookFilterRequestBO,
        BookFilterDTO, BookFilterBO> {

    protected BookSearchControllerImpl(BookSearchService baseService) {
        super(baseService);
    }

}

