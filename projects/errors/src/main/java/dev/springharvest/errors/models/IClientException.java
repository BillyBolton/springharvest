package dev.springharvest.errors.models;

import java.util.List;
import java.util.Locale;
import org.springframework.http.HttpStatus;

/**
 * This interface is used to represent the client exception. Client exceptions are acceptable to be sent to a consumer, such as a front end client, to be
 * displayed to the user.
 */
public interface IClientException {


  /**
   * This method is used to get the status code of the exception.
   *
   * @return The integer of the http status code of the exception.
   * @see HttpStatus
   */
  int getStatusCode();

  /**
   * This method is used to get the http status of the exception.
   *
   * @return The http status of the exception.
   * @see HttpStatus
   */
  HttpStatus getStatus();

  /**
   * This method is used to get the cause of the exception.
   *
   * @return The cause of the exception.
   * @see Throwable
   */
  Throwable getCause();

  /**
   * This method is used to get the stack trace of the exception.
   *
   * @return The stack trace of the exception.
   * @see Throwable
   */
  Throwable[] getSuppressed();


  /**
   * This method is used to get the message of the exception.
   *
   * @return The message of the exception.
   * @see String
   */
  String getMessage();

  /**
   * This method is used to get the localized message of the exception.
   *
   * @return The localized message of the exception.
   * @see String
   */
  String getLocalizedMessage();

  /**
   * This method is used to get the locale of the exception.
   *
   * @return The locale of the exception.
   * @see Locale
   */
  Locale getLocale();

  /**
   * This method is used to get the details of the exception.
   *
   * @return A list of ExceptionDetail that contains the details of the exception.
   * @see ExceptionDetail
   */
  List<ExceptionDetail> getDetails();

}