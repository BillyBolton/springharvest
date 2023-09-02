package ca.springharvest.errors.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;

/**
 * This class is used to represent the client exception. Client exceptions are acceptable to be sent to a consumer, such
 * as a front end client, to be displayed to the user.
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class ClientException extends RuntimeException implements IClientException, Serializable {

    @Serial
    private static final long serialVersionUID = 6754950803957667906L;


    @Builder.Default
    private final int statusCode = 500;
    @Builder.Default
    private final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    private final Throwable cause;

    private final String message;

    @Builder.Default
    private final List<ExceptionDetail> details = List.of();

    @JsonIgnore
    @Builder.Default
    private final boolean fillInStackTrace = false;

    @JsonIgnore
    @Builder.Default
    private final Locale locale = Locale.getDefault();

    @JsonIgnore
    private final String localizedMessage;


    @Override
    @JsonIgnore
    public synchronized Throwable fillInStackTrace() {
        return fillInStackTrace ? super.fillInStackTrace() : null;
    }

}