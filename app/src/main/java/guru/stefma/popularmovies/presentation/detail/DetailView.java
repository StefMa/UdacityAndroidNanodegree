package guru.stefma.popularmovies.presentation.detail;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import guru.stefma.popularmovies.domain.Review;
import guru.stefma.popularmovies.domain.Video;
import java.util.List;
import net.grandcentrix.thirtyinch.TiView;

interface DetailView extends TiView {

    // TODO: Rename me to something like "set movie info"
    void populateUI(@NonNull final DetailMovieViewModel viewModel);

    void setVideos(@Nullable final List<Video> videos);

    void setReviews(@Nullable final List<Review> reviews);

    void showFullReview(@NonNull final String author, @NonNull final String content);

    void showYouTubeVideo(@NonNull final String youtubeKey);

    void updateFavoriteMenu(final boolean isFavorite);
}
