package be.milete.app.exception;

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

    /**
     * Handler for exceptions that are not handled by other methods.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception e){
        ErrorResponse responseBody = new ErrorResponse("Unexpected error");
        //todo: log the exception
        return new ResponseEntity<>(responseBody, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(){
        ErrorResponse responseBody = new ErrorResponse("HTTP message not readable");
        //todo: log the exception
        return new ResponseEntity<>(responseBody, BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException e){
        ErrorResponse responseBody = new ErrorResponse(e.getMessage());
        //todo: log the exception
        return new ResponseEntity<>(responseBody, NOT_FOUND);
    }

    @ExceptionHandler(NotUniqueValueException.class)
    public ResponseEntity<ErrorResponse> handleNotUniqueValueException(NotUniqueValueException e){
        ErrorResponse responseBody = new ErrorResponse(e.getMessage());
        //todo: log the exception
        return new ResponseEntity<>(responseBody, CONFLICT);
    }
}
