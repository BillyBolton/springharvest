package dev.springharvest.testing.constants;


import dev.springharvest.errors.constants.ExceptionMessages;

public class TestConstants {

    private TestConstants() {
        throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
    }

    public static class Messages {

        public static final String CONTEXT_LOADS = "Context loads";

        private Messages() {
            throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
        }

    }

}
