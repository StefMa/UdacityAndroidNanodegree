package guru.stefma.popularmovies.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import guru.stefma.popularmovies.data.remote.ApiService;
import guru.stefma.popularmovies.data.remote.entity.RemoteMovieWrapperEntity;
import io.reactivex.Single;

public class MoviesRepositoryImpl implements MoviesRepository {

    private final ApiService mApiService;

    public MoviesRepositoryImpl(@NonNull final ApiService apiService) {
        mApiService = apiService;
    }

    @Override
    public Single<RemoteMovieWrapperEntity> getPopularMovies(
            @NonNull final String apiKey,
            @Nullable final Integer page) {
        return mApiService.popularMovies(apiKey, page);
    }

    @Override
    public Single<RemoteMovieWrapperEntity> getTopRatedMovies(
            @NonNull final String apiKey,
            @Nullable final Integer page) {
        return mApiService.topRatedMovies(apiKey, page);
    }

}
