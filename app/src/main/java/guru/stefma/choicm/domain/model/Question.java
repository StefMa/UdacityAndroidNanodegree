package guru.stefma.choicm.domain.model;

import android.support.annotation.NonNull;
import java.util.Date;

/**
 * A question is a question without any possible answers.
 */
public class Question {

    @NonNull
    private final String mKey;

    @NonNull
    private final String mTitle;

    @NonNull
    private final String mUsername;

    @NonNull
    private final Date mCreationDate;

    public Question(
            @NonNull final String key,
            @NonNull final String title,
            @NonNull final String username,
            @NonNull final Date creationDate) {
        mKey = key;
        mTitle = title;
        mUsername = username;
        mCreationDate = creationDate;
    }

    @NonNull
    public String getKey() {
        return mKey;
    }

    @NonNull
    public String getTitle() {
        return mTitle;
    }

    @NonNull
    public String getUsername() {
        return mUsername;
    }

    @NonNull
    public Date getCreationDate() {
        return mCreationDate;
    }
}
