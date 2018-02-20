package com.udacity.sandwichclub.utils;

import static org.assertj.core.api.Assertions.*;

import android.support.test.runner.AndroidJUnit4;
import com.udacity.sandwichclub.model.Sandwich;
import org.junit.*;
import org.junit.runner.*;

@RunWith(AndroidJUnit4.class)
public class JsonUtilsTest {

    @Test
    public void testParseCorrectly() throws Exception {
        final String jsonString = "{\n"
                + "\t\"name\": {\n"
                + "\t\t\"mainName\": \"Ham and cheese sandwich\",\n"
                + "\t\t\"alsoKnownAs\": []\n"
                + "\t},\n"
                + "\t\"placeOfOrigin\": \"\",\n"
                + "\t\"description\": \"A ham and cheese sandwich is a common type of sandwich. It is made by putting cheese and sliced ham between two slices of bread.The bread is sometimes buttered and / or toasted.Vegetables like lettuce, tomato, onion or pickle slices can also be included.Various kinds of mustard and mayonnaise are also common.\",\n"
                + "\t\"image\": \"https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Grilled_ham_and_cheese_014.JPG/800px-Grilled_ham_and_cheese_014.JPG\",\n"
                + "\t\"ingredients\": [\"Sliced bread\", \"Cheese\", \"Ham\"]\n"
                + "}";
        final Sandwich sandwich = JsonUtils.parseSandwichJson(jsonString);

        assertThat(sandwich).isNotNull();
        assertThat(sandwich.getMainName()).contains("Ham and cheese");
        assertThat(sandwich.getAlsoKnownAs()).isEmpty();
        assertThat(sandwich.getDescription()).contains("A ham and cheese", "tomato, onion");
        assertThat(sandwich.getImage()).isEqualTo(
                "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Grilled_ham_and_cheese_014.JPG/800px-Grilled_ham_and_cheese_014.JPG");
        assertThat(sandwich.getPlaceOfOrigin()).isEmpty();
        assertThat(sandwich.getIngredients()).contains("Sliced bread", "Cheese", "Ham");
    }
}