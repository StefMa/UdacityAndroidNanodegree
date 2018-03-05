package guru.stefma.popularmovies.data.local;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import guru.stefma.popularmovies.data.local.FavoriteMovieContract.FavoriteMovieEntry;

public class FavoriteMovieContentProvider extends ContentProvider {

    private static final int CODE_ALL = 999;

    private static final int CODE_INSERT = 111;

    private FavoriteMovieDBHelper mDBHelper;

    private UriMatcher mUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {

        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.
                addURI(FavoriteMovieContract.CONTENT_AUTHORITY, FavoriteMovieContract.PATH_FAVORITE_MOVIES, CODE_ALL);
        uriMatcher.
                addURI(FavoriteMovieContract.CONTENT_AUTHORITY,
                        FavoriteMovieContract.PATH_FAVORITE_MOVIES + "/#",
                        CODE_INSERT);

        return uriMatcher;

    }

    @Override
    public boolean onCreate() {
        mDBHelper = new FavoriteMovieDBHelper(getContext());

        return true;
    }

    @Nullable
    @Override
    public Cursor query(
            @NonNull final Uri uri, @Nullable final String[] projection, @Nullable final String selection,
            @Nullable final String[] selectionArgs, @Nullable final String sortOrder) {
        final SQLiteDatabase database = mDBHelper.getReadableDatabase();

        switch (mUriMatcher.match(uri)) {
            case CODE_ALL:
                return database.query(FavoriteMovieEntry.TABLE_NAME,
                        projection, selection, selectionArgs, null, null, sortOrder);
            default:
                throw new IllegalArgumentException("Can't handle uri: " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull final Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull final Uri uri, @Nullable final ContentValues contentValues) {
        final SQLiteDatabase database = mDBHelper.getWritableDatabase();

        switch (mUriMatcher.match(uri)) {
            case CODE_INSERT:
                final long insert = database.insert(FavoriteMovieEntry.TABLE_NAME, null, contentValues);
                if (insert > 0) {
                    return FavoriteMovieEntry.buildUriWithMovieId(insert);
                }
                throw new IllegalArgumentException("Can't insert movie with given id");
            default:
                throw new IllegalArgumentException("Can't handle uri: " + uri);
        }
    }

    @Override
    public int delete(@NonNull final Uri uri, @Nullable final String s, @Nullable final String[] strings) {
        final SQLiteDatabase database = mDBHelper.getWritableDatabase();

        switch (mUriMatcher.match(uri)) {
            case CODE_INSERT:
                return database.delete(FavoriteMovieEntry.TABLE_NAME,
                        FavoriteMovieEntry.COLUMN_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
            case CODE_ALL:
                return database.delete(FavoriteMovieEntry.TABLE_NAME, null, null);
            default:
                throw new IllegalArgumentException("Can't handle uri: " + uri);
        }
    }

    @Override
    public int update(@NonNull final Uri uri, @Nullable final ContentValues contentValues, @Nullable final String s,
            @Nullable final String[] strings) {
        return 0;
    }
}
