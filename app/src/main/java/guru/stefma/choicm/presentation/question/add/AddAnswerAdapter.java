package guru.stefma.choicm.presentation.question.add;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import guru.stefma.choicm.R;
import guru.stefma.choicm.presentation.question.add.AddAnswerAdapter.AnswerViewHolder;
import guru.stefma.choicm.presentation.question.add.AddAnswerView.OnViewChangedListener;
import guru.stefma.choicm.presentation.question.add.AddAnswerView.State;
import java.util.ArrayList;
import java.util.List;

class AddAnswerAdapter extends RecyclerView.Adapter<AnswerViewHolder> {

    static class AnswerViewHolder extends RecyclerView.ViewHolder {

        @NonNull
        final AddAnswerView mAnswerView;

        AnswerViewHolder(final View itemView) {
            super(itemView);
            mAnswerView = (AddAnswerView) itemView;
        }
    }

    @NonNull
    private final List<AddQuestionAnswerViewModel> mViewModels = new ArrayList<>();

    AddAnswerAdapter() {
        // Add first disabled item
        // TODO: The ViewModels should be set from the outer world.
        // Move all logic out of this Adapter.
        // Also the clicklistener somehow.
        mViewModels.add(AddQuestionAnswerViewModel.createEmptyViewModel());
    }

    @NonNull
    @Override
    public AnswerViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.item_add_answer, parent, false);
        return new AnswerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AnswerViewHolder holder, final int position) {
        holder.mAnswerView.setText(mViewModels.get(position).getAnswer());
        holder.mAnswerView.setState(mViewModels.get(position).getState());
        holder.mAnswerView.setViewChangedListener(new OnViewChangedListener() {

            private final int adapterPosition = holder.getAdapterPosition();

            @Override
            public void onTextChanged(@NonNull final String text) {
                final AddQuestionAnswerViewModel currentViewModel = mViewModels.get(adapterPosition);
                final AddQuestionAnswerViewModel newViewModel =
                        new AddQuestionAnswerViewModel(text, currentViewModel.getState());
                mViewModels.set(adapterPosition, newViewModel);
            }

            @Override
            public void onStateChanged(@NonNull final State state) {
                if (state == State.ENABLED) {
                    // Update the current item...
                    final AddQuestionAnswerViewModel currentViewModel = mViewModels.get(adapterPosition);
                    final AddQuestionAnswerViewModel newViewModel =
                            new AddQuestionAnswerViewModel(currentViewModel.getAnswer(), state);
                    mViewModels.set(adapterPosition, newViewModel);

                    // ...and add a new view
                    mViewModels.add(AddQuestionAnswerViewModel.createEmptyViewModel());
                    notifyItemInserted(adapterPosition + 1);
                    return;
                }

                if (state == State.DISABLED) {
                    // Remove the view
                    mViewModels.remove(adapterPosition);
                    notifyItemRemoved(adapterPosition);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mViewModels.size();
    }

    @NonNull
    public List<AddQuestionAnswerViewModel> getViewModels() {
        return mViewModels;
    }

    public void setViewModels(@NonNull final List<AddQuestionAnswerViewModel> viewModels) {
        mViewModels.clear();
        mViewModels.addAll(viewModels);
        notifyDataSetChanged();
    }
}
