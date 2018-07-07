package guru.stefma.choicm.domain.usecase.base;

import android.support.annotation.NonNull;

/**
 * A UseCase which will provide two generics. A result and a parameter.
 *
 * Could be used to make a UseCase fully customizable.
 *
 * Use {@link #buildUseCase(Object)} to build the UseCase without modification.
 * Call {@link #execute(Object)} to get a "modified version" of the UseCase.
 *
 * @param <R> describes the result of that UseCase
 * @param <P> describes the parameters for that UseCase
 */
public interface BaseUseCase<R, P> {

    R buildUseCase(@NonNull final P params);

    default R execute(@NonNull final P params) {
        return buildUseCase(params);
    }

}
