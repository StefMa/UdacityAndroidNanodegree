package guru.stefma.choicm.database.internal.usecase;

import android.support.annotation.NonNull;
import com.google.firebase.database.FirebaseDatabase;
import guru.stefma.choicm.auth.Authentication;
import guru.stefma.choicm.auth.model.User;
import guru.stefma.choicm.database.internal.DatabaseReferences;
import guru.stefma.choicm.database.internal.model.AnswerInternal;
import guru.stefma.choicm.database.internal.model.AnswerWrapperInternal;
import guru.stefma.choicm.database.internal.model.QuestionInternal;
import guru.stefma.choicm.database.model.AnswerRequest;
import guru.stefma.choicm.database.model.QuestionRequest;
import guru.stefma.choicm.database.model.QuestionResponse;
import io.reactivex.Single;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A usecase which will add a question to the {@link FirebaseDatabase}
 * by calling {@link #addQuestion(QuestionRequest)}
 */
public class AddQuestionUseCase {

    @NonNull
    private final FirebaseDatabase mFirebaseDatabase;

    @NonNull
    private final Authentication mAuthentication;

    public AddQuestionUseCase(
            @NonNull final FirebaseDatabase firebaseDatabase,
            @NonNull final Authentication authentication) {
        mFirebaseDatabase = firebaseDatabase;
        mAuthentication = authentication;
    }

    public Single<QuestionResponse> addQuestion(@NonNull final QuestionRequest questionRequest) {
        return mAuthentication.getUser()
                .map(User::getUsername)
                .flatMap(username -> Single.create(emitter -> {
                    final QuestionInternal questionInternal =
                            new QuestionInternal(questionRequest.getTitle(), username);
                    final String questionsKey = mFirebaseDatabase.getReference()
                            .child(DatabaseReferences.QUESTIONS)
                            .push()
                            .getKey();

                    final List<AnswerInternal> answerInternals = new ArrayList<>();
                    for (final AnswerRequest answerRequest : questionRequest.getAnswerRequests()) {
                        answerInternals.add(new AnswerInternal(answerRequest.getTitle(), Collections.emptyList()));
                    }
                    final AnswerWrapperInternal answerInternal =
                            new AnswerWrapperInternal(questionsKey, answerInternals);
                    final String answersKey = mFirebaseDatabase.getReference()
                            .child(DatabaseReferences.ANSWERS)
                            .push()
                            .getKey();

                    final Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put(DatabaseReferences.QUESTIONS + "/" + questionsKey, questionInternal);
                    childUpdates.put(DatabaseReferences.ANSWERS + "/" + answersKey, answerInternal);

                    mFirebaseDatabase.getReference()
                            .updateChildren(childUpdates)
                            .addOnSuccessListener(ignore -> {
                                final QuestionResponse response =
                                        new QuestionResponse(questionsKey, questionInternal.getTitle(), username,
                                                questionInternal.getCreationDate());
                                emitter.onSuccess(response);
                            })
                            .addOnFailureListener(error -> {
                                // TODO: Think about a custom throwable...
                                emitter.onError(error);
                            });
                }));
    }

}
