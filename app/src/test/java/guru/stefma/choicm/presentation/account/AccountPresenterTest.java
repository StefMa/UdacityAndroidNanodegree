package guru.stefma.choicm.presentation.account;

import static org.mockito.Mockito.*;

import guru.stefma.choicm.auth.model.User;
import guru.stefma.choicm.domain.usecase.account.GetAccountUseCase;
import guru.stefma.choicm.domain.usecase.account.SignOutUseCase;
import io.reactivex.Completable;
import io.reactivex.Single;
import net.grandcentrix.thirtyinch.test.TiTestPresenter;
import org.junit.*;
import org.junit.runner.*;
import org.junit.runners.*;

@RunWith(JUnit4.class)
public class AccountPresenterTest {

    private final GetAccountUseCase mockGetAccount = mock(GetAccountUseCase.class);

    private final SignOutUseCase mockSignOut = mock(SignOutUseCase.class);

    private final AccountView mockView = mock(AccountView.class);

    private final AccountPresenter accountPresenter = new AccountPresenter(mockGetAccount, mockSignOut);

    private final TiTestPresenter<AccountView> testPresenter = accountPresenter.test();

    @Test
    public void testAttachView_GetUserSuccessfully_ShouldShowUsername() {
        when(mockGetAccount.execute(any())).thenReturn(Single.just(new User("uid", "name")));
        testPresenter.create();

        testPresenter.attachView(mockView);

        verify(mockView).showUsername("name");
    }

    @Test
    public void testAttachView_GetUserWithError_ShouldShowError() {
        when(mockGetAccount.execute(any())).thenReturn(Single.error(new Throwable()));
        testPresenter.create();

        testPresenter.attachView(mockView);

        verify(mockView).showGetAccountError();
    }

    @Test
    public void testOnSignOutClicked_ShouldLogoutSuccessfully() {
        when(mockGetAccount.execute(any())).thenReturn(Single.error(new Throwable()));
        when(mockSignOut.execute(any())).thenReturn(Completable.complete());
        testPresenter.create();
        testPresenter.attachView(mockView);

        accountPresenter.onLogoutClicked();

        verify(mockView).finishViewSuccessfully();
    }


    @Test
    public void testOnSignOutClicked_WithError_ShouldShowError() {
        when(mockGetAccount.execute(any())).thenReturn(Single.error(new Throwable()));
        when(mockSignOut.execute(any())).thenReturn(Completable.error(new Throwable()));
        testPresenter.create();
        testPresenter.attachView(mockView);

        accountPresenter.onLogoutClicked();

        verify(mockView).showLogoutError();
    }
}