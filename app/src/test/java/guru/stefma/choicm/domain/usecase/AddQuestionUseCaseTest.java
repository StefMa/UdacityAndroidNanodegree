package guru.stefma.choicm.domain.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import guru.stefma.choicm.database.Database;
import guru.stefma.choicm.database.model.QuestionResponse;
import guru.stefma.choicm.domain.model.Question;
import guru.stefma.choicm.domain.usecase.AddQuestionUseCase.Params;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;
import java.util.Arrays;
import java.util.Date;
import org.junit.*;
import org.junit.runner.*;
import org.junit.runners.*;

@RunWith(JUnit4.class)
public class AddQuestionUseCaseTest {

    private final TestScheduler mTestScheduler = new TestScheduler();

    private final AddQuestionUseCase mUseCase = new AddQuestionUseCase(mTestScheduler, mTestScheduler);

    private final Database mockDatabase = mock(Database.class);

    @Test
    public void testExecute_ShouldReturnValidQuestionObject() {
        final Date date = new Date();
        when(mockDatabase.addQuestion(any())).thenReturn(Single.just(new QuestionResponse("k", "q", "u", date)));
        mUseCase.mDatabase = mockDatabase;

        final TestObserver<Question> testObserver =
                mUseCase.execute(new Params("q", Arrays.asList("a1", "a2"))).test();

        mTestScheduler.triggerActions();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        final Question question = testObserver.values().get(0);
        assertThat(question.getKey()).isEqualTo("k");
        assertThat(question.getTitle()).isEqualTo("q");
        assertThat(question.getUsername()).isEqualTo("u");
        assertThat(question.getCreationDate()).hasSameTimeAs(date);
    }
}