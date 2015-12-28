package lunch.voting.core.services.exceptions;

/**
 *
 */
public class RoleExistsException extends RuntimeException {
    public RoleExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public RoleExistsException(String message) {
        super(message);
    }

    public RoleExistsException() {
        super();
    }
}
