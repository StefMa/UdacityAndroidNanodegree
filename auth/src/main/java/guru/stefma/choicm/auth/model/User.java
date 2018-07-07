package guru.stefma.choicm.auth.model;

import android.support.annotation.NonNull;

public class User {

    @NonNull
    private final String mUid;

    @NonNull
    private final String mUsername;

    public User(@NonNull final String uid, @NonNull final String username) {
        mUid = uid;
        mUsername = username;
    }

    @NonNull
    public String getUid() {
        return mUid;
    }

    @NonNull
    public String getUsername() {
        return mUsername;
    }
}
