package guru.stefma.baking.domain.usecase.base;

import android.support.annotation.NonNull;

/**
 * A UseCase which will provide two generics. A result R and a Param P.
 *
 * Could be used to make a UseCase fully customizable.
 *
 * @param <R> describes the result of that UseCase
 * @param <P> describes the parameters for that UseCase
 */
public interface BaseUseCase<R, P> {

    R buildUseCase(@NonNull final P params);

    R execute(@NonNull final P params);

}