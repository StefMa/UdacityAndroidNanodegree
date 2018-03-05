package guru.stefma.popularmovies.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import guru.stefma.popularmovies.data.remote.entity.RemoteMovieWrapperEntity;
import guru.stefma.popularmovies.data.remote.entity.RemoteReviewWrapperEntity;
import guru.stefma.popularmovies.data.remote.entity.RemoteVideoWrapperEntity;
import io.reactivex.Single;

public interface MoviesRepository extends LocalRepository {

    /**
     * Get movies sorted by popularity.
     *
     * The returned entity may contain multiple pages (see {@link RemoteMovieWrapperEntity#mPage}).
     * You can add a optional page number to return the list for a given page number.
     *
     * @param apiKey the api key for the movie db.
     * @param page   the page number.
     */
    Single<RemoteMovieWrapperEntity> getPopularMovies(@NonNull final String apiKey, @Nullable Integer page);

    /**
     * Get movies sorted by top rated.
     *
     * The returned entity may contain multiple pages (see {@link RemoteMovieWrapperEntity#mPage}).
     * You can add a optional page number to return the list for a given page number.
     *
     * @param apiKey the api key for the movie db.
     * @param page   the page number.
     */
    Single<RemoteMovieWrapperEntity> getTopRatedMovies(@NonNull final String apiKey, @Nullable Integer page);

    /**
     * Get reviews for a given movieId.
     *
     * The returned entity may contain multiple pages (see {@link RemoteReviewWrapperEntity#mPage}).
     * You can add a optional page number to return the list for a given page number.
     *
     * @param apiKey  the api key for the movie db.
     * @param page    the page number.
     * @param movieId the movie id for the reviews.
     */
    Single<RemoteReviewWrapperEntity> getReviewsForMovie(
            @NonNull final String apiKey,
            @Nullable final Integer page,
            final int movieId
    );

    /**
     * Get all videos for a given movieId.
     *
     * @param apiKey  the api key for the movie db.
     * @param movieId the movie id for the videos.
     */
    Single<RemoteVideoWrapperEntity> getVideosForMovie(@NonNull final String apiKey, final int movieId);

}
