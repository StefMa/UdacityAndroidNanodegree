package guru.stefma.choicm.database.internal.model;

import android.support.annotation.NonNull;
import com.google.firebase.database.IgnoreExtraProperties;
import java.util.List;

@IgnoreExtraProperties
public class AnswerWrapperInternal {

    private String mQuestionKey;

    private List<AnswerInternal> mAnswers;

    @ForFirebaseDB
    public AnswerWrapperInternal() {
    }

    public AnswerWrapperInternal(
            @NonNull final String questionKey,
            @NonNull final List<AnswerInternal> answers) {
        mQuestionKey = questionKey;
        mAnswers = answers;
    }

    @NonNull
    public String getQuestionKey() {
        return mQuestionKey;
    }

    @NonNull
    public List<AnswerInternal> getAnswers() {
        return mAnswers;
    }

    @ForFirebaseDB
    public void setQuestionKey(final String questionKey) {
        mQuestionKey = questionKey;
    }

    @ForFirebaseDB
    public void setAnswers(final List<AnswerInternal> answers) {
        mAnswers = answers;
    }
}
