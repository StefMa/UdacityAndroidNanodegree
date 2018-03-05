package guru.stefma.popularmovies.data.remote;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import guru.stefma.popularmovies.data.remote.entity.RemoteMovieWrapperEntity;
import guru.stefma.popularmovies.data.remote.entity.RemoteReviewWrapperEntity;
import guru.stefma.popularmovies.data.remote.entity.RemoteVideoWrapperEntity;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("movie/popular")
    Single<RemoteMovieWrapperEntity> popularMovies(
            @Query(value = "api_key") @NonNull final String apiKey,
            @Query(value = "page") @Nullable final Integer pageNumber
    );

    @GET("movie/top_rated")
    Single<RemoteMovieWrapperEntity> topRatedMovies(
            @Query(value = "api_key") @NonNull final String apiKey,
            @Query(value = "page") @Nullable final Integer pageNumber
    );

    @GET("movie/{movieId}/reviews")
    Single<RemoteReviewWrapperEntity> reviewsForMovieWithId(
            @Path(value = "movieId") final int movieId,
            @Query(value = "api_key") @NonNull final String apiKey,
            @Query(value = "page") @Nullable final Integer pageNumber
    );

    @GET("movie/{movieId}/videos")
    Single<RemoteVideoWrapperEntity> videosForMovieWithId(
            @Path(value = "movieId") final int movieId,
            @Query(value = "api_key") @NonNull final String apiKey
    );

}
