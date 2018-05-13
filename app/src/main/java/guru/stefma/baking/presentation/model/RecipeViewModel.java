package guru.stefma.baking.presentation.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

public class RecipeViewModel implements Parcelable {

    public static final Creator<RecipeViewModel> CREATOR = new Creator<RecipeViewModel>() {
        @Override
        public RecipeViewModel createFromParcel(Parcel in) {
            return new RecipeViewModel(in);
        }

        @Override
        public RecipeViewModel[] newArray(int size) {
            return new RecipeViewModel[size];
        }
    };

    private String mName;

    private List<IngredientViewModel> mIngredients = null;

    private List<StepViewModel> mSteps = null;

    private String mImage;

    public RecipeViewModel(
            final String name,
            final List<IngredientViewModel> ingredients,
            final List<StepViewModel> steps,
            final String image
    ) {
        mName = name;
        mIngredients = ingredients;
        mSteps = steps;
        mImage = image;
    }

    protected RecipeViewModel(Parcel in) {
        mName = in.readString();
        mIngredients = in.createTypedArrayList(IngredientViewModel.CREATOR);
        mSteps = in.createTypedArrayList(StepViewModel.CREATOR);
        mImage = in.readString();
    }

    public String getName() {
        return mName;
    }

    public List<IngredientViewModel> getIngredients() {
        return mIngredients;
    }

    public List<StepViewModel> getSteps() {
        return mSteps;
    }

    public String getImage() {
        return mImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel parcel, final int i) {
        parcel.writeString(mName);
        parcel.writeTypedList(mIngredients);
        parcel.writeTypedList(mSteps);
        parcel.writeString(mImage);
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "mName='" + mName + '\'' +
                ", mIngredients=" + mIngredients +
                ", mSteps=" + mSteps +
                ", mImage='" + mImage + '\'' +
                '}';
    }

}
