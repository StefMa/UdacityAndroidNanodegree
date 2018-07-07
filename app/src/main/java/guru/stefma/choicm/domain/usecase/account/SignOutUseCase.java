package guru.stefma.choicm.domain.usecase.account;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import guru.stefma.choicm.auth.Authentication;
import guru.stefma.choicm.domain.usecase.account.SignOutUseCase.Params;
import guru.stefma.choicm.domain.usecase.base.BaseCompletableUseCase;
import io.reactivex.Completable;
import io.reactivex.Scheduler;
import javax.inject.Inject;

public class SignOutUseCase extends BaseCompletableUseCase<Params> {

    public static class Params {

    }

    @Inject
    Authentication mAuthentication;

    public SignOutUseCase(
            @Nullable final Scheduler executionScheduler,
            @Nullable final Scheduler postExecutionScheduler) {
        super(executionScheduler, postExecutionScheduler);
    }

    @Override
    public Completable buildUseCase(@NonNull final Params params) {
        return mAuthentication.signOut();
    }
}
