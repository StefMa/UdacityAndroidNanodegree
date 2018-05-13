package guru.stefma.baking.domain;

import java.util.List;

public class Recipe {

    private String mName;

    private List<Ingredient> mIngredients = null;

    private List<Step> mSteps = null;

    private String mImage;

    public Recipe(final String name, final List<Ingredient> ingredients, final List<Step> steps, final String image) {
        mName = name;
        mIngredients = ingredients;
        mSteps = steps;
        mImage = image;
    }

    public String getName() {
        return mName;
    }

    public List<Ingredient> getIngredients() {
        return mIngredients;
    }

    public List<Step> getSteps() {
        return mSteps;
    }

    public String getImage() {
        return mImage;
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
