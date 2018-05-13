package guru.stefma.baking.data.remote.entity;

import com.squareup.moshi.Json;

public class RemoteIngredientEntity {

    @Json(name = "quantity")
    private Float mQuantity;

    @Json(name = "measure")
    private String mMeasure;

    @Json(name = "ingredient")
    private String mIngredient;

    public Float getQuantity() {
        return mQuantity;
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

    public String getIngredient() {
        return mIngredient;
    }

    public void setIngredient(final String ingredient) {
        mIngredient = ingredient;
    }
}
