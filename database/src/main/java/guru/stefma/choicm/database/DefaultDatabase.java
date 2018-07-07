package guru.stefma.choicm.database;

import android.support.annotation.NonNull;
import com.google.firebase.database.FirebaseDatabase;
import guru.stefma.choicm.auth.Authentication;
import guru.stefma.choicm.database.internal.usecase.AddQuestionUseCase;
import guru.stefma.choicm.database.internal.usecase.ToggleAnswerForAnswerKeyAtPositionUseCase;
import guru.stefma.choicm.database.internal.usecase.GetAnswersForQuestionKeyUseCase;
import guru.stefma.choicm.database.internal.usecase.GetQuestionForQuestionKeyUseCase;
import guru.stefma.choicm.database.internal.usecase.GetQuestionsUseCase;
import guru.stefma.choicm.database.model.AnswersResponse;
import guru.stefma.choicm.database.model.QuestionRequest;
import guru.stefma.choicm.database.model.QuestionResponse;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import java.util.List;
import java.util.Random;

/**
 * The default implementation of an {@link Database} instance.
 *
 * The class will be used by default if you a user request an instance over the {@link DatabaseProvider}.
 */
class DefaultDatabase implements Database {

    @NonNull
    private final FirebaseDatabase mFirebaseDatabase;

    @NonNull
    private final Authentication mAuthentication;

    @NonNull
    private final GetQuestionsUseCase mGetQuestionsUseCase;

    DefaultDatabase(
            @NonNull final FirebaseDatabase firebaseDatabase,
            @NonNull final Authentication authentication) {
        mFirebaseDatabase = firebaseDatabase;
        mAuthentication = authentication;
        mGetQuestionsUseCase = new GetQuestionsUseCase(mFirebaseDatabase);
    }

    @Override
    public Single<QuestionResponse> addQuestion(@NonNull final QuestionRequest questionRequest) {
        final AddQuestionUseCase useCase = new AddQuestionUseCase(mFirebaseDatabase, mAuthentication);
        return useCase.addQuestion(questionRequest);
    }

    @Override
    public Single<QuestionResponse> getQuestionForQuestionKey(@NonNull final String questionKey) {
        return new GetQuestionForQuestionKeyUseCase(mFirebaseDatabase).getQuestionForQuestionKey(questionKey);
    }

    @Override
    public Observable<List<QuestionResponse>> getQuestions() {
        return mGetQuestionsUseCase.getQuestionRequests();
    }

    @Override
    public void moreQuestions() {
        mGetQuestionsUseCase.loadMore();
    }

    @Override
    public Single<QuestionResponse> getRandomQuestion() {
        // TODO: Needs to be changed somwhow when moreQuestions() loading works
        return Single.create(emitter -> getQuestions()
                .take(1)
                .subscribe(questions -> {
                    // TODO: Better logic for "randomize".
                    // e.g. filter own questions or already asked questions
                    final int random = new Random().nextInt(questions.size() - 1);
                    emitter.onSuccess(questions.get(random));
                }));
    }

    @Override
    public Single<AnswersResponse> getAnswersForQuestionKey(@NonNull final String questionKey) {
        return new GetAnswersForQuestionKeyUseCase(mFirebaseDatabase).getAnswersForQuestionKey(questionKey);
    }

    @Override
    public Completable toggleAnswerForAnswerKeyAtPosition(
            @NonNull final String answerKey,
            final int position,
            final boolean checked) {
        final ToggleAnswerForAnswerKeyAtPositionUseCase useCase
                = new ToggleAnswerForAnswerKeyAtPositionUseCase(mFirebaseDatabase, mAuthentication);
        return useCase.toggleAnswer(answerKey, position, checked);
    }
}
