package guru.stefma.choicm.database;

import android.support.annotation.NonNull;
import guru.stefma.choicm.database.model.AnswerResponse;
import guru.stefma.choicm.database.model.AnswersResponse;
import guru.stefma.choicm.database.model.QuestionRequest;
import guru.stefma.choicm.database.model.QuestionResponse;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import java.util.List;

public interface Database {

    /**
     * Add a new {@link QuestionRequest} to the database.
     *
     * @return emit a {@link QuestionResponse} if the question was successfully created.
     * Otherwise it emit an <b>error</b>.
     */
    Single<QuestionResponse> addQuestion(@NonNull final QuestionRequest questionRequest);

    /**
     * Get a single {@link QuestionResponse} for the given <b>questionKey</b>.
     *
     * @param questionKey the question for the question.
     * @return emit with the {@link QuestionResponse} or throw an <b>error</b>.
     */
    Single<QuestionResponse> getQuestionForQuestionKey(@NonNull final String questionKey);

    /**
     * Get a {@link List} of {@link QuestionResponse}s.
     * This will emit *always* 100 questions and emit more if you call {@link #moreQuestions()}.
     *
     * @return a {@link Observable} with <b>always</b> 100 questions (or less if not available).
     * Will load more by calling {@link #moreQuestions()}.
     * @see #moreQuestions()
     */
    Observable<List<QuestionResponse>> getQuestions();

    /**
     * Request more {@link QuestionRequest}s for the attached listener at {@link #getQuestions()}.
     *
     * Will do nothing if there is no subscriber at the {@link #getQuestions()}.
     */
    void moreQuestions();

    /**
     * Get a single - random - {@link QuestionResponse}.
     */
    Single<QuestionResponse> getRandomQuestion();

    /**
     * Get a {@link AnswersResponse} which contains the {@link AnswersResponse#getAnswerKey()}
     * and a {@link List} of all possible answers ({@link AnswerResponse}).
     *
     * @see QuestionResponse#getKey()
     */
    Single<AnswersResponse> getAnswersForQuestionKey(@NonNull final String questionKey);

    /**
     * Checks a answer for the current logged in user as "answered".
     *
     * @param answerKey the answerKey where the answer get checked (or answered)
     * @param position  the position inside the answer list
     * @param checked   the value if the answer is checked aka answered or not.
     * @return a {@link Completable} which completes if successfully checked. Otherwise throw an <b>error</b>
     */
    // TODO: make an object out of it instead of putting three values in
    Completable toggleAnswerForAnswerKeyAtPosition(
            @NonNull final String answerKey,
            final int position,
            final boolean checked);

}
