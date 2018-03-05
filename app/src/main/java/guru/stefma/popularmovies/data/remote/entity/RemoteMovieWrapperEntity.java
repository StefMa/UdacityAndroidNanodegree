package guru.stefma.popularmovies.data.remote.entity;

import android.support.annotation.Nullable;
import com.squareup.moshi.Json;
import java.util.List;

public class RemoteMovieWrapperEntity {

    @Json(name = "page")
    public int mPage;

    @Json(name = "total_results")
    public int mTotalResults;

    @Json(name = "total_pages")
    public int mTotalPages;

    @Json(name = "results")
    public List<RemoteMovieEntity> mResults;

    public static class RemoteMovieEntity {

        @Nullable
        @Json(name = "poster_path")
        public String mPosterPath;

        @Json(name = "adult")
        public boolean mAdult;

        @Json(name = "overview")
        public String mOverview;

        @Json(name = "release_date")
        public String mReleaseDate;

        @Json(name = "genre_ids")
        public List<Integer> mGenreIds;

        @Json(name = "id")
        public int mId;

        @Json(name = "original_title")
        public String mOriginalTitle;

        @Json(name = "original_language")
        public String mOriginalLanguage;

        @Json(name = "title")
        public String mTitle;

        @Nullable
        @Json(name = "backdrop_path")
        public String mBackdropPath;

        @Json(name = "popularity")
        public float mPopularity;

        @Json(name = "vote_count")
        public int mVoteCount;

        @Json(name = "video")
        public boolean mVideo;

        @Json(name = "vote_average")
        public float mVoteAverage;

    }


}
