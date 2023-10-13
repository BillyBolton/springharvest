package dev.springharvest.library.domains.books.mappers.search;


import dev.springharvest.library.domains.books.models.entities.BookEntity;
import dev.springharvest.library.domains.books.models.entities.BookEntityMetadata;
import dev.springharvest.library.domains.books.models.queries.BookFilterBO;
import dev.springharvest.library.domains.books.models.queries.BookFilterDTO;
import dev.springharvest.library.domains.books.models.queries.BookFilterRequestBO;
import dev.springharvest.library.domains.books.models.queries.BookFilterRequestDTO;
import dev.springharvest.library.global.GlobalClazzResolver;
import dev.springharvest.search.domains.base.mappers.queries.ISearchMapper;
import java.util.UUID;
import lombok.Getter;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public abstract class BookSearchMapper
    implements ISearchMapper<BookEntity, UUID, BookFilterRequestDTO, BookFilterRequestBO, BookFilterDTO, BookFilterBO> {

  @Autowired
  private GlobalClazzResolver globalClazzResolver;

  @Autowired
  private BookEntityMetadata entityMetadata;

}
