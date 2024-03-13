package dev.springharvest.errors.unit.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.springharvest.errors.models.ClientException;
import dev.springharvest.errors.models.ExceptionDetail;
import java.util.List;
import java.util.Locale;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class ClientExceptionTest {

  @Test
  @DisplayName("Should create ClientException with default values")
  public void shouldCreateClientExceptionWithDefaultValues() {
    ClientException exception = ClientException.builder().build();

    assertEquals(500, exception.getStatusCode());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
    assertNull(exception.getCause());
    assertNull(exception.getMessage());
    assertEquals(List.of(), exception.getDetails());
    assertFalse(exception.isFillInStackTrace());
    assertEquals(Locale.getDefault(), exception.getLocale());
    assertNull(exception.getLocalizedMessage());
  }

  @Test
  @DisplayName("Should create ClientException with provided values")
  public void shouldCreateClientExceptionWithProvidedValues() {
    ExceptionDetail detail = ExceptionDetail.builder()
        .field("Test Field")
        .message("Test Message")
        .build();
    ClientException exception = ClientException.builder()
        .statusCode(404)
        .status(HttpStatus.NOT_FOUND)
        .cause(new RuntimeException("Cause"))
        .message("Test exception")
        .details(List.of(detail))
        .fillInStackTrace(true)
        .locale(Locale.CANADA)
        .localizedMessage("Localized Test exception")
        .build();

    assertEquals(404, exception.getStatusCode());
    assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    assertEquals("Cause", exception.getCause().getMessage());
    assertEquals("Test exception", exception.getMessage());
    assertEquals(List.of(detail), exception.getDetails());
    assertTrue(exception.isFillInStackTrace());
    assertEquals(Locale.CANADA, exception.getLocale());
    assertEquals("Localized Test exception", exception.getLocalizedMessage());
  }
}