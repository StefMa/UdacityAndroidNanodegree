package guru.stefma.choicm.auth.exception;

/**
 * An {@link Exception} which will be thrown if the user not not logged in.
 */
public class UserNotLoggedInException extends Exception {

    public UserNotLoggedInException() {
        super("User is not logged in");
    }
}
