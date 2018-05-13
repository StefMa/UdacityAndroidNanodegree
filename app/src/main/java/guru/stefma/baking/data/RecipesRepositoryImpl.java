package guru.stefma.baking.data;

import guru.stefma.baking.data.remote.ApiService;
import guru.stefma.baking.data.remote.entity.RemoteRecipeEntity;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import java.util.List;

public class RecipesRepositoryImpl implements RecipesRepository {

    @NonNull
    private final ApiService mApiService;

    public RecipesRepositoryImpl(@NonNull final ApiService apiService) {
        mApiService = apiService;
    }

    @Override
    public Single<List<RemoteRecipeEntity>> getRecipes() {
        return mApiService.getRecipes();
    }

}
