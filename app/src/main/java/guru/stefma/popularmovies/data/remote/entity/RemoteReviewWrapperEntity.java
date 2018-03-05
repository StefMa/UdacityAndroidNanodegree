package guru.stefma.popularmovies.data.remote.entity;

import com.squareup.moshi.Json;
import java.util.List;

public class RemoteReviewWrapperEntity {

    @Json(name = "id")
    public int mId;

    @Json(name = "page")
    public int mPage;

    @Json(name = "total_results")
    public int mTotalResults;

    @Json(name = "total_pages")
    public int mTotalPages;

    @Json(name = "results")
    public List<RemoteReviewEntity> mResults;

    public static class RemoteReviewEntity {

        @Json(name = "id")
        public String mId;

        @Json(name = "author")
        public String mAuthor;

        @Json(name = "content")
        public String mContent;

        @Json(name = "url")
        public String mUrl;

    }

}
