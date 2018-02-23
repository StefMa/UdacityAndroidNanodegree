package guru.stefma.popularmovies.domain.usecase.base;

import io.reactivex.Observable;

/**
 * A extension of {@link BaseUseCase} which will use {@link Observable} as a result value.
 *
 * @param <R> the result which will be wrapped in a {@link Observable}
 * @param <P> describes the parameters for that UseCase
 */
public interface BaseObservableUseCase<R, P> extends BaseUseCase<Observable<R>, P> {

}
