package guru.stefma.baking.domain.usecase;

import guru.stefma.baking.domain.Recipe;
import guru.stefma.baking.domain.usecase.GetRecipes.Params;
import guru.stefma.baking.domain.usecase.base.BaseSingleUseCase;
import java.util.List;

/**
 * Just a marker interface for injecting
 */
public interface GetRecipes extends BaseSingleUseCase<List<Recipe>, Params> {

    /**
     * The params for the {@link GetRecipesUseCase}
     */
    class Params {

    }

}
