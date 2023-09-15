package dev.springharvest.library.domains.books.models.entities;

import dev.springharvest.errors.constants.ExceptionMessages;
import dev.springharvest.search.model.entities.IEntityMetadata;
import dev.springharvest.shared.domains.books.models.entities.BookEntity;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;

@Component
public class BookEntityMetadata implements IEntityMetadata<BookEntity> {

    @Override
    public String getDomainName() {
        return Paths.BOOK;
    }

    @Override
    public String getDomainName(boolean isPlural) {
        return isPlural ? Paths.BOOKS : getDomainName();
    }

    @Override
    public Class<?> getClazz(String path) {
        switch (path) {
            case Paths.BOOK_ID -> {
                return UUID.class;
            }
            case Paths.BOOK_TITLE -> {
                return String.class;
            }
            default -> {
                return null;
            }
        }
    }

    @Override
    public Set<String> getRootPaths() {
        return Set.of(Paths.BOOK);
    }

    @Override
    public Set<String> getNestedPaths() {
        return Set.of();
    }

    public static class Paths {

        public static final String BOOKS = "books";

        public static final String BOOK = "book";

        public static final String BOOK_ID = BOOK + ".id";
        public static final String BOOK_TITLE = BOOK + ".title";

        private Paths() {
            throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
        }

    }

}
