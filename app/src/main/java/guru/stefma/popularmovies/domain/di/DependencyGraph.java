package guru.stefma.popularmovies.domain.di;

import android.content.Context;
import android.support.annotation.NonNull;
import toothpick.Scope;
import toothpick.Toothpick;

/**
 * Take care of all dependency injection module and classes.
 */
public class DependencyGraph {

    private static final String APP_MODULE = "app_module";

    /**
     * Create the {@link ApplicationModule} which is the root module in the scope named {@link #APP_MODULE}
     *
     * @param context a context. Should be the context from a {@link android.app.Application} instance!
     */
    public static void init(@NonNull final Context context) {
        final Scope scope = Toothpick.openScope(APP_MODULE);
        scope.installModules(new ApplicationModule(context));
    }

    public static Scope getApplicationScope() {
        return Toothpick.openScope(APP_MODULE);
    }
}
