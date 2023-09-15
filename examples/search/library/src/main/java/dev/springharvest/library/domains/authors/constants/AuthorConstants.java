package dev.springharvest.library.domains.authors.constants;

import dev.springharvest.errors.constants.ExceptionMessages;

public class AuthorConstants {

    private AuthorConstants() {
        throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
    }

    public static class Controller {

        public static final String TAG = "Authors";
        public static final String DOMAIN_CONTEXT = "/library/authors";

        private Controller() {
            throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
        }

    }

}
