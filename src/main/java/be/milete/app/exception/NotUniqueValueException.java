package be.milete.app.exception;


/**
 * Exception that indicates that a value - that has to be unique - was already in use.
 */
public class NotUniqueValueException extends RuntimeException {

    /**
     * Constructor
     * @param message should not contain sensitive information
     */
    public NotUniqueValueException(String message) {
        super(message);
    }

}
