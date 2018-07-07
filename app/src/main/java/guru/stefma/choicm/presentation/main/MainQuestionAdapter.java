package guru.stefma.choicm.presentation.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import guru.stefma.choicm.R;
import guru.stefma.choicm.domain.model.Question;
import guru.stefma.choicm.presentation.main.MainQuestionAdapter.MainQuestionViewHolder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class MainQuestionAdapter extends RecyclerView.Adapter<MainQuestionViewHolder> {

    interface OnQuestionClickedListener {

        void onQuestionClicked(final int position);
    }

    static class MainQuestionViewHolder extends RecyclerView.ViewHolder {

        @NonNull
        final TextView mUsername;

        @NonNull
        final TextView mDate;

        @NonNull
        final TextView mQuestion;

        MainQuestionViewHolder(final View itemView) {
            super(itemView);

            mUsername = itemView.findViewById(R.id.item_main_question_user);
            mDate = itemView.findViewById(R.id.item_main_question_date);
            mQuestion = itemView.findViewById(R.id.item_main_question_question);
        }
    }

    @NonNull
    private final OnQuestionClickedListener mOnQuestionClickedListener;

    // TODO: Create a MainQuestionViewModel which displays all the stuff
    @NonNull
    private final List<Question> mQuestions = new ArrayList<>();

    // TODO: Should be part of MainQuestionViewModel
    @NonNull
    private final SimpleDateFormat mDateFormat = new SimpleDateFormat("MM/dd/YYYY", Locale.getDefault());

    public MainQuestionAdapter(@NonNull final OnQuestionClickedListener onQuestionClickedListener) {
        mOnQuestionClickedListener = onQuestionClickedListener;
    }

    @Override
    public void onBindViewHolder(@NonNull final MainQuestionViewHolder holder, final int position) {
        final Question question = mQuestions.get(position);
        holder.mUsername.setText(question.getUsername());
        holder.mDate.setText(mDateFormat.format(question.getCreationDate()));
        holder.mQuestion.setText(question.getTitle());
        holder.itemView.setOnClickListener(view ->
                mOnQuestionClickedListener.onQuestionClicked(holder.getAdapterPosition()));
    }

    @NonNull
    @Override
    public MainQuestionViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.item_main_question, parent, false);
        return new MainQuestionViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mQuestions.size();
    }

    /**
     * Set a new list of questions to the adapter.
     *
     * This will lead to a complete {@link Adapter#notifyDataSetChanged()}
     *
     * @param questions the new questions or an empty list to delete the questions.
     */
    public void setQuestions(@NonNull final List<Question> questions) {
        mQuestions.clear();
        mQuestions.addAll(questions);
        notifyDataSetChanged();
    }
}
