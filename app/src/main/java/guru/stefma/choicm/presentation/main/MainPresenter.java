package guru.stefma.choicm.presentation.main;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import guru.stefma.choicm.domain.model.Question;
import guru.stefma.choicm.domain.usecase.GetQuestionsUseCase;
import guru.stefma.choicm.domain.usecase.account.IsLoggedInUseCase;
import guru.stefma.choicm.presentation.base.loading.LoadingView;
import guru.stefma.choicm.presentation.question.add.AddQuestionActivity;
import java.util.List;
import net.grandcentrix.thirtyinch.TiPresenter;
import net.grandcentrix.thirtyinch.rx2.RxTiPresenterDisposableHandler;
import timber.log.Timber;

class MainPresenter extends TiPresenter<MainView> {

    @NonNull
    private final RxTiPresenterDisposableHandler mDisposableHandler =
            new RxTiPresenterDisposableHandler(this);

    @Nullable
    private List<Question> mCurrentShowedQuestions;

    @NonNull
    private final GetQuestionsUseCase mGetQuestionUseCase;

    @NonNull
    private final IsLoggedInUseCase mIsLoggedInUseCase;

    MainPresenter(
            @NonNull final GetQuestionsUseCase getQuestionsUseCase,
            @NonNull final IsLoggedInUseCase loggedInUseCase) {
        mGetQuestionUseCase = getQuestionsUseCase;
        mIsLoggedInUseCase = loggedInUseCase;
    }

    @Override
    protected void onCreate() {
        super.onCreate();

        mDisposableHandler.manageDisposable(
                mGetQuestionUseCase.execute(new GetQuestionsUseCase.Params())
                        .doOnSubscribe(ignore -> sendToView(LoadingView::showLoading))
                        .doOnNext(questions -> mCurrentShowedQuestions = questions)
                        .doOnNext(ignore -> sendToView(LoadingView::hideLoading))
                        .subscribe(questions -> sendToView(view -> view.showQuestions(questions)),
                                throwable -> {
                                    // TODO: Show an error..
                                    Timber.e(throwable, "Can't fetch questions..");
                                }));
    }

    @Override
    protected void onAttachView(@NonNull final MainView view) {
        super.onAttachView(view);

        if (mCurrentShowedQuestions != null) {
            view.showQuestions(mCurrentShowedQuestions);
        }
    }

    /**
     * Is called when the user click on a question in the list.
     *
     * Will start the question details...
     */
    public void onQuestionClicked(final int position) {
        getViewOrThrow().showQuestion(mCurrentShowedQuestions.get(position).getKey());
    }

    /**
     * Is called if the user request a refresh via some UI components
     * in the View.
     *
     * Will refresh the current questions...
     */
    public void onRefreshRequested() {
        // TODO: Dublicated code.
        mDisposableHandler.manageViewDisposable(
                mGetQuestionUseCase.execute(new GetQuestionsUseCase.Params())
                        .take(1)
                        .doOnNext(questions -> mCurrentShowedQuestions = questions)
                        .subscribe(questions -> sendToView(view -> view.showQuestions(questions)),
                                throwable -> {
                                    // TODO: Show an error..
                                    Timber.e(throwable, "Can't fetch questions..");
                                }));
    }

    /**
     * Is called if the user press on the "account view"
     * to see its account.
     *
     * Will start the login if the user not logged in
     * or the account page if logged in.
     */
    public void onAccountClicked() {
        mDisposableHandler.manageViewDisposables(
                mIsLoggedInUseCase.execute(new IsLoggedInUseCase.Params())
                        .subscribe((isLoggedIn) -> {
                                    if (isLoggedIn) {
                                        sendToView(MainView::showAccount);
                                    } else {
                                        sendToView(MainView::showSignIn);
                                    }
                                },
                                throwable -> {
                                    Timber.e(throwable, "Don't get the info if the user is logged in");
                                    // TODO: Think about what to do here...
                                }));
    }

    /**
     * Is called if the press the <b>add new question</b> view.
     *
     * Will either start the {@link AddQuestionActivity}
     * or the {@link guru.stefma.choicm.presentation.account.signin.SignInActivity} if not logged in.
     */
    public void onAddQuestionClicked() {
        mDisposableHandler.manageViewDisposables(
                mIsLoggedInUseCase.execute(new IsLoggedInUseCase.Params())
                        .subscribe((isLoggedIn) -> {
                            if (isLoggedIn) {
                                sendToView(MainView::showAddQuestion);
                            } else {
                                // TODO: Start with for result
                                // and open AddQuestion after a successfully sign in
                                sendToView(MainView::showSignIn);
                            }
                        }));
    }
}
