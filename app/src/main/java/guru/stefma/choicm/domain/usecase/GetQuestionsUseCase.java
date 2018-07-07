package guru.stefma.choicm.domain.usecase;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import guru.stefma.choicm.database.Database;
import guru.stefma.choicm.database.model.QuestionResponse;
import guru.stefma.choicm.domain.model.Question;
import guru.stefma.choicm.domain.usecase.GetQuestionsUseCase.Params;
import guru.stefma.choicm.domain.usecase.base.BaseObservableUseCase;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class GetQuestionsUseCase extends BaseObservableUseCase<List<Question>, Params> {

    public static class Params {

    }

    @Inject
    Database mDatabase;

    public GetQuestionsUseCase(@Nullable final Scheduler executionScheduler,
            @Nullable final Scheduler postExecutionScheduler) {
        super(executionScheduler, postExecutionScheduler);
    }

    @Override
    public Observable<List<Question>> buildUseCase(@NonNull final Params params) {
        return mDatabase.getQuestions()
                .map(questionResponses -> {
                    final List<Question> questions = new ArrayList<>();
                    for (final QuestionResponse questionResponse : questionResponses) {
                        questions.add(new Question(
                                questionResponse.getKey(),
                                questionResponse.getTitle(),
                                questionResponse.getUsername(),
                                questionResponse.getCreationDate()));
                    }
                    return questions;
                });
    }

}
