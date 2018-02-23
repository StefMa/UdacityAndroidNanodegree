package guru.stefma.popularmovies.data.remote.entity;

import static org.assertj.core.api.Assertions.*;

import com.squareup.moshi.Moshi.Builder;
import guru.stefma.popularmovies.data.remote.entity.RemoteMovieWrapperEntity.RemoteMovieEntity;
import java.util.List;
import org.junit.*;
import org.junit.runner.*;
import org.junit.runners.*;

@RunWith(JUnit4.class)
public class RemoteMovieEntityTest {

    private static final String mJsonExample = "{\n"
            + "  \"page\": 1,\n"
            + "  \"total_results\": 8141,\n"
            + "  \"total_pages\": 408,\n"
            + "  \"results\": [\n"
            + "    {\n"
            + "      \"vote_count\": 14054,\n"
            + "      \"id\": 155,\n"
            + "      \"video\": false,\n"
            + "      \"vote_average\": 8.3,\n"
            + "      \"title\": \"The Dark Knight\",\n"
            + "      \"popularity\": 34.165257,\n"
            + "      \"poster_path\": \"/1hRoyzDtpgMU7Dz4JF22RANzQO7.jpg\",\n"
            + "      \"original_language\": \"en\",\n"
            + "      \"original_title\": \"The Dark Knight\",\n"
            + "      \"genre_ids\": [\n"
            + "        18,\n"
            + "        28,\n"
            + "        80,\n"
            + "        53\n"
            + "      ],\n"
            + "      \"backdrop_path\": \"/hqkIcbrOHL86UncnHIsHVcVmzue.jpg\",\n"
            + "      \"adult\": false,\n"
            + "      \"overview\": \"Batman raises the stakes in his war on crime. With the help of Lt. Jim Gordon and District Attorney Harvey Dent, Batman sets out to dismantle the remaining criminal organizations that plague the streets. The partnership proves to be effective, but they soon find themselves prey to a reign of chaos unleashed by a rising criminal mastermind known to the terrified citizens of Gotham as the Joker.\",\n"
            + "      \"release_date\": \"2008-07-16\"\n"
            + "    }\n"
            + "  ]\n"
            + "}";

    @Test
    public void testSuccessfullyConvert() throws Exception {
        final RemoteMovieWrapperEntity wrapperEntity = new Builder()
                .build()
                .adapter(RemoteMovieWrapperEntity.class)
                .fromJson(mJsonExample);

        assertThat(wrapperEntity.mPage).isEqualTo(1);
        assertThat(wrapperEntity.mTotalResults).isEqualTo(8141);
        assertThat(wrapperEntity.mTotalPages).isEqualTo(408);
        final List<RemoteMovieEntity> results = wrapperEntity.mResults;
        assertThat(results.size()).isEqualTo(1);
        final RemoteMovieEntity movieEntity = results.get(0);
        assertThat(movieEntity.mVoteCount).isEqualTo(14054);
        assertThat(movieEntity.mId).isEqualTo(155);
        assertThat(movieEntity.mVideo).isEqualTo(false);
        assertThat(movieEntity.mVoteAverage).isEqualTo(8.3f);
        assertThat(movieEntity.mTitle).isEqualTo("The Dark Knight");
        assertThat(movieEntity.mPopularity).isEqualTo(34.165257f);
        assertThat(movieEntity.mPosterPath).isEqualTo("/1hRoyzDtpgMU7Dz4JF22RANzQO7.jpg");
        assertThat(movieEntity.mOriginalLanguage).isEqualTo("en");
        assertThat(movieEntity.mOriginalTitle).isEqualTo("The Dark Knight");
        assertThat(movieEntity.mBackdropPath).isEqualTo("/hqkIcbrOHL86UncnHIsHVcVmzue.jpg");
        assertThat(movieEntity.mAdult).isEqualTo(false);
        assertThat(movieEntity.mOverview).isEqualTo(
                "Batman raises the stakes in his war on crime. With the help of Lt. Jim Gordon and District Attorney Harvey Dent, Batman sets out to dismantle the remaining criminal organizations that plague the streets. The partnership proves to be effective, but they soon find themselves prey to a reign of chaos unleashed by a rising criminal mastermind known to the terrified citizens of Gotham as the Joker.");
        assertThat(movieEntity.mReleaseDate).isEqualTo("2008-07-16");
    }

}