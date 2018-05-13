package guru.stefma.baking.presentation.overview;

import android.support.annotation.NonNull;
import guru.stefma.baking.presentation.model.RecipeViewModel;
import java.util.List;
import net.grandcentrix.thirtyinch.TiView;

interface MainView extends TiView {

    void setData(@NonNull final List<RecipeViewModel> recipes);

}
