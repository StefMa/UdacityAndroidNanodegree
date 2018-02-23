package guru.stefma.popularmovies.domain.di;

import toothpick.Scope;
import toothpick.Toothpick;

/**
 * Take care of all dependency injection module and classes.
 */
public class DependencyGraph {

    private static final String APP_MODULE = "app_module";

    /**
     * Create the {@link ApplicationModule} which is the root module in the scope named {@link #APP_MODULE}
     */
    public static void init() {
        final Scope scope = Toothpick.openScope(APP_MODULE);
        scope.installModules(new ApplicationModule());
    }

    public static Scope getApplicationScope() {
        return Toothpick.openScope(APP_MODULE);
    }
}
