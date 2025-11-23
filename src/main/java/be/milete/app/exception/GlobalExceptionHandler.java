package be.milete.app.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

/**
 * Class responsible for handling exceptions for multiple controllers.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handler for exceptions that are not handled by other methods.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception e){
        ErrorResponse responseBody = new ErrorResponse("Unexpected error");
        logger.error("Exception occurred", e);
        return new ResponseEntity<>(responseBody, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e){
        ErrorResponse responseBody = new ErrorResponse("HTTP message not readable");
        logger.warn("HTTP message not readable", e);
        return new ResponseEntity<>(responseBody, BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException e){
        ErrorResponse responseBody = new ErrorResponse(e.getMessage());
        logger.warn("Resource not found", e);
        return new ResponseEntity<>(responseBody, NOT_FOUND);
    }

    @ExceptionHandler(NotUniqueValueException.class)
    public ResponseEntity<ErrorResponse> handleNotUniqueValueException(NotUniqueValueException e){
        ErrorResponse responseBody = new ErrorResponse(e.getMessage());
        logger.warn("Value not unique", e);
        return new ResponseEntity<>(responseBody, CONFLICT);
    }
}
