package guru.stefma.choicm.database.model;

import android.support.annotation.NonNull;
import java.util.List;

public class AnswerResponse {

    @NonNull
    private final String mTitle;

    @NonNull
    private final List<String> mAnsweredUids;

    public AnswerResponse(@NonNull final String title, @NonNull final List<String> answeredUids) {
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
