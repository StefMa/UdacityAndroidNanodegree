package guru.stefma.choicm.database.internal.usecase;

import android.support.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import guru.stefma.choicm.database.internal.DatabaseReferences;
import guru.stefma.choicm.database.internal.model.AnswerInternal;
import guru.stefma.choicm.database.internal.model.AnswerWrapperInternal;
import guru.stefma.choicm.database.model.AnswerResponse;
import guru.stefma.choicm.database.model.AnswersResponse;
import io.reactivex.Single;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GetAnswersForQuestionKeyUseCase {

    @NonNull
    private final FirebaseDatabase mFirebaseDatabase;

    public GetAnswersForQuestionKeyUseCase(@NonNull final FirebaseDatabase firebaseDatabase) {
        mFirebaseDatabase = firebaseDatabase;
    }

    public Single<AnswersResponse> getAnswersForQuestionKey(
            @NonNull final String questionKey) {
        return Single.create(
                emitter -> mFirebaseDatabase.getReference(DatabaseReferences.ANSWERS)
                        .orderByChild("questionKey")
                        .equalTo(questionKey)
                        .limitToFirst(1)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                                for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    final AnswerWrapperInternal value =
                                            snapshot.getValue(AnswerWrapperInternal.class);
                                    final List<AnswerResponse> answerResonse = new ArrayList<>();
                                    for (final AnswerInternal answerInternal : value.getAnswers()) {
                                        final List<String> answeredUids = answerInternal.getAnsweredUids();
                                        answerResonse.add(new AnswerResponse(
                                                answerInternal.getTitle(),
                                                answeredUids != null ? answeredUids : Collections.emptyList()));
                                    }
                                    emitter.onSuccess(new AnswersResponse(snapshot.getKey(), answerResonse));
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
