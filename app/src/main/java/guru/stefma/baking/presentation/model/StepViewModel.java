package guru.stefma.baking.presentation.model;

import android.os.Parcel;
import android.os.Parcelable;

public class StepViewModel implements Parcelable {

    public static final Creator<StepViewModel> CREATOR = new Creator<StepViewModel>() {
        @Override
        public StepViewModel createFromParcel(Parcel in) {
            return new StepViewModel(in);
        }

        @Override
        public StepViewModel[] newArray(int size) {
            return new StepViewModel[size];
        }
    };

    private final String mStepNumber;

    private final String mShortDescription;

    private final String mDescription;

    private final String mVideoUrl;

    private final String mThumbNailUrl;

    public StepViewModel(final String stepNumber, final String shortDescription, final String description,
            final String videoUrl,
            final String thumbNailUrl) {
        mStepNumber = stepNumber;
        mShortDescription = shortDescription;
        mDescription = description;
        mVideoUrl = videoUrl;
        mThumbNailUrl = thumbNailUrl;
    }

    protected StepViewModel(Parcel in) {
        mStepNumber = in.readString();
        mShortDescription = in.readString();
        mDescription = in.readString();
        mVideoUrl = in.readString();
        mThumbNailUrl = in.readString();
    }

    public String getStep() {
        if (getStepNumber().equals("0")) {
            return getShortDescription();
        }
        return String.format("%s. %s", getStepNumber(), getShortDescription());
    }

    public String getStepNumber() {
        return mStepNumber;
    }

    public String getShortDescription() {
        return mShortDescription;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getVideoUrl() {
        return mVideoUrl;
    }

    public String getThumbNailUrl() {
        return mThumbNailUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel parcel, final int i) {
        parcel.writeString(mStepNumber);
        parcel.writeString(mShortDescription);
        parcel.writeString(mDescription);
        parcel.writeString(mVideoUrl);
        parcel.writeString(mThumbNailUrl);
    }

    @Override
    public String toString() {
        return "StepViewModel{" +
                "mStepNumber='" + mStepNumber + '\'' +
                ", mShortDescription='" + mShortDescription + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mVideoUrl='" + mVideoUrl + '\'' +
                ", mThumbNailUrl='" + mThumbNailUrl + '\'' +
                '}';
    }
}
