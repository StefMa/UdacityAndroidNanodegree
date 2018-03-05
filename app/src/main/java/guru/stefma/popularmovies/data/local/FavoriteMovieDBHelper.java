package guru.stefma.popularmovies.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import guru.stefma.popularmovies.data.local.FavoriteMovieContract.FavoriteMovieEntry;

public class FavoriteMovieDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "FavMovies.db";

    private static final int DB_VERSION = 1;

    private static final String SQL_CREATE_FAVORITE_MOVIE_TABLE =
            "CREATE TABLE " + FavoriteMovieEntry.TABLE_NAME + " (" +
                    FavoriteMovieEntry.COLUMN_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                    FavoriteMovieEntry.COLUMN_NAME + " TEXT NOT NULL);";

    public FavoriteMovieDBHelper(@NonNull final Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(final SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_MOVIE_TABLE);
    }

    /**
     * Currently we don't have a upgrade mechanism. Just drop the favorite table and create a new one.
     */
    @Override
    public void onUpgrade(final SQLiteDatabase sqLiteDatabase, final int i, final int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FavoriteMovieEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
