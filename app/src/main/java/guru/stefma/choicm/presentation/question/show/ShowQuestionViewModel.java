package guru.stefma.choicm.presentation.question.show;

import android.support.annotation.NonNull;
import java.util.List;

class ShowQuestionViewModel {

    static class AnswerViewModel {

        @NonNull
        private final String mAnswer;

        private final boolean mChecked;

        AnswerViewModel(@NonNull final String answer, final boolean checked) {
            mAnswer = answer;
            mChecked = checked;
        }

        @NonNull
        public String getAnswer() {
            return mAnswer;
        }

        public boolean isChecked() {
            return mChecked;
        }
    }

    @NonNull
    private final String mQuestionTitle;

    @NonNull
    private final String mAnswerKey;

    @NonNull
    private final List<AnswerViewModel> mAnswerViewModels;

    // TODO: Add a readOnly reason.
    // either your own question or you are not signed in.
    private final boolean mReadOnly;

    ShowQuestionViewModel(
            @NonNull final String questionTitle,
            @NonNull final String answerKey,
            @NonNull final List<AnswerViewModel> answerViewModels,
            final boolean readOnly) {
        mQuestionTitle = questionTitle;
        mAnswerKey = answerKey;
        mAnswerViewModels = answerViewModels;
        mReadOnly = readOnly;
    }

    @NonNull
    public String getQuestionTitle() {
        return mQuestionTitle;
    }

    @NonNull
    public String getAnswerKey() {
        return mAnswerKey;
    }

    @NonNull
    public List<AnswerViewModel> getAnswerViewModels() {
        return mAnswerViewModels;
    }

    public boolean isReadOnly() {
        return mReadOnly;
    }
}
