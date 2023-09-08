package dev.springharvest.library.books.constants;

import dev.springharvest.errors.constants.ExceptionMessages;

public class BookConstants {

    private BookConstants() {
        throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
    }

    public static class Controller {
        public static final String TAG = "Books";
        public static final String DOMAIN_CONTEXT = "/library/books";

        private Controller() {
            throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
        }
    }

    public static class Paths {
        public static final String BOOK = "Book";
        public static final String BOOKS = "Books";

        private Paths() {
            throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
        }
    }

}
