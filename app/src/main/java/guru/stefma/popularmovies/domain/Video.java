package guru.stefma.popularmovies.domain;

import android.support.annotation.NonNull;

public class Video {

    public enum Site {
        YouTube;

        public static Site fromString(@NonNull final String site) {
            switch (site) {
                case "YouTube":
                    return Site.YouTube;
                default:
                    throw new IllegalArgumentException("Don't know what '" + site + "' is...");
            }
        }
    }

    private String mName;

    private String mKey;

    private Site mSite;

    public Video(final String name, final String key, final Site site) {
        mName = name;
        mKey = key;
        mSite = site;
    }

    public String getName() {
        return mName;
    }

    public String getKey() {
        return mKey;
    }

    public Site getSite() {
        return mSite;
    }
}
