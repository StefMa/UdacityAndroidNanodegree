package guru.stefma.choicm.domain.usecase.account;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import guru.stefma.choicm.auth.Authentication;
import guru.stefma.choicm.auth.model.User;
import guru.stefma.choicm.domain.usecase.account.SignUpUseCase.Params;
import guru.stefma.choicm.domain.usecase.base.BaseSingleUseCase;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import javax.inject.Inject;

public class SignUpUseCase extends BaseSingleUseCase<User, Params> {

    public static class Params {

        @NonNull
        private String mEmail;

        @NonNull
        private String mUsername;

        @NonNull
        private String mPassword;

        public Params(@NonNull final String email, @NonNull final String username, @NonNull final String password) {
            mEmail = email;
            mUsername = username;
            mPassword = password;
        }
    }

    @Inject
    Authentication mAuthentication;

    public SignUpUseCase(
            @Nullable final Scheduler executionScheduler,
            @Nullable final Scheduler postExecutionScheduler) {
        super(executionScheduler, postExecutionScheduler);
    }

    @Override
    public Single<User> buildUseCase(@NonNull final Params params) {
        return mAuthentication.signUp(params.mUsername, params.mEmail, params.mPassword);
    }
}
