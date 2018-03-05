package guru.stefma.popularmovies.data.local;

import android.content.ContentUris;
import android.net.Uri;

public class FavoriteMovieContract {

    public static final String CONTENT_AUTHORITY = "guru.stefma.popularmovies";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_FAVORITE_MOVIES = "favoritemovies";

    public static final class FavoriteMovieEntry {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_FAVORITE_MOVIES)
                .build();

        public static final String TABLE_NAME = "favoritemovies";

        public static final String COLUMN_ID = "id";

        public static final String COLUMN_NAME = "name";

        public static Uri buildUriWithMovieId(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

}
