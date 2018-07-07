package guru.stefma.choicm.domain.usecase;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import guru.stefma.choicm.database.Database;
import guru.stefma.choicm.database.model.AnswerResponse;
import guru.stefma.choicm.domain.model.Answer;
import guru.stefma.choicm.domain.model.Answers;
import guru.stefma.choicm.domain.usecase.GetAnswersForQuestionKeyUseCase.Params;
import guru.stefma.choicm.domain.usecase.base.BaseSingleUseCase;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class GetAnswersForQuestionKeyUseCase extends BaseSingleUseCase<Answers, Params> {

    public static class Params {

        @NonNull
        private final String mQuestionKey;

        public Params(@NonNull final String questionKey) {
            mQuestionKey = questionKey;
        }
    }

    @Inject
    Database mDatabase;

    public GetAnswersForQuestionKeyUseCase(
            @Nullable final Scheduler executionScheduler,
            @Nullable final Scheduler postExecutionScheduler) {
        super(executionScheduler, postExecutionScheduler);
    }

    @Override
    public Single<Answers> buildUseCase(@NonNull final Params params) {
        return mDatabase.getAnswersForQuestionKey(params.mQuestionKey)
                .map(answersResponse -> {
                    final List<Answer> answers = new ArrayList<>();
                    for (final AnswerResponse answerRespons : answersResponse.getAnswers()) {
                        answers.add(new Answer(answerRespons.getTitle(), answerRespons.getAnsweredUids()));
                    }
                    return new Answers(answersResponse.getAnswerKey(), answers);
                });
    }
}
