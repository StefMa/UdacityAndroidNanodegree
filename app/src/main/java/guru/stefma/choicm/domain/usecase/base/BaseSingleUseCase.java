package guru.stefma.choicm.domain.usecase.base;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * A extension of {@link BaseUseCase} which will use {@link Single} as a result value.
 *
 * By default it uses the {@link Schedulers#io()} on the {@link Single#subscribeOn(Scheduler)}
 * and the {@link AndroidSchedulers#mainThread()} on the {@link Single#observeOn(Scheduler)}.
 * You can override these values by adding <b>different</b> {@link Scheduler} to the constructor
 * of this class...
 *
 * {@link #buildUseCase(Object)} will not be modified.
 *
 * @param <R> the result which will be wrapped into a {@link Single}
 * @param <P> describes the parameters for that UseCase
 */
public abstract class BaseSingleUseCase<R, P> implements BaseUseCase<Single<R>, P> {

    private final Scheduler mExecutionScheduler;

    private final Scheduler mPostExecutionScheduler;

    public BaseSingleUseCase(
            @Nullable Scheduler executionScheduler,
            @Nullable Scheduler postExecutionScheduler) {
        if (executionScheduler == null) {
            executionScheduler = Schedulers.io();
        }
        if (postExecutionScheduler == null) {
            postExecutionScheduler = AndroidSchedulers.mainThread();
        }

        mExecutionScheduler = executionScheduler;
        mPostExecutionScheduler = postExecutionScheduler;
    }

    @Override
    public Single<R> execute(@NonNull final P params) {
        return buildUseCase(params)
                .subscribeOn(mExecutionScheduler)
                .observeOn(mPostExecutionScheduler);
    }
}