package guru.stefma.choicm.domain.model;

import android.support.annotation.NonNull;
import java.util.List;

/**
 * An answer which holds the answer directly ({@link #getTitle()})
 * and a {@link List} of "ids" by users who has checked/answered
 * these specific answer.
 */
public class Answer {

    @NonNull
    private final String mTitle;

    @NonNull
    private final List<String> mAnsweredUids;

    public Answer(@NonNull final String title, @NonNull final List<String> answeredUids) {
        mTitle = title;
        mAnsweredUids = answeredUids;
    }

    @NonNull
    public String getTitle() {
        return mTitle;
    }

    @NonNull
    public List<String> getAnsweredUids() {
        return mAnsweredUids;
    }
}
