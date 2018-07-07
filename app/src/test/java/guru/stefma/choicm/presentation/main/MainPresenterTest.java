package guru.stefma.choicm.presentation.main;

import static org.mockito.Mockito.*;

import guru.stefma.choicm.domain.usecase.GetQuestionForQuestionKeyUseCase;
import guru.stefma.choicm.domain.usecase.GetQuestionsUseCase;
import guru.stefma.choicm.domain.usecase.account.IsLoggedInUseCase;
import io.reactivex.Observable;
import io.reactivex.Single;
import java.util.Collections;
import net.grandcentrix.thirtyinch.test.TiTestPresenter;
import org.junit.*;
import org.junit.runner.*;
import org.junit.runners.*;

@RunWith(JUnit4.class)
public class MainPresenterTest {

    private final GetQuestionsUseCase mockGetQuestions = mock(GetQuestionsUseCase.class);

    private final IsLoggedInUseCase mockIsLoggedIn = mock(IsLoggedInUseCase.class);

    private final MainView mockView = mock(MainView.class);

    private final MainPresenter mainPresenter = new MainPresenter(mockGetQuestions, mockIsLoggedIn);

    private final TiTestPresenter<MainView> testPresenter = mainPresenter.test();

    @Test
    public void testOnAccountClicked_LoggedIn_ShouldShowAccount() {
        when(mockGetQuestions.execute(any())).thenReturn(Observable.just(Collections.emptyList()));
        when(mockIsLoggedIn.execute(any())).thenReturn(Single.just(true));
        testPresenter.create();
        testPresenter.attachView(mockView);

        mainPresenter.onAccountClicked();

        verify(mockView).showAccount();
    }

    @Test
    public void testOnAccountClicked_NotLoggedIn_ShouldShowSignIn() {
        when(mockGetQuestions.execute(any())).thenReturn(Observable.just(Collections.emptyList()));
        when(mockIsLoggedIn.execute(any())).thenReturn(Single.just(false));
        testPresenter.create();
        testPresenter.attachView(mockView);

        mainPresenter.onAccountClicked();

        verify(mockView).showSignIn();
    }

    @Test
    public void testOnAddQuestionClicked_LoggedIn_ShouldShowAddQuestion() {
        when(mockGetQuestions.execute(any())).thenReturn(Observable.just(Collections.emptyList()));
        when(mockIsLoggedIn.execute(any())).thenReturn(Single.just(true));
        testPresenter.create();
        testPresenter.attachView(mockView);

        mainPresenter.onAddQuestionClicked();

        verify(mockView).showAddQuestion();
    }

    @Test
    public void testOnAddQuestionClicked_NotLoggedIn_ShouldShowSignIn() {
        when(mockGetQuestions.execute(any())).thenReturn(Observable.just(Collections.emptyList()));
        when(mockIsLoggedIn.execute(any())).thenReturn(Single.just(false));
        testPresenter.create();
        testPresenter.attachView(mockView);

        mainPresenter.onAddQuestionClicked();

        verify(mockView).showSignIn();
    }

    // TODO: Add tests for questions
}