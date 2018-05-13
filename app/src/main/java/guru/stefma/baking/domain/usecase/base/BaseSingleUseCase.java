package guru.stefma.baking.domain.usecase.base;

import android.support.annotation.NonNull;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * A extension of {@link BaseUseCase} which will use {@link Single} as a result value.
 *
 * @param <R> the result which will be wrapped in a {@link Single}
 * @param <P> describes the parameters for that UseCase
 */
public interface BaseSingleUseCase<R, P> extends BaseUseCase<Single<R>, P> {

    Scheduler executionScheduler = Schedulers.io();

    Scheduler postExecutionScheduler = AndroidSchedulers.mainThread();

    @Override
    default Single<R> execute(@NonNull final P params) {
        return buildUseCase(params)
                .subscribeOn(executionScheduler)
                .observeOn(postExecutionScheduler);
    }
}