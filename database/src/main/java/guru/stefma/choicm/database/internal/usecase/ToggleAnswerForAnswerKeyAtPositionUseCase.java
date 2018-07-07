package guru.stefma.choicm.database.internal.usecase;

import android.support.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import guru.stefma.choicm.auth.Authentication;
import guru.stefma.choicm.database.internal.DatabaseReferences;
import guru.stefma.choicm.database.internal.model.AnswerInternal;
import guru.stefma.choicm.database.internal.model.AnswerWrapperInternal;
import io.reactivex.Completable;
import io.reactivex.Single;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ToggleAnswerForAnswerKeyAtPositionUseCase {

    @NonNull
    private final FirebaseDatabase mFirebaseDatabase;

    @NonNull
    private final Authentication mAuthentication;

    public ToggleAnswerForAnswerKeyAtPositionUseCase(
            @NonNull final FirebaseDatabase firebaseDatabase,
            @NonNull final Authentication authentication) {
        mFirebaseDatabase = firebaseDatabase;
        mAuthentication = authentication;
    }

    public Completable toggleAnswer(@NonNull final String answerkey, final int position, final boolean checked) {
        return Single.<List<String>>create(emitter -> {
            mFirebaseDatabase.getReference(DatabaseReferences.ANSWERS)
                    .orderByKey()
                    .equalTo(answerkey)
                    .limitToFirst(1)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                            for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                final AnswerWrapperInternal value =
                                        snapshot.getValue(AnswerWrapperInternal.class);
                                final List<AnswerInternal> answers = value.getAnswers();
                                final AnswerInternal answerInternal = answers.get(position);
                                final List<String> currentUids = answerInternal.getAnsweredUids();
                                if (currentUids == null) {
                                    emitter.onSuccess(Collections.emptyList());
                                } else {
                                    emitter.onSuccess(currentUids);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull final DatabaseError databaseError) {
                            // TODO: Think about a better exception
                            emitter.onError(databaseError.toException());
                        }
                    });
        })
                .flatMap(currentUids -> mAuthentication.getUser()
                        .map(user -> {
                            final List<String> newUids = new ArrayList<>(currentUids);
                            if (checked) {
                                newUids.add(user.getUid());
                            } else {
                                newUids.remove(user.getUid());
                            }
                            return newUids;
                        }))
                .flatMapCompletable(newUids ->
                        Completable.create(emitter -> {
                                    mFirebaseDatabase.getReference(DatabaseReferences.ANSWERS)
                                            .child(answerkey)
                                            .child("answers")
                                            .child(String.valueOf(position))
                                            .child("answeredUids")
                                            .setValue(newUids);
                                    emitter.onComplete();
                                }
                        ));
    }
}
