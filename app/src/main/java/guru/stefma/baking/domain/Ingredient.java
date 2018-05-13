package guru.stefma.baking.domain;

public class Ingredient {

    private Float mQuantity;

    private String mMeasure;

    private String mName;

    public Ingredient(final Float quantity, final String measure, final String name) {
        mQuantity = quantity;
        mMeasure = measure;
        mName = name;
    }

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

    public String getName() {
        return mName;
    }

    public void setName(final String name) {
        mName = name;
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
