package guru.stefma.choicm.database.internal.model;

import android.support.annotation.NonNull;
import com.google.firebase.database.IgnoreExtraProperties;
import java.util.List;

@IgnoreExtraProperties
public class AnswerInternal {

    @ForFirebaseDB
    private String mTitle;

    @ForFirebaseDB
    private List<String> mAnsweredUids;

    @ForFirebaseDB
    public AnswerInternal() {
    }

    public AnswerInternal(@NonNull final String title, @NonNull final List<String> answeredUids) {
        mTitle = title;
        mAnsweredUids = answeredUids;
    }

    @NonNull
    public String getTitle() {
        return mTitle;
    }

    @ForFirebaseDB
    public void setTitle(final String title) {
        mTitle = title;
    }

    public List<String> getAnsweredUids() {
        return mAnsweredUids;
    }

    @ForFirebaseDB
    public void setAnsweredUids(final List<String> answeredUids) {
        mAnsweredUids = answeredUids;
    }
}
