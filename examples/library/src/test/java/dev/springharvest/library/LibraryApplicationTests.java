package dev.springharvest.library;

import dev.springharvest.library.constants.TestConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LibraryApplicationTests {

    @Test
    void contextLoads() {
        Assertions.assertTrue(true, TestConstants.Messages.CONTEXT_LOADS);
    }

}
