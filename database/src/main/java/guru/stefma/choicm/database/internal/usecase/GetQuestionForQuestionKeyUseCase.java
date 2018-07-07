package guru.stefma.choicm.database.internal.usecase;

import android.support.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import guru.stefma.choicm.database.internal.DatabaseReferences;
import guru.stefma.choicm.database.internal.model.QuestionInternal;
import guru.stefma.choicm.database.model.QuestionResponse;
import io.reactivex.Single;

/**
 * Get a single {@link guru.stefma.choicm.database.model.QuestionResponse} for
 * a given <b>question key</b>.
 */
public class GetQuestionForQuestionKeyUseCase {

    @NonNull
    private final FirebaseDatabase mFirebaseDatabase;

    public GetQuestionForQuestionKeyUseCase(@NonNull final FirebaseDatabase firebaseDatabase) {
        mFirebaseDatabase = firebaseDatabase;
    }

    public Single<QuestionResponse> getQuestionForQuestionKey(
            @NonNull final String questionKey) {
        return Single.create(
                emitter -> mFirebaseDatabase.getReference(DatabaseReferences.QUESTIONS)
                        .orderByKey()
                        .equalTo(questionKey)
                        .limitToFirst(1)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                                for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    // Since we limit it to only one
                                    // It should be only one :)
                                    final QuestionInternal value = snapshot.getValue(QuestionInternal.class);
                                    final QuestionResponse response = new QuestionResponse(
                                            questionKey, value.getTitle(), value.getUsername(),
                                            value.getCreationDate());
                                    emitter.onSuccess(response);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull final DatabaseError databaseError) {
                                // TODO: Think about a better exception
                                emitter.onError(databaseError.toException());
                            }
                        }));
    }
}
