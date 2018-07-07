package guru.stefma.choicm.domain.usecase.account;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import guru.stefma.choicm.auth.Authentication;
import guru.stefma.choicm.auth.model.User;
import guru.stefma.choicm.domain.usecase.account.SignInUseCase.Params;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;
import org.junit.*;
import org.junit.runner.*;
import org.junit.runners.*;

@RunWith(JUnit4.class)
public class SignInUseCaseTest {

    private final TestScheduler mTestScheduler = new TestScheduler();

    private final Authentication mMockAuth = mock(Authentication.class);

    @Test
    public void testSignInSuccessfully_ShouldReturnUser() {
        final SignInUseCase useCase = new SignInUseCase(mTestScheduler, mTestScheduler);
        useCase.mAuthentication = mMockAuth;
        when(mMockAuth.signIn(any(), any())).thenReturn(Single.just(new User("uid", "name")));

        final TestObserver<User> testObserver = useCase.execute(new Params("", "")).test();

        mTestScheduler.triggerActions();
        testObserver.assertNoErrors();
        testObserver.assertComplete();
        final User user = testObserver.values().get(0);
        assertThat(user).isNotNull();
        assertThat(user.getUid()).isEqualTo("uid");
        assertThat(user.getUsername()).isEqualTo("name");
    }
}