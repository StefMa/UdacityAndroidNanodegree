package guru.stefma.popularmovies.domain.usecase.base;

import io.reactivex.Completable;

/**
 * A extension of {@link BaseUseCase} which will use {@link Completable} as a result value.
 *
 * @param <P> describes the parameters for that UseCase
 */
public interface BaseCompletableUseCase<P> extends BaseUseCase<Completable, P> {

}
