package dev.springharvest.library.domains.books.rest;

import dev.springharvest.crud.domains.base.rest.AbstractCrudController;
import dev.springharvest.library.domains.books.constants.BookConstants;
import dev.springharvest.library.domains.books.mappers.IBookMapper;
import dev.springharvest.library.domains.books.models.dtos.BookDTO;
import dev.springharvest.library.domains.books.models.entities.BookEntity;
import dev.springharvest.library.domains.books.services.BookCrudService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Tag(name = BookConstants.Controller.TAG)
@RequestMapping(BookConstants.Controller.DOMAIN_CONTEXT)
public class BookCrudControllerImpl extends AbstractCrudController<BookDTO, BookEntity, UUID> {

  protected BookCrudControllerImpl(IBookMapper baseModelMapper, BookCrudService baseService) {
    super(baseModelMapper, baseService);
  }

}
