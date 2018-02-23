package guru.stefma.popularmovies.domain.usecase;

/**
 * A {@link RuntimeException} which will be thrown if the last page have reached.
 */
public class LastPageReachedException extends RuntimeException {

    public LastPageReachedException() {
        super("Last page reached");
    }
}
