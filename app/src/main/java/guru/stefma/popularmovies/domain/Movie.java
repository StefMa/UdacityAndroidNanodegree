package guru.stefma.popularmovies.domain;

public class Movie {

    private String mImageUrl;

    private String mDescription;

    private String mTitle;

    private double mVoteAverage;

    private String mReleaseDate;

    public Movie(
            final String imageUrl,
            final String description,
            final String title,
            final double voteAverage,
            final String releaseDate) {
        mImageUrl = imageUrl;
        mDescription = description;
        mTitle = title;
        mVoteAverage = voteAverage;
        mReleaseDate = releaseDate;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getTitle() {
        return mTitle;
    }

    public double getVoteAverage() {
        return mVoteAverage;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }
}
