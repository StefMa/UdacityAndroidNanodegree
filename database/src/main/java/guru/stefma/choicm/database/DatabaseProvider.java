package guru.stefma.choicm.database;

import com.google.firebase.database.FirebaseDatabase;
import guru.stefma.choicm.auth.Authentication;
import guru.stefma.choicm.auth.AuthenticationProvider;

/**
 * The AuthenticationProvider is a <b>entrypoint</b> to get a {@link Database} instance.
 *
 * Just call {@link DatabaseProvider#getInstance()} to get a <b>singleton</b> instance of it.
 */
public class DatabaseProvider {

    private static Database mInstance;

    public static Database getInstance() {
        if (mInstance == null) {
            final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            final Authentication authentication = AuthenticationProvider.getInstance();
            mInstance = new DefaultDatabase(firebaseDatabase, authentication);
        }
        return mInstance;
    }

}
