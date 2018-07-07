package guru.stefma.choicm.database.internal.usecase;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import guru.stefma.choicm.database.internal.DatabaseReferences;
import guru.stefma.choicm.database.internal.model.QuestionInternal;
import guru.stefma.choicm.database.model.QuestionRequest;
import guru.stefma.choicm.database.model.QuestionResponse;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.subjects.BehaviorSubject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Get all {@link guru.stefma.choicm.database.model.QuestionResponse} from the {@link FirebaseDatabase}
 * by calling {@link #getQuestionRequests()}.
 *
 * The returned {@link Observable} of {@link #getQuestionRequests()} will
 * <b>always</b> emit 100 {@link QuestionRequest}s (if available).
 * But you can request more by calling {@link #loadMore()}.
 */
public class GetQuestionsUseCase {

    @NonNull
    private final BehaviorSubject<List<QuestionResponse>> mListQuestionObserver = BehaviorSubject.create();

    @NonNull
    private final List<QuestionResponse> mQuestionRequests = new ArrayList<>();

    @NonNull
    private final FirebaseDatabase mFirebaseDatabase;

    public GetQuestionsUseCase(@NonNull final FirebaseDatabase firebaseDatabase) {
        mFirebaseDatabase = firebaseDatabase;
    }

    public Observable<List<QuestionResponse>> getQuestionRequests() {
        loadMore();
        return mListQuestionObserver;
    }

    @SuppressLint("CheckResult")
    public void loadMore() {
        // TODO: load 100 more items based on the last recent loaded items
        getQuestions()
                .subscribe(questions -> {
                    Collections.sort(questions, Collections.reverseOrder(
                            (r1, r2) -> r1.getCreationDate().compareTo(r2.getCreationDate())));
                    // TODO: Needs be changed when loadmore works
                    mQuestionRequests.clear();
                    mQuestionRequests.addAll(questions);
                    mListQuestionObserver.onNext(mQuestionRequests);
                }, mListQuestionObserver::onError);
    }

    // TODO: Get only maximal 100 items and load more on {loadmore}
    private Single<List<QuestionResponse>> getQuestions() {
        return Single.create(
                emitter -> {
                    mFirebaseDatabase.getReference(DatabaseReferences.QUESTIONS)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                                    final List<QuestionResponse> questions =
                                            new ArrayList<>((int) dataSnapshot.getChildrenCount());
                                    for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        final QuestionInternal value = snapshot.getValue(QuestionInternal.class);
                                        final String questionKey = snapshot.getKey();
                                        final QuestionResponse response = new QuestionResponse(
                                                questionKey, value.getTitle(), value.getUsername(),
                                                value.getCreationDate());
                                        questions.add(response);
                                    }
                                    emitter.onSuccess(questions);
                                }

                                @Override
                                public void onCancelled(@NonNull final DatabaseError databaseError) {
                                    // TODO: Think about a better exception
                                    emitter.onError(databaseError.toException());
                                }
                            });
                });
    }
}
