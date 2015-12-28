package lunch.voting.core.services.exceptions;

/**
 *
 */
public class RoleDoesNotExistException extends RuntimeException {
    public RoleDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public RoleDoesNotExistException(String message) {
        super(message);
    }

    public RoleDoesNotExistException() {
        super();
    }
}
