package dev.springharvest.crud.unit.rest.constants;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.springharvest.crud.domains.base.rest.constants.CrudControllerUri;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

class CrudControllerUriTest {

  @Test
  void testCrudControllerUriPrivateConstructor() throws Exception {
    testPrivateConstructor(CrudControllerUri.class);
  }

  private void testPrivateConstructor(Class<?> clazz) throws Exception {
    Constructor<?> constructor = clazz.getDeclaredConstructor();
    assertTrue(Modifier.isPrivate(constructor.getModifiers()));

    constructor.setAccessible(true);

    InvocationTargetException exception = assertThrows(InvocationTargetException.class, constructor::newInstance);
    assertEquals(UnsupportedOperationException.class, exception.getCause().getClass());
    assertEquals("This is a utility class and cannot be instantiated", exception.getCause().getMessage());
  }

  @Test
  void testControllerContextsPrivateConstructor() throws Exception {
    Class<?> controllerContextsClass = Arrays.stream(CrudControllerUri.class.getDeclaredClasses())
        .filter(clazz -> "ControllerContexts".equals(clazz.getSimpleName()))
        .findFirst()
        .orElseThrow(() -> new ClassNotFoundException("ControllerContexts class not found"));

    testPrivateConstructor(controllerContextsClass);
  }

  @Test
  void testControllerParametersPrivateConstructor() throws Exception {
    testPrivateConstructor(CrudControllerUri.ControllerParameters.class);
  }
}