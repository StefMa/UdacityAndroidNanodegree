package guru.stefma.baking.presentation.widget;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import guru.stefma.baking.R;
import guru.stefma.baking.domain.Ingredient;
import guru.stefma.baking.domain.Recipe;
import guru.stefma.baking.domain.Step;
import guru.stefma.baking.domain.di.DependencyGraph;
import guru.stefma.baking.domain.usecase.GetRecipes;
import guru.stefma.baking.domain.usecase.GetRecipes.Params;
import guru.stefma.baking.presentation.model.IngredientViewModel;
import guru.stefma.baking.presentation.model.RecipeViewModel;
import guru.stefma.baking.presentation.model.StepViewModel;
import guru.stefma.baking.presentation.overview.RecipesAdapter;
import guru.stefma.baking.presentation.overview.RecipesItemDecoration;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import toothpick.Scope;
import toothpick.Toothpick;

public class IngredientWidgetConfigureActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "guru.stefma.baking.presentation.widget.IngredientWidget";

    private static final String PREF_PREFIX_KEY = "appwidget_";

    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    @Inject
    GetRecipes mGetRecipes;

    private Disposable mLoadRecipes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_widget_configure);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId =
                    extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }

        final Scope scope = DependencyGraph.getApplicationScope();
        Toothpick.inject(this, scope);

        final RecyclerView recyclerView = findViewById(R.id.activity_ingredient_widget_configure_list);
        final RecipesAdapter recipesAdapter = new RecipesAdapter(recipe -> {
            String widgetText = buildWidgetText(recipe.getName(), recipe.getIngredients());
            WidgetStorage.saveTextWithWidgetId(this, mAppWidgetId, widgetText);

            // It is the responsibility of the configuration activity to update the app widget
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            IngredientWidget.updateAppWidget(this, appWidgetManager, mAppWidgetId);

            // Make sure we pass back the original appWidgetId
            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
            setResult(RESULT_OK, resultValue);
            finish();
        });
        recyclerView.setAdapter(recipesAdapter);
        recyclerView.addItemDecoration(new RecipesItemDecoration(1));

        mLoadRecipes = mGetRecipes.execute(new Params())
                .flatMap(recipes -> Observable.fromIterable(recipes)
                        .map(this::mapRecipeToRecipeViewModel)
                        .toList())
                .subscribe(recipes -> {
                    recipesAdapter.setData(recipes);
                }, throwable -> {
                    Toast.makeText(this, "Can't configure widget", Toast.LENGTH_LONG).show();
                    finish();
                });
    }

    private String buildWidgetText(final String name, final List<IngredientViewModel> ingredients) {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getString(R.string.ingredient_widget_title, name));
        stringBuilder.append("\n\n");
        for (final IngredientViewModel ingredient : ingredients) {
            final String text = String.format("%s: %s", ingredient.getName(), ingredient.getUnit());
            stringBuilder.append(text);
            stringBuilder.append("\n");
        }
        return stringBuilder.toString().substring(0, stringBuilder.length() - 2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLoadRecipes != null && !mLoadRecipes.isDisposed()) {
            mLoadRecipes.dispose();
        }
    }

    private RecipeViewModel mapRecipeToRecipeViewModel(final Recipe recipe) {
        final List<IngredientViewModel> ingredients = mapIngredientsToIngredientViewModels(recipe);
        final List<StepViewModel> steps = mapRemoteStepsToStepViewModels(recipe);
        return new RecipeViewModel(recipe.getName(), ingredients, steps, recipe.getImage());
    }

    private List<IngredientViewModel> mapIngredientsToIngredientViewModels(final Recipe recipe) {
        final List<Ingredient> ingredients = recipe.getIngredients();
        final List<IngredientViewModel> ingredientViewModels = new ArrayList<>(ingredients.size());
        for (final Ingredient ingredient : ingredients) {
            ingredientViewModels.add(
                    new IngredientViewModel(ingredient.getQuantity(), ingredient.getMeasure(), ingredient.getName())
            );
        }
        return ingredientViewModels;
    }

    private List<StepViewModel> mapRemoteStepsToStepViewModels(final Recipe recipe) {
        final List<Step> steps = recipe.getSteps();
        final List<StepViewModel> stepViewModels = new ArrayList<>(steps.size());
        for (final Step step : steps) {
            stepViewModels.add(
                    new StepViewModel(
                            String.valueOf(step.getId()), step.getShortDescription(),
                            step.getDescription(), step.getVideoUrl(), step.getThumbnailUrl())
            );
        }
        return stepViewModels;
    }
}

