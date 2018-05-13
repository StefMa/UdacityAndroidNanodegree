package guru.stefma.baking.presentation.overview;

import android.support.annotation.NonNull;
import guru.stefma.baking.domain.Ingredient;
import guru.stefma.baking.domain.Recipe;
import guru.stefma.baking.domain.Step;
import guru.stefma.baking.domain.usecase.GetRecipes;
import guru.stefma.baking.domain.usecase.GetRecipes.Params;
import guru.stefma.baking.presentation.model.IngredientViewModel;
import guru.stefma.baking.presentation.model.RecipeViewModel;
import guru.stefma.baking.presentation.model.StepViewModel;
import io.reactivex.Observable;
import java.util.ArrayList;
import java.util.List;
import net.grandcentrix.thirtyinch.TiPresenter;
import net.grandcentrix.thirtyinch.rx2.RxTiPresenterDisposableHandler;
import timber.log.Timber;

class MainPresenter extends TiPresenter<MainView> {

    @NonNull
    private final GetRecipes mGetRecipes;

    private RxTiPresenterDisposableHandler mDisposableHandler = new RxTiPresenterDisposableHandler(this);

    public MainPresenter(final GetRecipes getRecipes) {
        mGetRecipes = getRecipes;
    }

    @Override
    protected void onAttachView(@NonNull final MainView view) {
        super.onAttachView(view);

        mDisposableHandler.manageViewDisposable(mGetRecipes.execute(new Params())
                .flatMap(recipes -> Observable.fromIterable(recipes)
                        .map(this::mapRecipeToRecipeViewModel)
                        .toList())
                .subscribe(recipes -> {
                    Timber.d("Got recipes" + recipes.toString());
                    sendToView(mainView -> {
                        mainView.setData(recipes);
                    });
                }, throwable -> Timber.e(throwable, "Something went wrong by getting recipes :/")));
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
