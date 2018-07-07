package guru.stefma.choicm.presentation.question.add;

import android.support.annotation.NonNull;
import guru.stefma.choicm.presentation.base.loading.LoadingView;
import net.grandcentrix.thirtyinch.TiView;

interface AddQuestionView extends TiView, LoadingView {

    /**
     * Will show the {@link guru.stefma.choicm.presentation.question.show.ShowQuestionActivity}
     * and finish this view {@link AddQuestionActivity}.
     *
     * @param questionKey the key for the question.
     */
    void showQuestion(@NonNull String questionKey);
}
