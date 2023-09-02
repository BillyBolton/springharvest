package ca.springharvest.errors.rest;

import ca.springharvest.errors.models.ClientException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * > This class is a controller that handles exceptions
 *
 * @author Billy Bolton
 * @link <a href =
 * "https://www.baeldung.com/exception-handling-for-rest-with-spring#controlleradvice">https://www.baeldung
 * .com/exception-handling-for-rest-with-spring#controlleradvice</a>
 * @since 1.0
 */
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = {ClientException.class})
    public ResponseEntity<ClientException> resourceNotFound(ClientException exception) {
        return ResponseEntity.status(exception.getStatus()).body(exception);
    }

}
