package guru.stefma.popularmovies.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import guru.stefma.popularmovies.data.local.FavoriteMovieContract.FavoriteMovieEntry;
import guru.stefma.popularmovies.data.local.entity.LocalMovieFavoriteEntity;
import io.reactivex.Completable;
import io.reactivex.Single;
import java.util.ArrayList;
import java.util.List;

/**
 * A implementation of {@link LocalRepository} to strip out the {@link Context} from the {@link MoviesRepository}.
 */
public class LocalRepositoryImpl implements LocalRepository {

    @NonNull
    private final Context mContext;

    public LocalRepositoryImpl(@NonNull final Context context) {
        mContext = context;
    }

    @Override
    public Completable saveMovieAsFavorite(@NonNull final LocalMovieFavoriteEntity favoriteEntity) {
        return Completable.fromRunnable(() -> {
            final ContentValues contentValues = new ContentValues();
            contentValues.put(FavoriteMovieEntry.COLUMN_ID, favoriteEntity.mId);
            contentValues.put(FavoriteMovieEntry.COLUMN_NAME, favoriteEntity.mTitle);
            mContext.getContentResolver()
                    .insert(FavoriteMovieEntry.buildUriWithMovieId(favoriteEntity.mId), contentValues);
        });
    }

    @Override
    public Completable removeMovieAsFavorite(final int movieId) {
        return Completable.fromRunnable(() -> {
            mContext.getContentResolver()
                    .delete(FavoriteMovieEntry.buildUriWithMovieId(movieId), null, null);
        });
    }

    @Override
    public Single<List<LocalMovieFavoriteEntity>> getFavoritesMovies() {
        return Single.fromCallable(() -> {
            final Cursor cursor = mContext.getContentResolver()
                    .query(FavoriteMovieEntry.CONTENT_URI, null, null, null, FavoriteMovieEntry.COLUMN_ID);
            final List<LocalMovieFavoriteEntity> localFavoriteEntities = new ArrayList<>();
            if (cursor != null && cursor.moveToFirst()) {
                addMovieToList(cursor, localFavoriteEntities);
                while (cursor.moveToNext()) {
                    addMovieToList(cursor, localFavoriteEntities);
                }
                cursor.close();
                return localFavoriteEntities;
            } else {
                return localFavoriteEntities;
            }
        });
    }

    private void addMovieToList(final Cursor cursor, final List<LocalMovieFavoriteEntity> localFavoriteEntities) {
        final int movieId = cursor.getInt(cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_ID));
        final String movieName = cursor.getString(cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_NAME));
        final LocalMovieFavoriteEntity favoriteEntity = new LocalMovieFavoriteEntity();
        favoriteEntity.mId = movieId;
        favoriteEntity.mTitle = movieName;
        localFavoriteEntities.add(favoriteEntity);
    }
}
