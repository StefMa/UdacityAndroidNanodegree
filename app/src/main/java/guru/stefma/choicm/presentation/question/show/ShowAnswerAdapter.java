package guru.stefma.choicm.presentation.question.show;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import guru.stefma.choicm.R;
import guru.stefma.choicm.presentation.question.show.ShowAnswerAdapter.ShowAnswerViewHolder;
import guru.stefma.choicm.presentation.question.show.ShowQuestionViewModel.AnswerViewModel;
import java.util.ArrayList;
import java.util.List;

// TODO: Document readOnly
public class ShowAnswerAdapter extends RecyclerView.Adapter<ShowAnswerViewHolder> {

    interface OnAnswerCheckedChanged {

        void onAnswerChanged(int position, boolean checked);

    }

    class ShowAnswerViewHolder extends RecyclerView.ViewHolder {

        @NonNull
        final ShowAnswerView mShowAnswerView;

        ShowAnswerViewHolder(final View itemView) {
            super(itemView);
            mShowAnswerView = (ShowAnswerView) itemView;
        }
    }

    @NonNull
    private final OnAnswerCheckedChanged mOnAnswerCheckedChanged;

    @NonNull
    private final List<AnswerViewModel> mViewModels = new ArrayList<>();

    private boolean mReadOnly = true;

    ShowAnswerAdapter(@NonNull final OnAnswerCheckedChanged onAnswerCheckedChanged) {
        mOnAnswerCheckedChanged = onAnswerCheckedChanged;
    }

    @NonNull
    @Override
    public ShowAnswerViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.item_show_answer, parent, false);
        return new ShowAnswerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ShowAnswerViewHolder holder, final int position) {
        final AnswerViewModel viewModel = mViewModels.get(position);
        holder.mShowAnswerView.setText(viewModel.getAnswer());
        holder.mShowAnswerView.setChecked(viewModel.isChecked());
        holder.mShowAnswerView.setOnCheckedChangeListener((compoundButton, checked) ->
                mOnAnswerCheckedChanged.onAnswerChanged(holder.getAdapterPosition(), checked));
        holder.mShowAnswerView.setReadOnly(mReadOnly);
    }

    @Override
    public int getItemCount() {
        return mViewModels.size();
    }

    public void setViewModels(@NonNull final List<AnswerViewModel> viewModels) {
        mViewModels.clear();
        mViewModels.addAll(viewModels);
        notifyDataSetChanged();
    }

    public void setReadOnly(final boolean readOnly) {
        mReadOnly = readOnly;
        notifyDataSetChanged();
    }
}
