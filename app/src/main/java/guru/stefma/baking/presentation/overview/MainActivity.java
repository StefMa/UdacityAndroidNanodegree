package guru.stefma.baking.presentation.overview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import guru.stefma.baking.R;
import guru.stefma.baking.domain.di.DependencyGraph;
import guru.stefma.baking.domain.usecase.GetRecipes;
import guru.stefma.baking.presentation.detail.RecipeDetailActivity;
import guru.stefma.baking.presentation.model.RecipeViewModel;
import guru.stefma.baking.presentation.overview.RecipesAdapter.OnRecipeClickListener;
import java.util.List;
import javax.inject.Inject;
import net.grandcentrix.thirtyinch.TiActivity;
import toothpick.Scope;
import toothpick.Toothpick;

public class MainActivity extends TiActivity<MainPresenter, MainView> implements MainView, OnRecipeClickListener {

    @Inject
    GetRecipes mGetRecipes;

    private RecipesAdapter mRecipesAdapter = new RecipesAdapter(this);

    @NonNull
    @Override
    public MainPresenter providePresenter() {
        final Scope scope = DependencyGraph.getApplicationScope();
        Toothpick.inject(this, scope);
        return new MainPresenter(mGetRecipes);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar();
        setupRecipesList();
    }

    private void setupToolbar() {
        final Toolbar toolbar = findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupRecipesList() {
        final RecyclerView recyclerView = findViewById(R.id.activity_main_recipes_list);
        final GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
        final int spanCount = getResources().getInteger(R.integer.recipes_list_item_count);
        layoutManager.setSpanCount(spanCount);
        recyclerView.setAdapter(mRecipesAdapter);
        recyclerView.addItemDecoration(new RecipesItemDecoration(spanCount));
    }

    @Override
    public void setData(@NonNull final List<RecipeViewModel> recipes) {
        mRecipesAdapter.setData(recipes);
    }

    /**
     * Listener which got called by the {@link RecipesAdapter} if a recipe got clicked.
     */
    @Override
    public void onRecipeClicked(@NonNull final RecipeViewModel recipe) {
        startActivity(RecipeDetailActivity.newInstance(this, recipe));
    }
}
