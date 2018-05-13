package guru.stefma.baking.data.remote.entity;

import com.squareup.moshi.Json;

public class RemoteStepEntity {

    @Json(name = "id")
    private Integer mId;

    @Json(name = "shortDescription")
    private String mShortDescription;

    @Json(name = "description")
    private String mDescription;

    @Json(name = "videoURL")
    private String mVideoUrl;

    @Json(name = "thumbnailURL")
    private String mThumbnailUrl;

    public Integer getId() {
        return mId;
    }

    public void setId(final Integer id) {
        mId = id;
    }

    public String getShortDescription() {
        return mShortDescription;
    }

    public void setShortDescription(final String shortDescription) {
        mShortDescription = shortDescription;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(final String description) {
        mDescription = description;
    }

    public String getVideoUrl() {
        return mVideoUrl;
    }

    public void setVideoUrl(final String videoUrl) {
        mVideoUrl = videoUrl;
    }

    public String getThumbnailUrl() {
        return mThumbnailUrl;
    }

    public void setThumbnailUrl(final String thumbnailUrl) {
        mThumbnailUrl = thumbnailUrl;
    }
}
