package guru.stefma.baking.presentation.detail.overview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import guru.stefma.baking.R;
import guru.stefma.baking.presentation.detail.overview.IngredientsAdapter.IngredientsViewHolder;
import guru.stefma.baking.presentation.model.IngredientViewModel;
import java.util.ArrayList;
import java.util.List;

class IngredientsAdapter extends RecyclerView.Adapter<IngredientsViewHolder> {

    @NonNull
    private List<IngredientViewModel> mIngredients = new ArrayList<>();

    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.item_ingredients, parent, false);
        return new IngredientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final IngredientsViewHolder holder, final int position) {
        final IngredientViewModel ingredient = mIngredients.get(position);
        holder.mName.setText(ingredient.getName());
        holder.mUnit.setText(ingredient.getUnit());
    }

    @Override
    public int getItemCount() {
        return mIngredients.size();
    }

    public void setData(@NonNull final List<IngredientViewModel> ingredients) {
        mIngredients = ingredients;
        notifyDataSetChanged();
    }

    static class IngredientsViewHolder extends RecyclerView.ViewHolder {

        final TextView mName;

        final TextView mUnit;

        public IngredientsViewHolder(final View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.item_ingredients_name);
            mUnit = itemView.findViewById(R.id.item_ingredients_unit);
        }
    }
}
