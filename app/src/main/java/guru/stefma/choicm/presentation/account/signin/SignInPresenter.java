package guru.stefma.choicm.presentation.account.signin;

import android.support.annotation.NonNull;
import guru.stefma.choicm.domain.usecase.account.SignInUseCase;
import guru.stefma.choicm.domain.usecase.account.SignInUseCase.Params;
import guru.stefma.choicm.presentation.base.loading.LoadingView;
import java.util.Objects;
import net.grandcentrix.thirtyinch.TiPresenter;
import net.grandcentrix.thirtyinch.rx2.RxTiPresenterDisposableHandler;
import timber.log.Timber;

class SignInPresenter extends TiPresenter<SignInView> {

    @NonNull
    private final RxTiPresenterDisposableHandler mDisposableHandler =
            new RxTiPresenterDisposableHandler(this);

    @NonNull
    private final SignInUseCase mSingInUseCase;

    SignInPresenter(@NonNull final SignInUseCase signInUseCase) {
        mSingInUseCase = signInUseCase;
    }

    /**
     * Is called if the signin button get clicked
     *
     * @param email    the email or <b>empty</b> if not set
     * @param password the password or <b>empty</b> if not set
     */
    public void onSignInClicked(@NonNull final String email, @NonNull final String password) {
        if (email.isEmpty() || password.isEmpty()) {
            // TODO: Show error in view that the views are empty...
            return;
        }

        mDisposableHandler.manageViewDisposable(
                mSingInUseCase.buildUseCase(new Params(email, password))
                        .doOnSubscribe(ignore -> sendToView(LoadingView::showLoading))
                        .doOnEvent((ignore, ignored) -> sendToView(LoadingView::hideLoading))
                        .subscribe(user -> Objects.requireNonNull(getView()).finishViewSuccessfully(),
                                throwable -> {
                                    Timber.e(throwable, "There was error while trying to sign up the user %s", email);
                                    Objects.requireNonNull(getView()).showLoginError();
                                }));
    }
}
