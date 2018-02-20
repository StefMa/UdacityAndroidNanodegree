package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";

    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(@NonNull Sandwich sandwich) {
        // Image
        ImageView ingredientsIv = findViewById(R.id.image_iv);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        // Toolbar title & Ui title
        setTitle(sandwich.getMainName());
        TextView titleTv = findViewById(R.id.origin_tv);
        titleTv.setText(sandwich.getMainName());

        // Also known
        VerticalTitleDescriptionView alsoKnownTv = findViewById(R.id.also_known);
        if (sandwich.getAlsoKnownAs().isEmpty()) {
            alsoKnownTv.setVisibility(View.GONE);
        } else {
            final String alsoKnownAs = TextUtils.join(", ", sandwich.getAlsoKnownAs());
            alsoKnownTv.setDescription(alsoKnownAs);
        }

        // Ingredients
        VerticalTitleDescriptionView ingredientsTv = findViewById(R.id.ingredients);
        if (sandwich.getIngredients().isEmpty()) {
            ingredientsTv.setVisibility(View.GONE);
        } else {
            final String ingredients = TextUtils.join(", ", sandwich.getIngredients());
            ingredientsTv.setDescription(ingredients);
        }

        // Place of Origin
        VerticalTitleDescriptionView placeOfOriginTv = findViewById(R.id.place_of_origin);
        if (TextUtils.isEmpty(sandwich.getPlaceOfOrigin())) {
            placeOfOriginTv.setVisibility(View.GONE);
        } else {
            placeOfOriginTv.setDescription(sandwich.getPlaceOfOrigin());
        }

        // Description
        VerticalTitleDescriptionView descTv = findViewById(R.id.description);
        if (TextUtils.isEmpty(sandwich.getDescription())) {
            descTv.setVisibility(View.GONE);
        } else {
            descTv.setDescription(sandwich.getDescription());
        }
    }
}
