package guru.stefma.jokes;

import com.squareup.moshi.Json;
import com.squareup.moshi.Moshi;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class JokeProvider {

    private static final String CHUCK_NORRIS_JOKES
            = "{ \"type\": \"success\", \"value\": [ { \"id\": 547, \"joke\": \"Product Owners never ask Chuck Norris for more features. They ask for mercy.\", \"categories\": [\"nerdy\"] }, { \"id\": 419, \"joke\": \"Count from one to ten. That's how long it would take Chuck Norris to kill you...Forty seven times.\", \"categories\": [] }, { \"id\": 406, \"joke\": \"Chuck Norris doesn't say &quot;who's your daddy&quot;, because he knows the answer.\", \"categories\": [] }, { \"id\": 235, \"joke\": \"Chuck Norris once kicked a horse in the chin. Its descendants are known today as Giraffes.\", \"categories\": [] }, { \"id\": 16, \"joke\": \"Pluto is actually an orbiting group of British soldiers from the American Revolution who entered space after the Chuck gave them a roundhouse kick to the face.\", \"categories\": [] }, { \"id\": 191, \"joke\": \"The easiest way to determine Chuck Norris' age is to cut him in half and count the rings.\", \"categories\": [] }, { \"id\": 247, \"joke\": \"Chuck Norris can win at solitaire with only 18 cards.\", \"categories\": [] }, { \"id\": 227, \"joke\": \"When God said, &quot;let there be light&quot;, Chuck Norris said, &quot;say 'please'.&quot;\", \"categories\": [] }, { \"id\": 3, \"joke\": \"Chuck Norris doesn't read books. He stares them down until he gets the information he wants.\", \"categories\": [] }, { \"id\": 266, \"joke\": \"Chuck Norris? roundhouse kick is so powerful, it can be seen from outer space by the naked eye.\", \"categories\": [] }, { \"id\": 59, \"joke\": \"Chuck Norris once roundhouse kicked someone so hard that his foot broke the speed of light, went back in time, and killed Amelia Earhart while she was flying over the Pacific Ocean.\", \"categories\": [] }, { \"id\": 483, \"joke\": \"Bill Gates thinks he's Chuck Norris. Chuck Norris actually laughed. Once.\", \"categories\": [] }, { \"id\": 503, \"joke\": \"Chuck Norris protocol design method has no status, requests or responses, only commands.\", \"categories\": [\"nerdy\"] }, { \"id\": 350, \"joke\": \"Chuck Norris starts everyday with a protein shake made from Carnation Instant Breakfast, one dozen eggs, pure Colombian cocaine, and rattlesnake venom. He injects it directly into his neck with a syringe.\", \"categories\": [] }, { \"id\": 333, \"joke\": \"Chuck Norris was banned from competitive bullriding after a 1992 exhibition in San Antonio, when he rode the bull 1,346 miles from Texas to Milwaukee Wisconsin to pick up his dry cleaning.\", \"categories\": [] } ]  }";

    private static final Moshi MOSHI = new Moshi.Builder().build();

    public static String joke() {
        try {
            final Joke joke = MOSHI.adapter(Joke.class).fromJson(CHUCK_NORRIS_JOKES);
            final int randomInt = new Random().nextInt(joke.mValues.size());
            return joke.mValues.get(randomInt).mJoke;
        } catch (NullPointerException | IOException npe) {
            // Fallback. Should never happen
            return "";
        }
    }

    private static class Joke {

        @Json(name = "value")
        private List<RealJoke> mValues;

    }

    private static class RealJoke {

        @Json(name = "joke")
        private String mJoke;

    }

}
