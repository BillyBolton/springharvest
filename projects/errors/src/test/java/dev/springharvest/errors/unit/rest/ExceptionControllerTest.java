package dev.springharvest.errors.unit.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dev.springharvest.errors.models.ClientException;
import dev.springharvest.errors.rest.ExceptionController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class ExceptionControllerTest {

  @InjectMocks
  private ExceptionController exceptionController;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Should return correct response when ClientException is thrown")
  void shouldReturnCorrectResponseWhenClientExceptionIsThrown() {
    ClientException exception = ClientException.builder().message("Test exception").status(HttpStatus.NOT_FOUND).build();
    ResponseEntity<ClientException> response = exceptionController.resourceNotFound(exception);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Test exception", response.getBody().getMessage());
  }

  @Test
  @DisplayName("Should return correct status code when ClientException is thrown")
  void shouldReturnCorrectStatusCodeWhenClientExceptionIsThrown() {
    ClientException exception = ClientException.builder().message("Test exception").status(HttpStatus.BAD_REQUEST).build();
    ResponseEntity<ClientException> response = exceptionController.resourceNotFound(exception);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }
}