package guru.stefma.choicm.presentation.main;

import android.support.annotation.NonNull;
import guru.stefma.choicm.domain.model.Question;
import guru.stefma.choicm.presentation.base.loading.LoadingView;
import guru.stefma.choicm.presentation.question.add.AddQuestionActivity;
import java.util.List;
import net.grandcentrix.thirtyinch.TiView;

interface MainView extends TiView, LoadingView {

    /**
     * Will show the {@link guru.stefma.choicm.presentation.account.AccountActivity}
     */
    void showAccount();

    /**
     * Will show the {@link AddQuestionActivity}
     */
    void showAddQuestion();

    /**
     * Will show the {@link guru.stefma.choicm.presentation.question.show.ShowQuestionActivity}.
     *
     * @param questionKey the key of the question we want to show.
     */
    void showQuestion(@NonNull final String questionKey);

    /**
     * Will show all the questions in the UI.
     */
    void showQuestions(@NonNull final List<Question> questions);

    /**
     * Will show the {@link guru.stefma.choicm.presentation.account.signin.SignInActivity}
     */
    void showSignIn();
}
