package guru.stefma.popularmovies.domain.di;

import android.content.Context;
import guru.stefma.popularmovies.data.LocalRepositoryImpl;
import guru.stefma.popularmovies.data.MoviesRepository;
import guru.stefma.popularmovies.data.MoviesRepositoryImpl;
import guru.stefma.popularmovies.data.remote.RetrofitService;
import toothpick.config.Module;

public class ApplicationModule extends Module {

    public ApplicationModule(final Context context) {
        // Bind a singleton instance of the MoviesRepository
        bind(MoviesRepository.class).toInstance(
                new MoviesRepositoryImpl(RetrofitService.getApiService(), new LocalRepositoryImpl(context)));
    }

}
