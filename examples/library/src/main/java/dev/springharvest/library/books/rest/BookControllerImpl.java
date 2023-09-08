package dev.springharvest.library.books.rest;

import dev.springharvest.crud.rest.AbstractBaseCrudController;
import dev.springharvest.library.books.constants.BookConstants;
import dev.springharvest.library.books.models.dtos.BookDTO;
import dev.springharvest.library.books.models.entities.BookEntity;
import dev.springharvest.library.books.services.BookService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@Tag(name = BookConstants.Controller.TAG)
@RequestMapping(BookConstants.Controller.DOMAIN_CONTEXT)
public class BookControllerImpl extends AbstractBaseCrudController<BookDTO, BookEntity, UUID> {

    protected BookControllerImpl(BookService baseService) {
        super(baseService);
    }

}
