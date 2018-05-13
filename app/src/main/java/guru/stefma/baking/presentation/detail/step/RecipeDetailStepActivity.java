package guru.stefma.baking.presentation.detail.step;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import guru.stefma.baking.R;
import guru.stefma.baking.presentation.model.StepViewModel;

public class RecipeDetailStepActivity extends AppCompatActivity {

    private static final String BUNDLE_KEY_STEP = "bundle::key::step";

    public static Intent newInstance(@NonNull final Context context, @NonNull final StepViewModel stepViewModel) {
        final Intent intent = new Intent(context, RecipeDetailStepActivity.class);
        intent.putExtra(BUNDLE_KEY_STEP, stepViewModel);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail_step);

        if (savedInstanceState == null) {
            final RecipeDetailStepFragment stepFragment = (RecipeDetailStepFragment)
                    getSupportFragmentManager().findFragmentById(R.id.activity_detail_step_container);
            stepFragment.setStep(getIntent().getParcelableExtra(BUNDLE_KEY_STEP), null);
        }
    }
}
