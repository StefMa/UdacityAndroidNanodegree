package guru.stefma.choicm.auth;

import com.google.firebase.auth.FirebaseAuth;

/**
 * The AuthenticationProvider is a <b>entrypoint</b> to get a {@link Authentication} instance.
 *
 * Just call {@link AuthenticationProvider#getInstance()} to get a <b>singleton</b> instance of it.
 */
public class AuthenticationProvider {

    private static Authentication mInstance;

    public static Authentication getInstance() {
        if (mInstance == null) {
            final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            mInstance = new DefaultAuthentication(firebaseAuth);
        }
        return mInstance;
    }

}
