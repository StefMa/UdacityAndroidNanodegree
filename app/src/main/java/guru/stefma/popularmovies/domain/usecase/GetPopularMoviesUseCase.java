package guru.stefma.popularmovies.domain.usecase;

import android.support.annotation.NonNull;
import guru.stefma.popularmovies.data.MoviesRepository;
import guru.stefma.popularmovies.data.remote.entity.RemoteMovieWrapperEntity;
import io.reactivex.Single;
import javax.inject.Inject;

/**
 * A concrete implementation of {@link BaseMoviesUseCase} which will use
 * {@link MoviesRepository#getPopularMovies(String, Integer)} to receive movies.
 */
public class GetPopularMoviesUseCase extends BaseMoviesUseCase {

    private final MoviesRepository mMoviesRepo;

    @Inject
    public GetPopularMoviesUseCase(@NonNull final MoviesRepository moviesRepository) {
        mMoviesRepo = moviesRepository;
    }

    @Override
    protected Single<RemoteMovieWrapperEntity> getSortCriteriaSingle(
            @NonNull final String apiKey, final int currentPage) {
        return mMoviesRepo.getPopularMovies(apiKey, currentPage);
    }

}
