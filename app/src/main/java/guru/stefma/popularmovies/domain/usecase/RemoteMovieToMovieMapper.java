package guru.stefma.popularmovies.domain.usecase;

import guru.stefma.popularmovies.data.remote.entity.RemoteMovieWrapperEntity.RemoteMovieEntity;
import guru.stefma.popularmovies.domain.Movie;
import io.reactivex.functions.Function;

public class RemoteMovieToMovieMapper implements Function<RemoteMovieEntity, Movie> {

    @Override
    public Movie apply(final RemoteMovieEntity remoteEntity) throws Exception {
        // TODO: Use the prefix url in a better way :D
        final String mImage = "http://image.tmdb.org/t/p/w185/" + remoteEntity.mPosterPath;
        final String description = remoteEntity.mOverview;
        final String title = remoteEntity.mTitle;
        final float voteAverage = remoteEntity.mVoteAverage;
        final String releaseDate = remoteEntity.mReleaseDate;
        return new Movie(mImage, description, title, voteAverage, releaseDate);
    }

}
