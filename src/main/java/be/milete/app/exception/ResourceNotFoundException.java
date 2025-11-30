package be.milete.app.exception;

/**
 * General exception to indicate that a resource was not found
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * Creates ResourceNotFoundException with message in format: "... not found".
     * @param name of the thing that is not found
     * @return the ResourceNotFoundException
     */
    public static ResourceNotFoundException fromName(String name){
        return new ResourceNotFoundException(name + " not found");
    }
}
