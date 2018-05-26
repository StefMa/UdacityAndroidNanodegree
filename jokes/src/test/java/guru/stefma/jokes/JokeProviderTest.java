package guru.stefma.jokes;

import static org.assertj.core.api.Java6Assertions.*;

import org.junit.*;

public class JokeProviderTest {

    @Test
    public void testJokeProvider() {
        final String joke = JokeProvider.joke();

        System.out.println(joke);
        assertThat(joke).isNotEmpty();
        assertThat(joke).isNotNull();
    }
}