package guru.stefma.choicm.domain.usecase;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import guru.stefma.choicm.database.Database;
import guru.stefma.choicm.domain.model.Question;
import guru.stefma.choicm.domain.usecase.GetQuestionForQuestionKeyUseCase.Params;
import guru.stefma.choicm.domain.usecase.base.BaseSingleUseCase;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import javax.inject.Inject;

public class GetQuestionForQuestionKeyUseCase extends BaseSingleUseCase<Question, Params> {

    public static class Params {

        @NonNull
        private final String mQuestionKey;

        public Params(@NonNull final String questionKey) {
            mQuestionKey = questionKey;
        }
    }

    @Inject
    Database mDatabase;

    public GetQuestionForQuestionKeyUseCase(
            @Nullable final Scheduler executionScheduler,
            @Nullable final Scheduler postExecutionScheduler) {
        super(executionScheduler, postExecutionScheduler);
    }

    @Override
    public Single<Question> buildUseCase(@NonNull final Params params) {
        return mDatabase.getQuestionForQuestionKey(params.mQuestionKey)
                .map(questionResponse ->
                        new Question(
                                questionResponse.getKey(),
                                questionResponse.getTitle(),
                                questionResponse.getUsername(),
                                questionResponse.getCreationDate()));
    }
}
