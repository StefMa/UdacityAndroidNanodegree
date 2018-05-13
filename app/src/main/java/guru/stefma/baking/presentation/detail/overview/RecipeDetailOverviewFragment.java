package guru.stefma.baking.presentation.detail.overview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import guru.stefma.baking.R;
import guru.stefma.baking.presentation.model.IngredientViewModel;
import guru.stefma.baking.presentation.model.StepViewModel;
import java.util.ArrayList;
import java.util.List;
import net.grandcentrix.thirtyinch.TiFragment;
import timber.log.Timber;

public class RecipeDetailOverviewFragment
        extends TiFragment<RecipeDetailOverviewPresenter, RecipeDetailOverviewView>
        implements RecipeDetailOverviewView, StepsAdapter.OnStepClickListener {

    public interface OnStepClickListener extends StepsAdapter.OnStepClickListener {

    }

    private static final String ARG_KEY_INGREDIENTS = "ARG_KEY_INGREDIENTS";

    private static final String ARG_KEY_STEPS = "ARG_KEY_STEPS";

    public static RecipeDetailOverviewFragment newInstance(
            final List<IngredientViewModel> ingredients,
            final List<StepViewModel> steps
    ) {
        final RecipeDetailOverviewFragment fragment = new RecipeDetailOverviewFragment();
        final Bundle arguments = new Bundle();
        arguments.putParcelableArrayList(ARG_KEY_INGREDIENTS, new ArrayList<>(ingredients));
        arguments.putParcelableArrayList(ARG_KEY_STEPS, new ArrayList<>(steps));
        fragment.setArguments(arguments);
        return fragment;
    }

    @NonNull
    private final IngredientsAdapter mIngredientsAdapter = new IngredientsAdapter();

    @NonNull
    private final StepsAdapter mStepsAdapter = new StepsAdapter(this);

    @NonNull
    @Override
    public RecipeDetailOverviewPresenter providePresenter() {
        return new RecipeDetailOverviewPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(
            final LayoutInflater inflater,
            @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState
    ) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.fragment_recipe_detail_overview, container, false);

        final RecyclerView ingredientsList = view.findViewById(R.id.fragment_recipe_detail_overview_ingredients);
        ingredientsList.setAdapter(mIngredientsAdapter);
        ingredientsList.addItemDecoration(new StepItemDecoration());
        final ArrayList<IngredientViewModel> ingredients = getArguments().getParcelableArrayList(ARG_KEY_INGREDIENTS);
        mIngredientsAdapter.setData(ingredients);

        final RecyclerView stepsList = view.findViewById(R.id.fragment_recipe_detail_overview_steps);
        stepsList.setAdapter(mStepsAdapter);
        stepsList.addItemDecoration(new StepItemDecoration());
        final ArrayList<StepViewModel> steps = getArguments().getParcelableArrayList(ARG_KEY_STEPS);
        mStepsAdapter.setData(steps);

        return view;
    }

    @Override
    public void onStepClicked(@NonNull final StepViewModel stepViewModel) {
        if (getActivity() instanceof OnStepClickListener) {
            final OnStepClickListener stepClickListener = (OnStepClickListener) getActivity();
            stepClickListener.onStepClicked(stepViewModel);
        } else {
            Timber.i("Parent Activity (" + requireActivity().toString() + ") doesn't implement"
                    + OnStepClickListener.class.getSimpleName() + ". Expected?!");
        }
    }
}
