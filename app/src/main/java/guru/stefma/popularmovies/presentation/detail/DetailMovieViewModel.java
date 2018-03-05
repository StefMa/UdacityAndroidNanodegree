package guru.stefma.popularmovies.presentation.detail;

import android.os.Parcel;
import android.os.Parcelable;

public class DetailMovieViewModel implements Parcelable {

    public static final Creator<DetailMovieViewModel> CREATOR = new Creator<DetailMovieViewModel>() {
        @Override
        public DetailMovieViewModel createFromParcel(Parcel in) {
            return new DetailMovieViewModel(in);
        }

        @Override
        public DetailMovieViewModel[] newArray(int size) {
            return new DetailMovieViewModel[size];
        }
    };

    private int mId;

    private String mImageUrl;

    private String mDescription;

    private String mTitle;

    private String mVoteAverage;

    private String mReleaseDate;

    public DetailMovieViewModel(
            final int id,
            final String imageUrl,
            final String description,
            final String title,
            final String voteAverage,
            final String releaseDate) {
        mId = id;
        mImageUrl = imageUrl;
        mDescription = description;
        mTitle = title;
        mVoteAverage = voteAverage;
        mReleaseDate = releaseDate;
    }

    protected DetailMovieViewModel(Parcel in) {
        mId = in.readInt();
        mImageUrl = in.readString();
        mDescription = in.readString();
        mTitle = in.readString();
        mVoteAverage = in.readString();
        mReleaseDate = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel parcel, final int i) {
        parcel.writeInt(mId);
        parcel.writeString(mImageUrl);
        parcel.writeString(mDescription);
        parcel.writeString(mTitle);
        parcel.writeString(mVoteAverage);
        parcel.writeString(mReleaseDate);
    }

    public int getId() {
        return mId;
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

    public String getVoteAverage() {
        return mVoteAverage;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }
}
