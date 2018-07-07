package guru.stefma.choicm.auth;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import android.support.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import guru.stefma.choicm.auth.exception.UserNotLoggedInException;
import guru.stefma.choicm.auth.model.User;
import io.reactivex.observers.TestObserver;
import org.junit.*;
import org.junit.runner.*;
import org.junit.runners.*;

@RunWith(JUnit4.class)
public class DefaultAuthenticationTest {

    private FirebaseAuth mMockFirebase = mock(FirebaseAuth.class);

    private final DefaultAuthentication mAuthentication = new DefaultAuthentication(mMockFirebase);

    @Test
    public void testGetUser_notLoggedIn_ShouldEmitError() {
        when(mMockFirebase.getCurrentUser()).thenReturn(null);

        final TestObserver<User> testObserver = mAuthentication.getUser().test();

        testObserver.assertError(UserNotLoggedInException.class);
        testObserver.assertNotComplete();
        testObserver.assertNoValues();
    }

    @Test
    public void testGetUser_loggedIn_shouldEmitUser() {
        when(mMockFirebase.getCurrentUser()).thenReturn(mock(FirebaseUser.class));

        final TestObserver<User> testObserver = mAuthentication.getUser().test();

        testObserver.assertNoErrors();
        testObserver.assertComplete();
        final User user = testObserver.values().get(0);
        assertThat(user).isNotNull();
    }

    @Test
    public void testLoggedIn_notLoggedIn_shouldEmitFalse() {
        when(mMockFirebase.getCurrentUser()).thenReturn(null);

        final TestObserver<Boolean> testObserver = mAuthentication.isLoggedIn().test();

        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValue(false);
    }

    @Test
    public void testLoggedIn_loggedIn_shouldEmitTrue() {
        when(mMockFirebase.getCurrentUser()).thenReturn(mock(FirebaseUser.class));

        final TestObserver<Boolean> testObserver = mAuthentication.isLoggedIn().test();

        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValue(true);
    }

    @Test
    public void testSignUp_withError_shouldEmitError() {
        final MockTask<AuthResult> mockTask = new MockTask<AuthResult>() {
            @NonNull
            @Override
            public Task<AuthResult> addOnSuccessListener(
                    @NonNull final OnSuccessListener<? super AuthResult> onSuccessListener) {
                return this;
            }

            @NonNull
            @Override
            public Task<AuthResult> addOnFailureListener(@NonNull final OnFailureListener onFailureListener) {
                onFailureListener.onFailure(new IllegalArgumentException());
                return this;
            }
        };
        when(mMockFirebase.createUserWithEmailAndPassword(any(), any())).thenReturn(mockTask);

        final TestObserver<User> testObserver =
                mAuthentication.signUp("userName", "mail", "Pw").test();

        testObserver.assertError(IllegalArgumentException.class);
        testObserver.assertNotComplete();
        testObserver.assertNoValues();
    }

    @Ignore("Can't be tested... Think about hot to return a valid task which will then emit the emitter")
    @Test
    public void tesSignUp_shouldRegisterAndEmitUser() {
        final MockTask<AuthResult> mockTask = new MockTask<AuthResult>() {
            @NonNull
            @Override
            public Task<AuthResult> addOnSuccessListener(
                    @NonNull final OnSuccessListener<? super AuthResult> onSuccessListener) {
                return this;
            }

            @NonNull
            @Override
            public Task<AuthResult> addOnFailureListener(@NonNull final OnFailureListener onFailureListener) {
                return this;
            }
        };
        when(mMockFirebase.createUserWithEmailAndPassword(any(), any())).thenReturn(mockTask);

        final TestObserver<User> testObserver =
                mAuthentication.signUp("userName", "mail", "Pw").test();

        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertNoValues();
    }

    @Ignore("Can't be tested... Think about hot to return a valid task which will then emit the emitter")
    @Test
    public void testSignIn_ShouldSignIn() {
        final MockTask<AuthResult> mockTask = new MockTask<AuthResult>() {
            @NonNull
            @Override
            public Task<AuthResult> addOnSuccessListener(
                    @NonNull final OnSuccessListener<? super AuthResult> onSuccessListener) {
                return this;
            }

            @NonNull
            @Override
            public Task<AuthResult> addOnFailureListener(@NonNull final OnFailureListener onFailureListener) {
                return this;
            }
        };
        when(mMockFirebase.signInWithEmailAndPassword(any(), any())).thenReturn(mockTask);

        final TestObserver<User> testObserver = mAuthentication.signIn("mail", "Pw").test();

        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertNoValues();
    }

    @Test
    public void testSignOut_ShouldComplete() {
        final TestObserver<Void> testObserver = mAuthentication.signOut().test();

        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertNoValues();
    }

    @Test
    public void testSignOut_WithError_ShouldThrow() {
        final IllegalArgumentException exception = new IllegalArgumentException();
        doThrow(exception).when(mMockFirebase).signOut();

        final TestObserver<Void> testObserver = mAuthentication.signOut().test();

        testObserver.assertNotComplete();
        testObserver.assertError(exception);
        testObserver.assertNoValues();
    }
}