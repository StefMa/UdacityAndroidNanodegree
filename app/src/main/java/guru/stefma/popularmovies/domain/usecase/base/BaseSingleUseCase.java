package guru.stefma.popularmovies.domain.usecase.base;

import io.reactivex.Single;

/**
 * A extension of {@link BaseUseCase} which will use {@link Single} as a result value.
 *
 * @param <R> the result which will be wrapped in a {@link Single}
 * @param <P> describes the parameters for that UseCase
 */
public interface BaseSingleUseCase<R, P> extends BaseUseCase<Single<R>, P> {

}