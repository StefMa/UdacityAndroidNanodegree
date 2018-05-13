package guru.stefma.baking.presentation.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import guru.stefma.baking.R;
import guru.stefma.baking.presentation.detail.overview.RecipeDetailOverviewFragment;
import guru.stefma.baking.presentation.detail.overview.RecipeDetailOverviewFragment.OnStepClickListener;
import guru.stefma.baking.presentation.detail.step.RecipeDetailStepActivity;
import guru.stefma.baking.presentation.detail.step.RecipeDetailStepFragment;
import guru.stefma.baking.presentation.model.RecipeViewModel;
import guru.stefma.baking.presentation.model.StepViewModel;
import net.grandcentrix.thirtyinch.TiActivity;

public class RecipeDetailActivity extends TiActivity<RecipeDetailPresenter, RecipeDetailView>
        implements RecipeDetailView, OnStepClickListener {

    private static final String BUNDLE_KEY_RECIPE = "BUNDLE_KEY_RECIPE";

    private static final String TAG_OVERVIEW_FRAGMENT = RecipeDetailOverviewFragment.class.getName();

    private static final String TAG_STEP_FRAGMENT = RecipeDetailStepFragment.class.getName();

    public static Intent newInstance(@NonNull final Context context, @NonNull final RecipeViewModel recipe) {
        final Intent intent = new Intent(context, RecipeDetailActivity.class);
        intent.putExtra(BUNDLE_KEY_RECIPE, recipe);
        return intent;
    }

    private boolean mIsPhone = true;

    @NonNull
    @Override
    public RecipeDetailPresenter providePresenter() {
        return new RecipeDetailPresenter();
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        mIsPhone = getResources().getBoolean(R.bool.is_phone);

        final Toolbar toolbar = findViewById(R.id.activity_recipe_detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            final RecipeViewModel recipe = getIntent().getParcelableExtra(BUNDLE_KEY_RECIPE);
            if (mIsPhone) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.activity_recipe_detail_overview_container,
                                RecipeDetailOverviewFragment.newInstance(recipe.getIngredients(), recipe.getSteps()),
                                TAG_OVERVIEW_FRAGMENT)
                        .commitNow();
            } else {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.activity_recipe_detail_overview_container,
                                RecipeDetailOverviewFragment.newInstance(recipe.getIngredients(), recipe.getSteps()),
                                TAG_OVERVIEW_FRAGMENT)
                        .add(R.id.activity_recipe_detail_step_container,
                                RecipeDetailStepFragment.newInstance(), // TODO: Add empty fragment?!
                                TAG_STEP_FRAGMENT)
                        .commitNow();
            }
        }
    }

    /**
     * Got called if a step from the {@link RecipeDetailOverviewFragment} is clicked.
     */
    @Override
    public void onStepClicked(@NonNull final StepViewModel stepViewModel) {
        if (mIsPhone) {
            // Phone. Call the StepDetailActivity
            startActivity(RecipeDetailStepActivity.newInstance(this, stepViewModel));
        } else {
            // Tablet. Put the Step inside
            final Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG_STEP_FRAGMENT);
            if (fragment != null) {
                RecipeDetailStepFragment stepFragment = (RecipeDetailStepFragment) fragment;
                stepFragment.setStep(stepViewModel, null);
            }
        }
    }
}
