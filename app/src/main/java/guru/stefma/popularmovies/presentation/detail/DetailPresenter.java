package guru.stefma.popularmovies.presentation.detail;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import guru.stefma.popularmovies.BuildConfig;
import guru.stefma.popularmovies.domain.Review;
import guru.stefma.popularmovies.domain.Video;
import guru.stefma.popularmovies.domain.Video.Site;
import guru.stefma.popularmovies.domain.usecase.GetReviewsUseCase;
import guru.stefma.popularmovies.domain.usecase.GetVideosUseCase;
import guru.stefma.popularmovies.domain.usecase.LastPageReachedException;
import guru.stefma.popularmovies.domain.usecase.favorite.FavoriteMovie;
import guru.stefma.popularmovies.domain.usecase.favorite.IsMovieFavoriteUseCase;
import guru.stefma.popularmovies.domain.usecase.favorite.UpdateMovieFavoriteStateUseCase;
import guru.stefma.popularmovies.domain.usecase.favorite.UpdateMovieFavoriteStateUseCase.Params;
import java.util.List;
import net.grandcentrix.thirtyinch.TiPresenter;
import net.grandcentrix.thirtyinch.rx2.RxTiPresenterDisposableHandler;
import timber.log.Timber;

class DetailPresenter extends TiPresenter<DetailView> {


    /**
     * The current list of videos.
     *
     * Needs to be cached for orientation change.
     */
    @Nullable
    private List<Video> mVideos;

    /**
     * The current list of reviews.
     *
     * Needs to be cached for orientation change.
     */
    @Nullable
    private List<Review> mReviews;

    private final RxTiPresenterDisposableHandler mDisposableHandler
            = new RxTiPresenterDisposableHandler(this);

    private final DetailMovieViewModel mViewModel;

    private boolean mIsFavorite;

    private final GetVideosUseCase mVideosUseCase;

    private final GetReviewsUseCase mReviewsUseCase;

    private final IsMovieFavoriteUseCase mIsFavoriteUseCase;

    private final UpdateMovieFavoriteStateUseCase mUpdateFavoriteUseCase;

    public DetailPresenter(
            @NonNull final DetailMovieViewModel viewModel,
            @NonNull final GetVideosUseCase videosUseCase,
            @NonNull final GetReviewsUseCase reviewsUseCase,
            @NonNull final IsMovieFavoriteUseCase isMovieFavoriteUseCase,
            @NonNull final UpdateMovieFavoriteStateUseCase updateMovieFavoriteStateUseCase) {
        mViewModel = viewModel;
        mVideosUseCase = videosUseCase;
        mReviewsUseCase = reviewsUseCase;
        mIsFavoriteUseCase = isMovieFavoriteUseCase;
        mUpdateFavoriteUseCase = updateMovieFavoriteStateUseCase;
    }

    @Override
    protected void onCreate() {
        super.onCreate();

        // TODO: Implement empty view (for both)
        // Should be shown if throwable is instance of "NoPagesAvailable" or so

        mDisposableHandler.manageDisposable(
                mVideosUseCase.execute(new GetVideosUseCase.Params(BuildConfig.THEMOVIEDBAPIKEY, mViewModel.getId()))
                        .subscribe(videos -> {
                                    mVideos = videos;
                                    sendToView(detailView -> detailView.setVideos(mVideos));
                                },
                                throwable -> Timber.e(throwable, "Error while loading videos.")));

        mDisposableHandler.manageDisposable(
                mReviewsUseCase
                        .execute(new GetReviewsUseCase.Params(BuildConfig.THEMOVIEDBAPIKEY, mViewModel.getId()))
                        .subscribe(reviews -> {
                                    mReviews = reviews;
                                    sendToView(detailView -> detailView.setReviews(reviews));
                                },
                                throwable -> {
                                    if (throwable instanceof LastPageReachedException) {
                                        Timber.i("Last paged of reviews reached.");
                                    } else {
                                        Timber.e(throwable, "Error while loading reviews");
                                    }
                                }));
    }

    @Override
    protected void onAttachView(@NonNull final DetailView view) {
        super.onAttachView(view);

        view.populateUI(mViewModel);

        if (mVideos != null) {
            view.setVideos(mVideos);
        }

        if (mReviews != null) {
            view.setReviews(mReviews);
        }

        mIsFavoriteUseCase.execute(mViewModel.getId())
                .subscribe(isFavorite -> {
                            mIsFavorite = isFavorite;
                            sendToView(detailView -> detailView.updateFavoriteMenu(isFavorite));
                        },
                        throwable -> Timber.e(throwable, "There was an issue by checking if a movie is a favorite"));
    }

    /**
     * Load more reviews by calling {@link GetReviewsUseCase#loadMore()}.
     *
     * Make sure that {@link GetReviewsUseCase#execute(Object)} was called before!
     */
    void loadMoreReviews() {
        mReviewsUseCase.loadMore();
    }

    /**
     * Get called if on a review "read more" was clicked.
     *
     * Will extract some information from the {@link Review} object and start the
     * {@link guru.stefma.popularmovies.presentation.detail.review.DetailFullReviewActivity}
     */
    void onReviewClicked(@NonNull final Review review) {
        getViewOrThrow().showFullReview(review.getAuthor(), review.getContent());
    }

    /**
     * Get called if a video was clicked in the list.
     *
     * Will "prepare" a {@link android.content.Intent} which can start the YouTube App.
     */
    void onVideoClicked(@NonNull final Video video) {
        if (video.getSite() == Site.YouTube) {
            getViewOrThrow().showYouTubeVideo(video.getKey());
        } else {
            Timber.d("Can't handle site %s", video.getSite());
        }
    }

    /**
     * Get called if we press on the favorite icon.
     *
     * At this time we don't know if we favorite or unfavorite.
     */
    void onFavoriteClicked() {
        mUpdateFavoriteUseCase.execute(new Params(createMovie(), !mIsFavorite))
                .toSingleDefault(!mIsFavorite)
                .subscribe((newFavoriteState) -> {
                    mIsFavorite = newFavoriteState;
                    sendToView(detailView -> detailView.updateFavoriteMenu(newFavoriteState));
                }, throwable -> {
                    Timber.e(throwable, "Can't update favorite state :/");
                    sendToView(detailView -> detailView.updateFavoriteMenu(mIsFavorite));
                });
    }

    private FavoriteMovie createMovie() {
        return new FavoriteMovie(
                mViewModel.getId(),
                mViewModel.getTitle()
        );
    }
}
