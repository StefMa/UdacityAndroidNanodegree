package guru.stefma.baking.data.remote;

import guru.stefma.baking.data.remote.entity.RemoteRecipeEntity;
import io.reactivex.Single;
import java.util.List;
import retrofit2.http.GET;

public interface ApiService {

    @GET("/android-baking-app-json")
    Single<List<RemoteRecipeEntity>> getRecipes();

}
