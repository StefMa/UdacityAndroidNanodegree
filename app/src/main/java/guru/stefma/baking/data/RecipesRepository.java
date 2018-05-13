package guru.stefma.baking.data;

import guru.stefma.baking.data.remote.entity.RemoteRecipeEntity;
import io.reactivex.Single;
import java.util.List;

public interface RecipesRepository {

    Single<List<RemoteRecipeEntity>> getRecipes();

}
