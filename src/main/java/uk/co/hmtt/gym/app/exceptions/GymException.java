package uk.co.hmtt.gym.app.exceptions;

public class GymException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public GymException(String message) {
        super(message);
    }

    public GymException(Throwable cause) {
        super(cause);
    }
}
