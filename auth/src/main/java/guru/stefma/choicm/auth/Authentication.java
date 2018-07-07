package guru.stefma.choicm.auth;

import android.support.annotation.NonNull;
import guru.stefma.choicm.auth.model.User;
import io.reactivex.Completable;
import io.reactivex.Single;

public interface Authentication {

    /**
     * Check if there is currently a user logged in.
     *
     * The {@link Single} will either emit <code>true</code> if the user is logged in or <code>false</code> otherwise.
     */
    @NonNull
    Single<Boolean> isLoggedIn();

    /**
     * Get a {@link User} instance.
     *
     * The {@link Single} will either emit the valid {@link User} instance or an error.
     */
    @NonNull
    Single<User> getUser();

    /**
     * Sign up a new user.
     *
     * This will emit either a valid {@link User} instance or an <b>error</b> if something happen.
     * E.g. the email is already registered or something like this.
     */
    @NonNull
    Single<User> signUp(
            @NonNull final String userName,
            @NonNull final String email,
            @NonNull final String password);

    /**
     * Sign in a {@link User} with the given credentials.
     *
     * This will either emit the successfully logged in {@link User} or emit a <b>error</b>.
     */
    @NonNull
    Single<User> signIn(
            @NonNull final String email,
            @NonNull final String password);

    /**
     * Sign out the current logged in - if so - user.
     *
     * Will complete if successfully logged out or emit a <b>error</b>.
     */
    Completable signOut();

}
