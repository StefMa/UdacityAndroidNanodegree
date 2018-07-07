package guru.stefma.choicm.database.model;

import android.support.annotation.NonNull;
import java.util.List;

public class AnswersResponse {

    @NonNull
    private final String mAnswerKey;

    @NonNull
    private final List<AnswerResponse> mAnswers;

    public AnswersResponse(
            @NonNull final String answerKey,
            @NonNull final List<AnswerResponse> answers) {
        mAnswerKey = answerKey;
        mAnswers = answers;
    }

    @NonNull
    public String getAnswerKey() {
        return mAnswerKey;
    }

    @NonNull
    public List<AnswerResponse> getAnswers() {
        return mAnswers;
    }
}
