package dev.springharvest.errors.unit.constants;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import dev.springharvest.errors.constants.ExceptionMessages;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class PrivateConstructorTest {

  private static Stream<Class<?>> provideClassesForTesting() {
    return Stream.of(ExceptionMessages.class
                    );
  }

  @ParameterizedTest
  @MethodSource("provideClassesForTesting")
  void testPrivateConstructors(Class<?> clazz) throws NoSuchMethodException {
    String expectedMessage = "This is a utility class and cannot be instantiated";

    // Get the constructor
    Constructor<?> constructor = clazz.getDeclaredConstructor();
    // Try to make it accessible
    constructor.setAccessible(true);

    // Assert that an exception of type UnsupportedOperationException is thrown
    // when we try to create an instance, and that the message is correct
    InvocationTargetException thrown = assertThrows(InvocationTargetException.class, constructor::newInstance);
    assertEquals(expectedMessage, thrown.getTargetException().getMessage());
  }

}
