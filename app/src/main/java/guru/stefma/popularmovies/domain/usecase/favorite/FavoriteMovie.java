package guru.stefma.popularmovies.domain.usecase.favorite;

public class FavoriteMovie {

    private final int mId;

    private final String mTitle;

    public FavoriteMovie(final int id, final String title) {
        mId = id;
        mTitle = title;
    }

    public int getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

}
