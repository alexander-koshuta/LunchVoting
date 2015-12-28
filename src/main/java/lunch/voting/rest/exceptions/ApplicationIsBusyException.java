package lunch.voting.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This exception is thrown whenever we are unable to complete a user request in time.
 */
@ResponseStatus(value= HttpStatus.REQUEST_TIMEOUT)
public class ApplicationIsBusyException extends RuntimeException {
    public ApplicationIsBusyException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationIsBusyException(String message) {
        super(message);
    }

    public ApplicationIsBusyException() {
        super("We were unable to complete your request. Please try again later.");
    }
}
