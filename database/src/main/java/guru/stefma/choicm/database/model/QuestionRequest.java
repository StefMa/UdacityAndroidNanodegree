package guru.stefma.choicm.database.model;

import android.support.annotation.NonNull;
import java.util.List;

public class QuestionRequest {

    @NonNull
    private final String mTitle;

    @NonNull
    private final List<AnswerRequest> mAnswerRequests;

    public QuestionRequest(
            @NonNull final String title,
            @NonNull final List<AnswerRequest> answerRequests) {
        mTitle = title;
        mAnswerRequests = answerRequests;
    }

    @NonNull
    public String getTitle() {
        return mTitle;
    }

    @NonNull
    public List<AnswerRequest> getAnswerRequests() {
        return mAnswerRequests;
    }
}
