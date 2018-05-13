package guru.stefma.baking.domain.usecase;

import android.support.annotation.NonNull;
import guru.stefma.baking.data.RecipesRepository;
import guru.stefma.baking.data.remote.entity.RemoteIngredientEntity;
import guru.stefma.baking.data.remote.entity.RemoteRecipeEntity;
import guru.stefma.baking.data.remote.entity.RemoteStepEntity;
import guru.stefma.baking.domain.Ingredient;
import guru.stefma.baking.domain.Recipe;
import guru.stefma.baking.domain.Step;
import io.reactivex.Observable;
import io.reactivex.Single;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class GetRecipesUseCase implements GetRecipes {

    @Inject
    RecipesRepository mRecipesRepository;

    @Override
    public Single<List<Recipe>> buildUseCase(@NonNull final Params params) {
        return mRecipesRepository.getRecipes()
                .flatMap(remoteRecipeEntities -> {
                    return Observable.fromIterable(remoteRecipeEntities)
                            .map(this::mapRemoteRecipeToRecipe)
                            .toList();
                });
    }

    private Recipe mapRemoteRecipeToRecipe(final RemoteRecipeEntity remoteRecipeEntity) {
        final List<Ingredient> ingredients = mapRemoteIngredientsToIngredients(remoteRecipeEntity);
        final List<Step> steps = mapRemoteStepsToSteps(remoteRecipeEntity);
        return new Recipe(remoteRecipeEntity.getName(), ingredients, steps, remoteRecipeEntity.getImage());
    }

    private List<Ingredient> mapRemoteIngredientsToIngredients(final RemoteRecipeEntity remoteRecipeEntity) {
        final List<RemoteIngredientEntity> remoteIngredients = remoteRecipeEntity.getIngredients();
        final List<Ingredient> ingredients = new ArrayList<>(remoteIngredients.size());
        for (final RemoteIngredientEntity ingredient : remoteIngredients) {
            ingredients.add(
                    new Ingredient(ingredient.getQuantity(), ingredient.getMeasure(), ingredient.getIngredient())
            );
        }
        return ingredients;
    }

    private List<Step> mapRemoteStepsToSteps(final RemoteRecipeEntity remoteRecipeEntity) {
        final List<RemoteStepEntity> remoteSteps = remoteRecipeEntity.getSteps();
        final List<Step> steps = new ArrayList<>(remoteSteps.size());
        for (final RemoteStepEntity step : remoteSteps) {
            steps.add(new Step(
                    step.getId(), step.getShortDescription(), step.getDescription(), step.getVideoUrl(),
                    step.getThumbnailUrl()));
        }
        return steps;
    }

}
