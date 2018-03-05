package guru.stefma.popularmovies.data;

import android.support.annotation.NonNull;
import guru.stefma.popularmovies.data.local.entity.LocalMovieFavoriteEntity;
import io.reactivex.Completable;
import io.reactivex.Single;
import java.util.List;

/**
 * A simple wrapper around the {@link guru.stefma.popularmovies.data.local.FavoriteMovieContentProvider} to don't use
 * it directly.
 */
public interface LocalRepository {

    /**
     * Saves the given {@link LocalMovieFavoriteEntity} into the local database.
     */
    Completable saveMovieAsFavorite(@NonNull final LocalMovieFavoriteEntity favoriteEntity);

    /**
     * Removes (if available) the given movieId from the favorites.
     *
     * Return {@link Completable#complete()} if it was removed. Otherwise {@link Completable#error(Throwable)}.
     */
    Completable removeMovieAsFavorite(final int movieId);

    /**
     * Get all currently saved favorites.
     *
     * Can be always called. Even if you are offline!
     */
    Single<List<LocalMovieFavoriteEntity>> getFavoritesMovies();
}
