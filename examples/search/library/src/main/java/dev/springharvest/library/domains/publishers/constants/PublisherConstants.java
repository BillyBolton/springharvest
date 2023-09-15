package dev.springharvest.library.domains.publishers.constants;

import dev.springharvest.errors.constants.ExceptionMessages;

public class PublisherConstants {

    private PublisherConstants() {
        throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
    }

    public static class Controller {

        public static final String TAG = "Publishers";
        public static final String DOMAIN_CONTEXT = "/library/publishers";

        private Controller() {
            throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
        }

    }

}
