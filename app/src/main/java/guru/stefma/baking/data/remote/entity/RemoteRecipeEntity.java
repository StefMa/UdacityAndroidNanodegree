package guru.stefma.baking.data.remote.entity;

import com.squareup.moshi.Json;
import java.util.List;

public class RemoteRecipeEntity {

    @Json(name = "id")
    private Integer mId;

    @Json(name = "name")
    private String mName;

    @Json(name = "ingredients")
    private List<RemoteIngredientEntity> mIngredients = null;

    @Json(name = "steps")
    private List<RemoteStepEntity> mSteps = null;

    @Json(name = "servings")
    private Integer mServings;

    @Json(name = "image")
    private String mImage;

    public Integer getId() {
        return mId;
    }

    public void setId(final Integer id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(final String name) {
        mName = name;
    }

    public List<RemoteIngredientEntity> getIngredients() {
        return mIngredients;
    }

    public void setIngredients(final List<RemoteIngredientEntity> ingredients) {
        mIngredients = ingredients;
    }

    public List<RemoteStepEntity> getSteps() {
        return mSteps;
    }

    public void setSteps(final List<RemoteStepEntity> steps) {
        mSteps = steps;
    }

    public Integer getServings() {
        return mServings;
    }

    public void setServings(final Integer servings) {
        mServings = servings;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(final String image) {
        mImage = image;
    }
}
