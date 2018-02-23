package guru.stefma.popularmovies.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import guru.stefma.popularmovies.data.remote.entity.RemoteMovieWrapperEntity;
import io.reactivex.Single;

public interface MoviesRepository {

    /**
     * Get movies sorted by popularity.
     *
     * The returned entity may contain multiple pages (see {@link RemoteMovieWrapperEntity#mPage}).
     * You can add a optional page number to return the list for a given page number.
     *
     * @param apiKey the api key for the movie db.
     * @param page   the page number
     */
    Single<RemoteMovieWrapperEntity> getPopularMovies(@NonNull final String apiKey, @Nullable Integer page);

    /**
     * Get movies sorted by top rated.
     *
     * The returned entity may contain multiple pages (see {@link RemoteMovieWrapperEntity#mPage}).
     * You can add a optional page number to return the list for a given page number.
     *
     * @param apiKey the api key for the movie db.
     * @param page   the page number
     */
    Single<RemoteMovieWrapperEntity> getTopRatedMovies(@NonNull final String apiKey, @Nullable Integer page);
}
