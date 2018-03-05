package guru.stefma.popularmovies.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import guru.stefma.popularmovies.data.local.entity.LocalMovieFavoriteEntity;
import guru.stefma.popularmovies.data.remote.ApiService;
import guru.stefma.popularmovies.data.remote.entity.RemoteMovieWrapperEntity;
import guru.stefma.popularmovies.data.remote.entity.RemoteReviewWrapperEntity;
import guru.stefma.popularmovies.data.remote.entity.RemoteVideoWrapperEntity;
import io.reactivex.Completable;
import io.reactivex.Single;
import java.util.List;

public class MoviesRepositoryImpl implements MoviesRepository {

    @NonNull
    private final ApiService mApiService;

    @NonNull
    private final LocalRepository mLocalRepo;

    public MoviesRepositoryImpl(
            @NonNull final ApiService apiService,
            @NonNull final LocalRepository localRepo) {
        mApiService = apiService;
        mLocalRepo = localRepo;
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

    @Override
    public Single<RemoteReviewWrapperEntity> getReviewsForMovie(
            @NonNull final String apiKey,
            @Nullable final Integer page,
            final int movieId) {
        return mApiService.reviewsForMovieWithId(movieId, apiKey, page);
    }

    @Override
    public Single<RemoteVideoWrapperEntity> getVideosForMovie(@NonNull final String apiKey, final int movieId) {
        return mApiService.videosForMovieWithId(movieId, apiKey);
    }

    @Override
    public Completable saveMovieAsFavorite(@NonNull final LocalMovieFavoriteEntity favoriteEntity) {
        return mLocalRepo.saveMovieAsFavorite(favoriteEntity);
    }

    @Override
    public Completable removeMovieAsFavorite(final int movieId) {
        return mLocalRepo.removeMovieAsFavorite(movieId);
    }

    @Override
    public Single<List<LocalMovieFavoriteEntity>> getFavoritesMovies() {
        return mLocalRepo.getFavoritesMovies();
    }

}
