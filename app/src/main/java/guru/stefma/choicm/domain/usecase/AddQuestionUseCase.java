package guru.stefma.choicm.domain.usecase;

import static android.support.annotation.VisibleForTesting.NONE;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import guru.stefma.choicm.database.Database;
import guru.stefma.choicm.database.model.AnswerRequest;
import guru.stefma.choicm.database.model.QuestionRequest;
import guru.stefma.choicm.domain.model.Question;
import guru.stefma.choicm.domain.usecase.AddQuestionUseCase.Params;
import guru.stefma.choicm.domain.usecase.base.BaseSingleUseCase;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class AddQuestionUseCase extends BaseSingleUseCase<Question, Params> {

    public static class Params {

        @NonNull
        private final String mQuestion;

        @NonNull
        private final List<String> mAnswers;

        public Params(@NonNull final String question, @NonNull final List<String> answers) {
            mQuestion = question;
            mAnswers = answers;
        }

        @NonNull
        @VisibleForTesting(otherwise = NONE)
        public String getQuestion() {
            return mQuestion;
        }

        @NonNull
        @VisibleForTesting(otherwise = NONE)
        public List<String> getAnswers() {
            return mAnswers;
        }
    }

    @Inject
    Database mDatabase;

    public AddQuestionUseCase(
            @Nullable final Scheduler executionScheduler,
            @Nullable final Scheduler postExecutionScheduler) {
        super(executionScheduler, postExecutionScheduler);
    }

    @Override
    public Single<Question> buildUseCase(@NonNull final Params params) {
        final List<AnswerRequest> answerRequests = new ArrayList<>();
        for (final String answer : params.mAnswers) {
            answerRequests.add(new AnswerRequest(answer));
        }
        return mDatabase.addQuestion(new QuestionRequest(params.mQuestion, answerRequests))
                .map(questionResponse ->
                        new Question(
                                questionResponse.getKey(),
                                questionResponse.getTitle(),
                                questionResponse.getUsername(),
                                questionResponse.getCreationDate()));
    }
}
