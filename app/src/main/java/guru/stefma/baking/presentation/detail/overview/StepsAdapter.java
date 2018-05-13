package guru.stefma.baking.presentation.detail.overview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import guru.stefma.baking.R;
import guru.stefma.baking.presentation.detail.overview.StepsAdapter.StepsViewHolder;
import guru.stefma.baking.presentation.model.StepViewModel;
import java.util.ArrayList;
import java.util.List;

class StepsAdapter extends RecyclerView.Adapter<StepsViewHolder> {

    interface OnStepClickListener {

        void onStepClicked(@NonNull final StepViewModel stepViewModel);

    }

    @NonNull
    private List<StepViewModel> mSteps = new ArrayList<>();

    @NonNull
    private final OnStepClickListener mStepClickListener;

    public StepsAdapter(@NonNull final OnStepClickListener stepClickListener) {
        mStepClickListener = stepClickListener;
    }

    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.item_steps, parent, false);
        return new StepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final StepsViewHolder holder, final int position) {
        final StepViewModel stepViewModel = mSteps.get(position);

        holder.itemView.setOnClickListener(v -> mStepClickListener.onStepClicked(stepViewModel));
        holder.mStepDesc.setText(stepViewModel.getStep());
    }

    @Override
    public int getItemCount() {
        return mSteps.size();
    }

    public void setData(@NonNull final List<StepViewModel> steps) {
        mSteps = steps;
        notifyDataSetChanged();
    }

    static class StepsViewHolder extends RecyclerView.ViewHolder {

        final TextView mStepDesc;

        public StepsViewHolder(final View itemView) {
            super(itemView);
            mStepDesc = itemView.findViewById(R.id.item_steps_description);
        }
    }
}
