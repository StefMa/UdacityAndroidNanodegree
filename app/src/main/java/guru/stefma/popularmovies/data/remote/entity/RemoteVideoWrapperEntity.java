package guru.stefma.popularmovies.data.remote.entity;

import com.squareup.moshi.Json;
import java.util.List;

public class RemoteVideoWrapperEntity {

    @Json(name = "id")
    public int mId;

    @Json(name = "results")
    public List<RemoteVideoEntity> mResults;

    public static class RemoteVideoEntity {

        @Json(name = "id")
        public String mId;

        @Json(name = "iso_639_1")
        public String mIso6391;

        @Json(name = "iso_3166_1")
        public String mIso31661;

        @Json(name = "key")
        public String mKey;

        @Json(name = "name")
        public String mName;

        @Json(name = "site")
        public String mSite;

        @Json(name = "size")
        public int mSize;

        @Json(name = "type")
        public String mType;

    }

}
