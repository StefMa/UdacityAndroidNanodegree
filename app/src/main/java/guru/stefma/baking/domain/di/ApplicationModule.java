package guru.stefma.baking.domain.di;

import android.content.Context;
import guru.stefma.baking.data.RecipesRepository;
import guru.stefma.baking.data.RecipesRepositoryImpl;
import guru.stefma.baking.data.remote.RetrofitService;
import guru.stefma.baking.domain.usecase.GetRecipes;
import guru.stefma.baking.domain.usecase.GetRecipesUseCase;
import toothpick.config.Module;

public class ApplicationModule extends Module {

    public ApplicationModule(final Context context) {
        // Bind a singleton instance of the RecipesRepository
        bind(RecipesRepository.class).toInstance(new RecipesRepositoryImpl(RetrofitService.getApiService()));

        // Bind a single instance of the GetRecipesUseCase
        bind(GetRecipes.class).to(GetRecipesUseCase.class);
    }

}
