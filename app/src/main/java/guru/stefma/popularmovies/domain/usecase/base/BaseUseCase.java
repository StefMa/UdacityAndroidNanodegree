package guru.stefma.popularmovies.domain.usecase.base;

import android.support.annotation.NonNull;

/**
 * A UseCase which will provide two generics. A result and a P.
 *
 * Could be used to make a UseCase fully customizable.
 *
 * @param <R> describes the result of that UseCase
 * @param <P> describes the parameters for that UseCase
 */
public interface BaseUseCase<R, P> {

    R execute(@NonNull final P params);

}
