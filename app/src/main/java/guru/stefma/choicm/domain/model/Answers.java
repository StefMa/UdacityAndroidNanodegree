package guru.stefma.choicm.domain.model;

import android.support.annotation.NonNull;
import java.util.List;

/**
 * More or less a wrapper around a {@link java.util.List} of {@link Answer}
 * but with a additional {@link String} which represent the parent
 * key for the answers.
 */
public class Answers {

    @NonNull
    private final String mAnswerKey;

    @NonNull
    private final List<Answer> mAnswers;

    public Answers(@NonNull final String answerKey, @NonNull final List<Answer> answers) {
        mAnswerKey = answerKey;
        mAnswers = answers;
    }

    @NonNull
    public String getAnswerKey() {
        return mAnswerKey;
    }

    @NonNull
    public List<Answer> getAnswers() {
        return mAnswers;
    }
}
