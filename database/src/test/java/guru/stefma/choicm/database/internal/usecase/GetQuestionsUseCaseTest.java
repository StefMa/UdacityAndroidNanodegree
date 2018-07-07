package guru.stefma.choicm.database.internal.usecase;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import guru.stefma.choicm.database.model.QuestionRequest;
import guru.stefma.choicm.database.model.QuestionResponse;
import io.reactivex.observers.TestObserver;
import java.util.List;
import org.junit.*;
import org.junit.runner.*;
import org.junit.runners.*;

@RunWith(JUnit4.class)
public class GetQuestionsUseCaseTest {

    private final FirebaseDatabase mockDatabase = mock(FirebaseDatabase.class);

    private final GetQuestionsUseCase useCase = new GetQuestionsUseCase(mockDatabase);

    @Ignore("How can i test this?")
    @Test
    public void name() {
        final DatabaseReference mockReference = mock(DatabaseReference.class);
        when(mockDatabase.getReference(any())).thenReturn(mockReference);
        final TestObserver<List<QuestionResponse>> testObserver = useCase.getQuestionRequests().test();


    }
}