package guru.stefma.choicm.domain.usecase.account;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import guru.stefma.choicm.auth.Authentication;
import guru.stefma.choicm.auth.model.User;
import guru.stefma.choicm.domain.usecase.account.GetAccountUseCase.Params;
import guru.stefma.choicm.domain.usecase.base.BaseSingleUseCase;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import javax.inject.Inject;

public class GetAccountUseCase extends BaseSingleUseCase<User, Params> {

    public static class Params {

    }

    @Inject
    Authentication mAuthentication;

    public GetAccountUseCase(
            @Nullable final Scheduler executionScheduler,
            @Nullable final Scheduler postExecutionScheduler) {
        super(executionScheduler, postExecutionScheduler);
    }

    @Override
    public Single<User> buildUseCase(@NonNull final Params params) {
        return mAuthentication.getUser();
    }
}
