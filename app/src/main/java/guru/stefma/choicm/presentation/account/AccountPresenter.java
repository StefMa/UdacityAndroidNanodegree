package guru.stefma.choicm.presentation.account;

import android.support.annotation.NonNull;
import guru.stefma.choicm.domain.usecase.account.GetAccountUseCase;
import guru.stefma.choicm.domain.usecase.account.GetAccountUseCase.Params;
import guru.stefma.choicm.domain.usecase.account.SignOutUseCase;
import net.grandcentrix.thirtyinch.TiPresenter;
import net.grandcentrix.thirtyinch.rx2.RxTiPresenterDisposableHandler;
import timber.log.Timber;

class AccountPresenter extends TiPresenter<AccountView> {

    @NonNull
    private final RxTiPresenterDisposableHandler mDisposableHandler =
            new RxTiPresenterDisposableHandler(this);

    @NonNull
    private final GetAccountUseCase mGetAccountUseCase;

    @NonNull
    private final SignOutUseCase mSignOutUseCase;

    AccountPresenter(
            @NonNull final GetAccountUseCase accountUseCase,
            @NonNull final SignOutUseCase signOutUseCase) {
        mGetAccountUseCase = accountUseCase;
        mSignOutUseCase = signOutUseCase;
    }

    @Override
    protected void onAttachView(@NonNull final AccountView view) {
        super.onAttachView(view);

        mDisposableHandler.manageViewDisposables(
                mGetAccountUseCase.execute(new Params())
                        .subscribe(user -> sendToView((accountView) -> accountView.showUsername(user.getUsername())),
                                throwable -> {
                                    Timber.e("Can't get user...");
                                    sendToView(AccountView::showGetAccountError);
                                }));
    }

    /**
     * Is called if the user press the logout button
     */
    public void onLogoutClicked() {
        mDisposableHandler.manageViewDisposables(
                mSignOutUseCase.execute(new SignOutUseCase.Params())
                        .subscribe(() -> sendToView(AccountView::finishViewSuccessfully),
                                throwable -> {
                                    Timber.e(throwable, "Can't logout user...");
                                    sendToView(AccountView::showLogoutError);
                                }));
    }
}
