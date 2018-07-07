package guru.stefma.choicm.presentation.question.show;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import guru.stefma.choicm.auth.model.User;
import guru.stefma.choicm.domain.model.Answer;
import guru.stefma.choicm.domain.model.Answers;
import guru.stefma.choicm.domain.model.Question;
import guru.stefma.choicm.domain.usecase.GetAnswersForQuestionKeyUseCase;
import guru.stefma.choicm.domain.usecase.GetQuestionForQuestionKeyUseCase;
import guru.stefma.choicm.domain.usecase.ToggleAnswerForAnswerKeyAtPositionUseCase;
import guru.stefma.choicm.domain.usecase.account.GetAccountUseCase;
import guru.stefma.choicm.domain.usecase.account.GetAccountUseCase.Params;
import guru.stefma.choicm.presentation.base.loading.LoadingView;
import guru.stefma.choicm.presentation.question.show.ShowQuestionViewModel.AnswerViewModel;
import io.reactivex.Single;
import java.util.ArrayList;
import java.util.List;
import net.grandcentrix.thirtyinch.TiPresenter;
import net.grandcentrix.thirtyinch.rx2.RxTiPresenterDisposableHandler;
import timber.log.Timber;

class ShowQuestionPresenter extends TiPresenter<ShowQuestionView> {

    @NonNull
    private final RxTiPresenterDisposableHandler mDisposableHandler =
            new RxTiPresenterDisposableHandler(this);

    @NonNull
    private final String mQuestionKey;

    @NonNull
    private final GetQuestionForQuestionKeyUseCase mGetQuestionForQuestionUseCase;

    @NonNull
    private final GetAnswersForQuestionKeyUseCase mGetAnswersForQuestionKeyUseCase;

    @NonNull
    private final GetAccountUseCase mGetAccountUseCase;

    @NonNull
    private final ToggleAnswerForAnswerKeyAtPositionUseCase mToggleAnswerUseCase;

    @Nullable
    private ShowQuestionViewModel mQuestionViewModel;

    ShowQuestionPresenter(
            @NonNull final String questionKey,
            @NonNull final GetQuestionForQuestionKeyUseCase getQuestionForQuestionKeyUseCase,
            @NonNull final GetAnswersForQuestionKeyUseCase getAnswersForQuestionKeyUseCase,
            @NonNull final GetAccountUseCase getAccountUseCase,
            @NonNull final ToggleAnswerForAnswerKeyAtPositionUseCase toggleAnswerUseCase) {
        mQuestionKey = questionKey;
        mGetQuestionForQuestionUseCase = getQuestionForQuestionKeyUseCase;
        mGetAnswersForQuestionKeyUseCase = getAnswersForQuestionKeyUseCase;
        mGetAccountUseCase = getAccountUseCase;
        mToggleAnswerUseCase = toggleAnswerUseCase;
    }

    @Override
    protected void onCreate() {
        super.onCreate();

        final Single<Question> questionSingle = mGetQuestionForQuestionUseCase
                .execute(new GetQuestionForQuestionKeyUseCase.Params(mQuestionKey));
        final Single<Answers> answersSingle = mGetAnswersForQuestionKeyUseCase
                .execute(new GetAnswersForQuestionKeyUseCase.Params(mQuestionKey));
        final Single<String> usernameSingle = mGetAccountUseCase.execute(new Params())
                // TODO: Use uID to User because these are unqiue.
                // username can be doubled!
                .map(User::getUsername)
                .onErrorResumeNext(throwable -> Single.just(""));
        final Single<String> uidSingle = mGetAccountUseCase.execute(new Params())
                .map(User::getUid);
        mDisposableHandler.manageDisposable(
                Single.zip(questionSingle, answersSingle, usernameSingle, uidSingle,
                        (question, answers, username, uid) -> {
                            final List<AnswerViewModel> answerViewModels = new ArrayList<>();
                            for (final Answer answer : answers.getAnswers()) {
                                final boolean userHasAnswered = answer.getAnsweredUids().contains(uid);
                                answerViewModels.add(new AnswerViewModel(answer.getTitle(), userHasAnswered));
                            }
                            final boolean readOnly = isReadOnly(question.getUsername(), username);
                            return new ShowQuestionViewModel(question.getTitle(), answers.getAnswerKey(),
                                    answerViewModels, readOnly);
                        })
                        .doOnSubscribe(ignore -> sendToView(LoadingView::showLoading))
                        .doOnEvent((answers, throwable) -> sendToView(LoadingView::hideLoading))
                        .doAfterSuccess(showQuestionAnswerViewModels ->
                                mQuestionViewModel = showQuestionAnswerViewModels)
                        .subscribe(viewModel -> sendToView(view -> view.showQuestion(viewModel)),
                                throwable -> {
                                    Timber.e(throwable,
                                            "Can't get question & answers for questionKey '" + mQuestionKey + "'");
                                    // TODO: Show error somehow...
                                }));
    }

    /**
     * Check if the view should be {@link ShowQuestionViewModel#isReadOnly() readOnly} or not.
     *
     * @param questionUsername the username from the question we show.
     * @param accUsername      the username of the current sign in account. Or <b>empty</b> if not logged in.
     * @return <code>true</code> if its should be readOnly. <code>false</code> otherwise.
     */
    // TODO: Move such a logic into a UseCase...
    private boolean isReadOnly(@NonNull final String questionUsername, @NonNull final String accUsername) {
        if (accUsername.isEmpty()) {
            // User is not signed in
            return true;
        }

        // TODO: Dont compare usernames. They aren't unique
        // compare uId (see User#getUid)
        // QuestionUsername and AccUsername is equal.
        // You can't answer your own questions...
        return questionUsername.equals(accUsername);
    }

    @Override
    protected void onAttachView(@NonNull final ShowQuestionView view) {
        super.onAttachView(view);

        if (mQuestionViewModel != null) {
            view.showQuestion(mQuestionViewModel);
        }
    }

    /**
     * Is called if an answer get checked/unchecked.
     *
     * @param position the position where the answer has changed
     * @param checked  the state if the answer has checked or unchecked
     */
    public void onAnswerChanged(final int position, final boolean checked) {
        mDisposableHandler.manageViewDisposable(mToggleAnswerUseCase.execute(
                new ToggleAnswerForAnswerKeyAtPositionUseCase.Params(mQuestionViewModel.getAnswerKey(), position,
                        checked))
                .doOnSubscribe(ignore -> sendToView(LoadingView::hideLoading))
                .doOnEvent(ignore -> sendToView(LoadingView::hideLoading))
                .subscribe(() -> {
                    Timber.i("Successfully updated the answer");
                    // Nothing more to do. Checkbox have already changed via UI...
                }, throwable -> {
                    Timber.e(throwable, "Can't update answer... Toggle answer back");
                    // TODO: Toggle answer back
                }));
    }
}
