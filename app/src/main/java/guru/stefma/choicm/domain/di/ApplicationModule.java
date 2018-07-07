package guru.stefma.choicm.domain.di;

import android.content.Context;
import guru.stefma.choicm.auth.Authentication;
import guru.stefma.choicm.auth.AuthenticationProvider;
import guru.stefma.choicm.database.Database;
import guru.stefma.choicm.database.DatabaseProvider;
import toothpick.config.Module;

/**
 * The ApplicationModule take care of all "general" instances of the App.
 */
class ApplicationModule extends Module {

    ApplicationModule(final Context context) {
        // Bind a singleton instance of the Authentication
        bind(Authentication.class).toInstance(AuthenticationProvider.getInstance());
        // Bind a singleton instance of the Database
        bind(Database.class).toInstance(DatabaseProvider.getInstance());
    }

}
