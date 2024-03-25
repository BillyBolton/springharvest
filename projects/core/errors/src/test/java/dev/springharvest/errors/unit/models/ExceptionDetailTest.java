package dev.springharvest.errors.unit.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dev.springharvest.errors.models.ExceptionDetail;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ExceptionDetailTest {

  @Test
  @DisplayName("Should create ExceptionDetail with correct field and message")
  void shouldCreateExceptionDetailWithCorrectFieldAndMessage() {
    ExceptionDetail exceptionDetail = ExceptionDetail.builder()
        .field("Test Field")
        .message("Test Message")
        .build();

    assertEquals("Test Field", exceptionDetail.field());
    assertEquals("Test Message", exceptionDetail.message());
  }

  @Test
  @DisplayName("Should create ExceptionDetail with empty field and message")
  void shouldCreateExceptionDetailWithEmptyFieldAndMessage() {
    ExceptionDetail exceptionDetail = ExceptionDetail.builder()
        .field("")
        .message("")
        .build();

    assertEquals("", exceptionDetail.field());
    assertEquals("", exceptionDetail.message());
  }
}