package guru.stefma.choicm.auth;

import android.support.annotation.NonNull;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import guru.stefma.choicm.auth.exception.UserNotLoggedInException;
import guru.stefma.choicm.auth.model.User;
import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * The default implementation of an {@link Authentication} instance.
 *
 * The class will be used by default if you a user request an instance over the {@link AuthenticationProvider}.
 */
class DefaultAuthentication implements Authentication {

    private final FirebaseAuth mFirebaseAuth;

    DefaultAuthentication(@NonNull final FirebaseAuth firebaseAuth) {
        mFirebaseAuth = firebaseAuth;
    }

    @NonNull
    @Override
    public Single<Boolean> isLoggedIn() {
        return Single.fromCallable(() -> mFirebaseAuth.getCurrentUser() != null);
    }

    @NonNull
    @Override
    public Single<User> getUser() {
        return isLoggedIn()
                .flatMap(isLoggedIn -> {
                    if (!isLoggedIn) {
                        return Single.error(UserNotLoggedInException::new);
                    } else {
                        final FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                        return Single.just(mapFirebaseUserToUser(firebaseUser));
                    }
                });
    }

    @NonNull
    @Override
    public Single<User> signUp(
            @NonNull final String userName,
            @NonNull final String email,
            @NonNull final String password) {
        return Single.create(emitter -> {
            mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener(authResult -> {
                        final FirebaseUser firebaseUser = authResult.getUser();
                        final UserProfileChangeRequest changeRequest = new UserProfileChangeRequest.Builder()
                                .setDisplayName(userName)
                                .build();
                        firebaseUser.updateProfile(changeRequest)
                                .addOnSuccessListener(ignore -> {
                                    emitter.onSuccess(mapFirebaseUserToUser(firebaseUser));
                                })
                                .addOnFailureListener(e -> {
                                    // TODO: Maybe send a better exception
                                    emitter.onError(e);
                                });
                    })
                    .addOnFailureListener(e -> {
                        // TODO: Maybe send a better exception
                        emitter.onError(e);
                    });
        });
    }

    @NonNull
    @Override
    public Single<User> signIn(@NonNull final String email, @NonNull final String password) {
        return Single.create(emitter -> {
            mFirebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener(authResult -> {
                        final FirebaseUser firebaseUser = authResult.getUser();
                        emitter.onSuccess(mapFirebaseUserToUser(firebaseUser));
                    })
                    .addOnFailureListener(e -> {
                        // TODO: Maybe send a better exception
                        emitter.onError(e);
                    });
        });
    }

    @Override
    public Completable signOut() {
        return Completable.create(emitter -> {
            try {
                mFirebaseAuth.signOut();
                emitter.onComplete();
            } catch (Exception exception) {
                // TODO: Will this throw sometimes?!
                // Or is that always successfully
                emitter.onError(exception);
            }
        });
    }

    private User mapFirebaseUserToUser(@NonNull final FirebaseUser firebaseUser) {
        return new User(firebaseUser.getUid(), firebaseUser.getDisplayName());
    }
}
