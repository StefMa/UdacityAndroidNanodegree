package guru.stefma.popularmovies.domain.usecase.favorite;

import android.support.annotation.NonNull;
import guru.stefma.popularmovies.data.MoviesRepository;
import guru.stefma.popularmovies.data.local.entity.LocalMovieFavoriteEntity;
import guru.stefma.popularmovies.domain.Movie;
import guru.stefma.popularmovies.domain.usecase.base.BaseCompletableUseCase;
import guru.stefma.popularmovies.domain.usecase.favorite.UpdateMovieFavoriteStateUseCase.Params;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Inject;

/**
 * A {@link BaseCompletableUseCase} which uses a {@link Movie} as param and save or remove it as a favorite.
 *
 * Will return {@link Completable#complete()} if successfully. Otherwise {@link Completable#error(Throwable)}.
 */
public class UpdateMovieFavoriteStateUseCase implements BaseCompletableUseCase<Params> {

    public static class Params {

        private FavoriteMovie mMovie;

        private boolean mIsFavorite;

        public Params(final FavoriteMovie movie, final boolean isFavorite) {
            mMovie = movie;
            mIsFavorite = isFavorite;
        }

    }

    @NonNull
    private final MoviesRepository mMoviesRepository;

    @Inject
    public UpdateMovieFavoriteStateUseCase(@NonNull final MoviesRepository moviesRepository) {
        mMoviesRepository = moviesRepository;
    }

    @Override
    public Completable execute(@NonNull final Params params) {
        if (params.mIsFavorite) {
            return mMoviesRepository.saveMovieAsFavorite(mapFavoriteMovieToLocalEntity(params.mMovie))
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        } else {
            return mMoviesRepository.removeMovieAsFavorite(params.mMovie.getId())
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        }
    }

    private LocalMovieFavoriteEntity mapFavoriteMovieToLocalEntity(final FavoriteMovie movie) {
        final LocalMovieFavoriteEntity localEntity = new LocalMovieFavoriteEntity();
        localEntity.mId = movie.getId();
        localEntity.mTitle = movie.getTitle();
        return localEntity;
    }

}
