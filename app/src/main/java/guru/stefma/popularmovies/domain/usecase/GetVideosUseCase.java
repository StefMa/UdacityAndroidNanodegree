package guru.stefma.popularmovies.domain.usecase;

import android.support.annotation.NonNull;
import guru.stefma.popularmovies.data.MoviesRepository;
import guru.stefma.popularmovies.data.remote.entity.RemoteVideoWrapperEntity.RemoteVideoEntity;
import guru.stefma.popularmovies.domain.Video;
import guru.stefma.popularmovies.domain.Video.Site;
import guru.stefma.popularmovies.domain.usecase.GetVideosUseCase.Params;
import guru.stefma.popularmovies.domain.usecase.base.BaseSingleUseCase;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import javax.inject.Inject;

// TODO: Add JavaDoc
public class GetVideosUseCase implements BaseSingleUseCase<List<Video>, Params> {

    public static class Params {

        private String mApiKey;

        private int mMovieId;

        public Params(final String apiKey, final int movieId) {
            mApiKey = apiKey;
            mMovieId = movieId;
        }

    }

    @NonNull
    private final MoviesRepository mMoviesRepo;

    @Inject
    public GetVideosUseCase(@NonNull final MoviesRepository moviesRepository) {
        mMoviesRepo = moviesRepository;
    }

    @Override
    public Single<List<Video>> execute(@NonNull final Params params) {
        return mMoviesRepo.getVideosForMovie(params.mApiKey, params.mMovieId)
                .subscribeOn(Schedulers.io())
                .map(wrapper -> wrapper.mResults)
                .toObservable()
                .flatMapIterable(list -> list)
                .map(this::mapRemoteVideoToVideo)
                .toList()
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Video mapRemoteVideoToVideo(final RemoteVideoEntity videoEntity) {
        return new Video(videoEntity.mName, videoEntity.mKey, Site.fromString(videoEntity.mSite));
    }
}
