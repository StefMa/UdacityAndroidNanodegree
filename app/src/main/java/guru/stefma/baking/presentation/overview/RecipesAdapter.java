package guru.stefma.baking.presentation.overview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import guru.stefma.baking.R;
import guru.stefma.baking.presentation.model.RecipeViewModel;
import guru.stefma.baking.presentation.overview.RecipesAdapter.RecipesViewHolder;
import java.util.ArrayList;
import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesViewHolder> {

    public interface OnRecipeClickListener {

        void onRecipeClicked(@NonNull RecipeViewModel recipe);

    }

    @NonNull
    private List<RecipeViewModel> mRecipes = new ArrayList<>();

    @NonNull
    private final OnRecipeClickListener mOnRecipeClickListener;

    public RecipesAdapter(@NonNull final OnRecipeClickListener clickListener) {
        mOnRecipeClickListener = clickListener;
    }

    @NonNull
    @Override
    public RecipesViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.item_recipes, parent, false);
        return new RecipesViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    @Override
    public void onBindViewHolder(@NonNull final RecipesViewHolder holder, final int position) {
        final RecipeViewModel recipe = mRecipes.get(position);

        holder.itemView.setOnClickListener(view -> mOnRecipeClickListener.onRecipeClicked(recipe));

        final ImageView recipeImage = holder.mRecipeImage;
        Glide.with(recipeImage)
                .load(recipe.getImage())
                .apply(new RequestOptions().placeholder(R.drawable.img_recipes_placeholder))
                .into(recipeImage);

        holder.mRecipeName.setText(recipe.getName());
    }

    public void setData(@NonNull final List<RecipeViewModel> recipes) {
        mRecipes = recipes;
        notifyDataSetChanged();
    }

    static class RecipesViewHolder extends ViewHolder {

        final ImageView mRecipeImage;

        final TextView mRecipeName;

        public RecipesViewHolder(final View itemView) {
            super(itemView);
            mRecipeImage = itemView.findViewById(R.id.item_recipes_image);
            mRecipeName = itemView.findViewById(R.id.item_recipes_name);
        }
    }

}

