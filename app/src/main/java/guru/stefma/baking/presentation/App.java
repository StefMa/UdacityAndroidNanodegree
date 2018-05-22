package guru.stefma.baking.presentation;

import android.app.Application;
import guru.stefma.baking.domain.di.DependencyGraph;
import timber.log.Timber;
import timber.log.Timber.DebugTree;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        DependencyGraph.init(this);
        Timber.plant(new DebugTree());
    }
}
