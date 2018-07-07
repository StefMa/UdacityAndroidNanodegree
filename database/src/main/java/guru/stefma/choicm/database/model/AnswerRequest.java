package guru.stefma.choicm.database.model;

import android.support.annotation.NonNull;

public class AnswerRequest {

    @NonNull
    private final String mTitle;

    public AnswerRequest(@NonNull final String title) {
        mTitle = title;
    }

    @NonNull
    public String getTitle() {
        return mTitle;
    }
}
