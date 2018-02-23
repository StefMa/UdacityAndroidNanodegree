package guru.stefma.popularmovies.presentation;

import android.app.Application;
import guru.stefma.popularmovies.domain.di.DependencyGraph;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        DependencyGraph.init();
    }
}
