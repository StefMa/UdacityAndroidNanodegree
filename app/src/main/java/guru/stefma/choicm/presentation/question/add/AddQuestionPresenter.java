package guru.stefma.choicm.presentation.question.add;

import android.support.annotation.NonNull;
import guru.stefma.choicm.domain.usecase.AddQuestionUseCase;
import guru.stefma.choicm.domain.usecase.AddQuestionUseCase.Params;
import guru.stefma.choicm.presentation.base.loading.LoadingView;
import java.util.ArrayList;
import java.util.List;
import net.grandcentrix.thirtyinch.TiPresenter;
import net.grandcentrix.thirtyinch.rx2.RxTiPresenterDisposableHandler;
import timber.log.Timber;

class AddQuestionPresenter extends TiPresenter<AddQuestionView> {

    @NonNull
    private final RxTiPresenterDisposableHandler mDisposableHandler =
            new RxTiPresenterDisposableHandler(this);

    @NonNull
    private final AddQuestionUseCase mAddQuestionUseCase;

    AddQuestionPresenter(@NonNull final AddQuestionUseCase addQuestionUseCase) {
        mAddQuestionUseCase = addQuestionUseCase;
    }

    /**
     * Is called if the user clicked on the "add question".
     *
     * We should check if the question is set (not empty)
     * and the answers provide at least one answer.
     *
     * Then we can proceed and add the question.
     *
     * @param question the question or <b>empty</b>.
     * @param answers  the answers which may contain <code>null</code> or <b>empty</b> items. Or an <b>empty list</b>.
     */
    public void onAddQuestionClicked(@NonNull final String question, @NonNull final List<String> answers) {
        final List<String> filterAnswers = filterAnswers(answers);
        if (question.isEmpty() || filterAnswers.isEmpty()) {
            // TODO: Show error
            return;
        }

        mDisposableHandler.manageViewDisposable(
                mAddQuestionUseCase.execute(new Params(question, filterAnswers))
                        .doOnSubscribe(ignore -> sendToView(LoadingView::showLoading))
                        .doOnEvent((ignore, ignored) -> sendToView(LoadingView::hideLoading))
                        .subscribe((questionModel) -> {
                            Timber.i("Question (" + questionModel + ") was successfully added to the database");
                            sendToView(view -> view.showQuestion(questionModel.getKey()));
                        }, throwable -> {
                            // TODO: Show an error
                            Timber.e(throwable, "Can't add question for some reasons :/");
                        }));
    }

    /**
     * Filter the given <b>answers</b> by checking if they are <code>null</code> or <b>empty</b>.
     *
     * @param answers a list of {@link String} which may be <code>null</code> or <b>empty</b>.
     * @return a new {@link List} of {@link String}s which don't contain <code>null</code> or <b>empty</b> items.
     */
    private List<String> filterAnswers(final @NonNull List<String> answers) {
        final List<String> filteredAnswers = new ArrayList<>();
        for (final String answer : answers) {
            if (answer != null && !answer.isEmpty()) {
                filteredAnswers.add(answer);
            }
        }
        return filteredAnswers;
    }
}
