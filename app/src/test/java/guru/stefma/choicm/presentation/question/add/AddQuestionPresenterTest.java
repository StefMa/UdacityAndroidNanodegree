package guru.stefma.choicm.presentation.question.add;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import guru.stefma.choicm.domain.model.Question;
import guru.stefma.choicm.domain.usecase.AddQuestionUseCase;
import guru.stefma.choicm.domain.usecase.AddQuestionUseCase.Params;
import io.reactivex.Single;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.grandcentrix.thirtyinch.test.TiTestPresenter;
import org.junit.*;
import org.junit.runner.*;
import org.junit.runners.*;
import org.mockito.*;

@RunWith(JUnit4.class)
public class AddQuestionPresenterTest {

    private final AddQuestionUseCase mockAddQuestionUseCase = mock(AddQuestionUseCase.class);

    private final AddQuestionPresenter mPresenter = new AddQuestionPresenter(mockAddQuestionUseCase);

    private final AddQuestionView mockView = mock(AddQuestionView.class);

    private final TiTestPresenter<AddQuestionView> testPresenter = mPresenter.test();

    @Test
    public void testAddQuestion_AllValid_ShouldShowHideLoadingAndShowQuestion() {
        when(mockAddQuestionUseCase.execute(any())).thenReturn(Single.just(new Question("key", "qtitle", "", null)));
        testPresenter.create();
        testPresenter.attachView(mockView);

        mPresenter.onAddQuestionClicked("question", Collections.singletonList("answer"));

        verify(mockView).showLoading();
        verify(mockView).hideLoading();
        verify(mockView).showQuestion("key");
    }

    @Test
    public void testAddQuestion_WithEmptyAndNullAnswer_ShouldCallUseCaseWithFiltered() {
        when(mockAddQuestionUseCase.execute(any())).thenReturn(Single.just(new Question("", "", "", null)));
        testPresenter.create();
        testPresenter.attachView(mockView);

        mPresenter.onAddQuestionClicked("q", Arrays.asList("", null, "valid"));

        final ArgumentCaptor<Params> captor = ArgumentCaptor.forClass(Params.class);
        verify(mockAddQuestionUseCase).execute(captor.capture());
        final List<String> answers = captor.getValue().getAnswers();
        assertThat(answers.size()).isEqualTo(1);
        assertThat(answers.get(0)).isEqualTo("valid");
    }

    @Test
    public void testAddQuestion_WithoutQuestion_ShouldNeverCallAViewOrUseCase() {
        testPresenter.create();
        testPresenter.attachView(mockView);

        mPresenter.onAddQuestionClicked("", Collections.singletonList("answer"));

        verify(mockView, never()).showLoading();
        verify(mockView, never()).hideLoading();
        verify(mockAddQuestionUseCase, never()).execute(any());
    }

    @Test
    public void testAddQuestion_WithoutAnswers_ShouldNeverCallAViewOrUseCase() {
        testPresenter.create();
        testPresenter.attachView(mockView);

        mPresenter.onAddQuestionClicked("q", Collections.emptyList());

        verify(mockView, never()).showLoading();
        verify(mockView, never()).hideLoading();
        verify(mockAddQuestionUseCase, never()).execute(any());
    }
}