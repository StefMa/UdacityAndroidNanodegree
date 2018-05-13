package guru.stefma.baking.domain;

public class Step {

    private final int mId;

    private final String mShortDescription;

    private final String mDescription;

    private final String mVideoUrl;

    private final String mThumbnailUrl;

    public Step(final int id, final String shortDescription, final String description, final String videoUrl,
            final String thumbnailUrl) {
        mId = id;
        mShortDescription = shortDescription;
        mDescription = description;
        mVideoUrl = videoUrl;
        mThumbnailUrl = thumbnailUrl;
    }

    public int getId() {
        return mId;
    }

    public String getShortDescription() {
        return mShortDescription;
    }

    public String getThumbnailUrl() {
        return mThumbnailUrl;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getVideoUrl() {
        return mVideoUrl;
    }

    @Override
    public String toString() {
        return "Step{" +
                "mId=" + mId +
                ", mShortDescription='" + mShortDescription + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mVideoUrl='" + mVideoUrl + '\'' +
                ", mThumbnailUrl='" + mThumbnailUrl + '\'' +
                '}';
    }
}
