package guru.stefma.choicm.presentation.question.add;

import android.support.annotation.NonNull;
import guru.stefma.choicm.presentation.question.add.AddAnswerView.State;

public class AddQuestionAnswerViewModel {

    static AddQuestionAnswerViewModel createEmptyViewModel() {
        return new AddQuestionAnswerViewModel("", State.DISABLED);
    }

    @NonNull
    private final String mAnswer;

    @NonNull
    private final AddAnswerView.State mState;

    AddQuestionAnswerViewModel(@NonNull final String answer, @NonNull final State state) {
        mAnswer = answer;
        mState = state;
    }

    @NonNull
    public String getAnswer() {
        return mAnswer;
    }

    @NonNull
    public State getState() {
        return mState;
    }
}
