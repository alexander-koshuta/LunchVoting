package lunch.voting.core.services.exceptions;

/**
 *
 */
public class AlreadyVotedException extends RuntimeException {
    public AlreadyVotedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyVotedException(String message) {
        super(message);
    }

    public AlreadyVotedException() {
        super("You have already voted. You can vote only once a day.");
    }
}
