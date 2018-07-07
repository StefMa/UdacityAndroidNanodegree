package guru.stefma.choicm.database.internal.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import guru.stefma.choicm.auth.Authentication;
import guru.stefma.choicm.auth.model.User;
import guru.stefma.choicm.database.model.QuestionRequest;
import guru.stefma.choicm.database.model.QuestionResponse;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import java.util.Collections;
import org.junit.*;
import org.junit.runner.*;
import org.junit.runners.*;

@RunWith(JUnit4.class)
public class AddQuestionUseCaseTest {

    private final FirebaseDatabase mockDatabase = mock(FirebaseDatabase.class);

    private final Authentication mockAuth = mock(Authentication.class);

    private final AddQuestionUseCase useCase = new AddQuestionUseCase(mockDatabase, mockAuth);

    @Ignore("Don't work because of the tasks.. -.-")
    @Test
    public void testAddQuestion_ShouldAddQuestion() {
        final DatabaseReference mockRef = mock(DatabaseReference.class);
        when(mockRef.child(any())).thenReturn(mockRef);
        when(mockRef.push()).thenReturn(mockRef);
        when(mockRef.getKey()).thenReturn("aKey");
        final Task mockTask = mock(Task.class);
        when(mockRef.updateChildren(any())).thenReturn(mockTask);
        when(mockTask.addOnSuccessListener(any())).thenReturn(mockTask);
        when(mockTask.addOnFailureListener(any())).thenReturn(mockTask);
        when(mockDatabase.getReference()).thenReturn(mockRef);
        when(mockAuth.getUser()).thenReturn(Single.just(new User("uid", "username")));
        final QuestionRequest questionRequest = new QuestionRequest("aQuestion", Collections.emptyList());

        final TestObserver<QuestionResponse> testObserver = useCase.addQuestion(questionRequest).test();

        testObserver.assertNoErrors();
        testObserver.assertComplete();
        final QuestionResponse response = testObserver.values().get(0);
        assertThat(response.getKey()).isEqualTo("aKey");
        assertThat(response.getTitle()).isEqualTo("aQuestion");
        assertThat(response.getUsername()).isEqualTo("username");
    }
}