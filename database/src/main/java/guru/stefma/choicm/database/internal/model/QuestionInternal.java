package guru.stefma.choicm.database.internal.model;

import android.support.annotation.NonNull;
import com.google.firebase.database.IgnoreExtraProperties;
import java.util.Date;

@IgnoreExtraProperties
public class QuestionInternal {

    private String mTitle;

    @NonNull
    private Date mCreationDate = new Date();

    private String mUsername;

    @ForFirebaseDB
    public QuestionInternal() {
    }

    public QuestionInternal(
            @NonNull final String title,
            @NonNull final String username) {
        mTitle = title;
        mUsername = username;
    }

    @NonNull
    public String getTitle() {
        return mTitle;
    }

    @NonNull
    public Date getCreationDate() {
        return mCreationDate;
    }

    @NonNull
    public String getUsername() {
        return mUsername;
    }

    @ForFirebaseDB
    public void setTitle(final String title) {
        mTitle = title;
    }

    @ForFirebaseDB
    public void setCreationDate(@NonNull final Date creationDate) {
        mCreationDate = creationDate;
    }

    @ForFirebaseDB
    public void setUsername(final String username) {
        mUsername = username;
    }
}
