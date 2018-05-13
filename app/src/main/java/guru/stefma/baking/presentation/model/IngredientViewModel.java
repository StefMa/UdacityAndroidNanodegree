package guru.stefma.baking.presentation.model;

import android.os.Parcel;
import android.os.Parcelable;

public class IngredientViewModel implements Parcelable {

    public static final Creator<IngredientViewModel> CREATOR = new Creator<IngredientViewModel>() {
        @Override
        public IngredientViewModel createFromParcel(Parcel in) {
            return new IngredientViewModel(in);
        }

        @Override
        public IngredientViewModel[] newArray(int size) {
            return new IngredientViewModel[size];
        }
    };

    private Float mQuantity;

    private String mMeasure;

    private String mName;

    public IngredientViewModel(final Float quantity, final String measure, final String name) {
        mQuantity = quantity;
        mMeasure = measure;
        mName = name;
    }

    protected IngredientViewModel(Parcel in) {
        if (in.readByte() == 0) {
            mQuantity = null;
        } else {
            mQuantity = in.readFloat();
        }
        mMeasure = in.readString();
        mName = in.readString();
    }

    public Float getQuantity() {
        return mQuantity;
    }

    public String getUnit() {
        return String.format("%s %s", getQuantity(), getMeasure());
    }

    public void setQuantity(final Float quantity) {
        mQuantity = quantity;
    }

    public String getMeasure() {
        return mMeasure;
    }

    public void setMeasure(final String measure) {
        mMeasure = measure;
    }

    public String getName() {
        return mName;
    }

    public void setName(final String name) {
        mName = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel parcel, final int i) {
        if (mQuantity == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeFloat(mQuantity);
        }
        parcel.writeString(mMeasure);
        parcel.writeString(mName);
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "mQuantity=" + mQuantity +
                ", mMeasure='" + mMeasure + '\'' +
                ", mName='" + mName + '\'' +
                '}';
    }

}
