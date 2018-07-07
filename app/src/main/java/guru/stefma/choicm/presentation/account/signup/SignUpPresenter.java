package guru.stefma.choicm.presentation.account.signup;

import android.support.annotation.NonNull;
import guru.stefma.choicm.domain.usecase.account.SignUpUseCase;
import guru.stefma.choicm.domain.usecase.account.SignUpUseCase.Params;
import guru.stefma.choicm.presentation.base.loading.LoadingView;
import java.util.Objects;
import net.grandcentrix.thirtyinch.TiPresenter;
import net.grandcentrix.thirtyinch.rx2.RxTiPresenterDisposableHandler;
import timber.log.Timber;

class SignUpPresenter extends TiPresenter<SignUpView> {

    @NonNull
    private final RxTiPresenterDisposableHandler mDisposableHandler =
            new RxTiPresenterDisposableHandler(this);

    @NonNull
    private final SignUpUseCase mSingUpUseCase;

    SignUpPresenter(@NonNull final SignUpUseCase signUpUseCase) {
        mSingUpUseCase = signUpUseCase;
    }

    /**
     * Is called if the singup button get clicked
     *
     * @param username the username or <b>empty</b> if not set
     * @param email    the email or <b>empty</b> if not set
     * @param password the password or <b>empty</b> if not set
     */
    public void onSignUpClicked(
            @NonNull final String username,
            @NonNull final String email,
            @NonNull final String password) {
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            // TODO: Show error in view that the views are empty...
            return;
        }

        mDisposableHandler.manageViewDisposables(
                mSingUpUseCase.execute(new Params(email, username, password))
                        .doOnSubscribe(ignore -> sendToView(LoadingView::showLoading))
                        .doOnEvent((ignore, ignored) -> sendToView(LoadingView::hideLoading))
                        .subscribe(user -> Objects.requireNonNull(getView()).finishViewSuccessfully(),
                                throwable -> {
                                    Timber.e(throwable, "There was error while trying to register a user");
                                    Objects.requireNonNull(getView()).showRegisterError();
                                }));
    }
}
