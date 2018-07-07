package guru.stefma.choicm.logging;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import timber.log.Timber;
import timber.log.Timber.DebugTree;
import timber.log.Timber.Tree;

/**
 * A ContentProvider which will init a {@link timber.log.Timber.Tree} for {@link Timber}.
 */
public class InitTimberProvider extends ContentProvider {

    /**
     * A empty tree which will be used on relase builds
     * to don't log stuff...
     */
    private static class EmptyTree extends Tree {

        @Override
        protected void log(
                final int priority,
                @Nullable final String tag,
                @NotNull final String message,
                @Nullable final Throwable t) {
            // Do nothing
        }
    }

    @Override
    public boolean onCreate() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new DebugTree());
        } else {
            Timber.plant(new EmptyTree());
        }
        return true;
    }

    @Override
    public Cursor query(final Uri uri, final String[] strings, final String s, final String[] strings1,
            final String s1) {
        throw new IllegalArgumentException("Not allowed to call");
    }

    @Override
    public String getType(final Uri uri) {
        throw new IllegalArgumentException("Not allowed to call");
    }

    @Override
    public Uri insert(final Uri uri, final ContentValues contentValues) {
        throw new IllegalArgumentException("Not allowed to call");
    }

    @Override
    public int delete(final Uri uri, final String s, final String[] strings) {
        throw new IllegalArgumentException("Not allowed to call");
    }

    @Override
    public int update(final Uri uri, final ContentValues contentValues, final String s, final String[] strings) {
        throw new IllegalArgumentException("Not allowed to call");
    }
}
