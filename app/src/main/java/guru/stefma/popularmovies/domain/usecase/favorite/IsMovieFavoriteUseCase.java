package guru.stefma.popularmovies.domain.usecase.favorite;

import android.support.annotation.NonNull;
import guru.stefma.popularmovies.data.MoviesRepository;
import guru.stefma.popularmovies.data.local.entity.LocalMovieFavoriteEntity;
import guru.stefma.popularmovies.domain.Movie;
import guru.stefma.popularmovies.domain.usecase.base.BaseSingleUseCase;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import javax.inject.Inject;

/**
 * A UseCase which will return either {@link Boolean#TRUE} if the given {@link Movie#getId()} is a favorite.
 * Will return {@link Boolean#FALSE} if not.
 */
public class IsMovieFavoriteUseCase implements BaseSingleUseCase<Boolean, Integer> {

    @NonNull
    private final MoviesRepository mMoviesRepository;

    @Inject
    public IsMovieFavoriteUseCase(@NonNull final MoviesRepository moviesRepository) {
        mMoviesRepository = moviesRepository;
    }

    @Override
    public Single<Boolean> execute(@NonNull final Integer params) {
        return mMoviesRepository.getFavoritesMovies()
                .subscribeOn(Schedulers.io())
                .map(list -> findMovieWithId(list, params))
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Tries to find the given movieId in the given {@link List} of {@link LocalMovieFavoriteEntity}.
     *
     * @param list the list where we search for a given movieId
     * @return true if we found the movieId in the list. False otherwise.
     */
    private boolean findMovieWithId(final List<LocalMovieFavoriteEntity> list, int movieId) {
        for (final LocalMovieFavoriteEntity entity : list) {
            if (entity.mId == movieId) {
                return true;
            }
        }
        return false;
    }
}
