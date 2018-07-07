package guru.stefma.choicm.domain.usecase;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import guru.stefma.choicm.database.Database;
import guru.stefma.choicm.domain.usecase.ToggleAnswerForAnswerKeyAtPositionUseCase.Params;
import guru.stefma.choicm.domain.usecase.base.BaseCompletableUseCase;
import io.reactivex.Completable;
import io.reactivex.Scheduler;
import javax.inject.Inject;

public class ToggleAnswerForAnswerKeyAtPositionUseCase extends BaseCompletableUseCase<Params> {

    public static class Params {

        @NonNull
        private final String mAnswerKey;

        private final int mPosition;

        private final boolean mChecked;

        public Params(@NonNull final String answerKey, final int position, final boolean checked) {
            mAnswerKey = answerKey;
            mPosition = position;
            mChecked = checked;
        }
    }

    @Inject
    Database mDatabase;

    public ToggleAnswerForAnswerKeyAtPositionUseCase(
            @Nullable final Scheduler executionScheduler,
            @Nullable final Scheduler postExecutionScheduler) {
        super(executionScheduler, postExecutionScheduler);
    }

    @Override
    public Completable buildUseCase(@NonNull final Params params) {
        return mDatabase.toggleAnswerForAnswerKeyAtPosition(params.mAnswerKey, params.mPosition, params.mChecked);
    }
}
