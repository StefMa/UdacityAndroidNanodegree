package guru.stefma.choicm.presentation.question.show;

import android.support.annotation.NonNull;
import guru.stefma.choicm.presentation.base.loading.LoadingView;
import net.grandcentrix.thirtyinch.TiView;

interface ShowQuestionView extends TiView, LoadingView {

    /**
     * Show the given {@link ShowQuestionViewModel} in the UI.
     */
    void showQuestion(@NonNull final ShowQuestionViewModel questionViewModel);
}
