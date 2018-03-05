package guru.stefma.popularmovies.presentation;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import guru.stefma.popularmovies.BuildConfig;
import guru.stefma.popularmovies.domain.Movie;
import guru.stefma.popularmovies.domain.usecase.GetPopularMoviesUseCase;
import guru.stefma.popularmovies.domain.usecase.GetTopRatedMoviesUseCase;
import guru.stefma.popularmovies.domain.usecase.LastPageReachedException;
import guru.stefma.popularmovies.domain.usecase.favorite.FavoriteMovie;
import guru.stefma.popularmovies.domain.usecase.favorite.GetFavoritesUseCase;
import guru.stefma.popularmovies.presentation.detail.DetailMovieViewModel;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import java.util.List;
import net.grandcentrix.thirtyinch.TiPresenter;
import timber.log.Timber;

public class MainPresenter extends TiPresenter<MainView> {

    enum SortBy {
        POPULARITY,
        TOP_RATED,
        FAVORITE
    }

    /**
     * Current list of movies which are displayed.
     *
     * Needs to be cached for orientation change.
     */
    private List<Movie> mCurrentMovies;

    private List<FavoriteMovie> mCurrentFavorites;

    private Observable<List<Movie>> mPopularObservable;

    private Observable<List<Movie>> mTopRatedObservable;

    private final GetPopularMoviesUseCase mPopularUseCase;

    private final GetTopRatedMoviesUseCase mTopRatedUseCase;

    private final GetFavoritesUseCase mFavoriteUseCase;

    /**
     * Indicated the current sorted movies.
     */
    private SortBy mSortBy;

    private Disposable mCurrentSubscription;

    /**
     * Indicate if we currently loading something.
     *
     * Set to false if we are in "idle" more.
     * Set to true if we are currently loading movies.
     */
    private boolean mOnLoading = false;

    public MainPresenter(
            @NonNull final GetPopularMoviesUseCase popularUseCase,
            @NonNull final GetTopRatedMoviesUseCase topRatedUseCase,
            @NonNull final GetFavoritesUseCase favoritesUseCase,
            @NonNull final SortBy sortBy) {
        mPopularUseCase = popularUseCase;
        mTopRatedUseCase = topRatedUseCase;
        mFavoriteUseCase = favoritesUseCase;
        mSortBy = sortBy;
    }

    @Override
    protected void onCreate() {
        super.onCreate();

        loadMovies();
    }

    @Override
    protected void onAttachView(@NonNull final MainView view) {
        super.onAttachView(view);

        switch (mSortBy) {
            case FAVORITE:
                if (mCurrentFavorites != null) {
                    view.setFavoriteMovies(mCurrentFavorites);
                }
                break;
            default:
                if (mCurrentMovies != null) {
                    view.setMovies(mCurrentMovies);
                }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mCurrentSubscription != null) {
            mCurrentSubscription.dispose();
        }
    }

    /**
     * Loads movies based on the current {@link #mSortBy} (evaluated by {@link #getMoviesObservableFromUseCase()}).
     *
     * This will also (if not null) {@link Disposable#dispose()} on the {@link #mCurrentSubscription}. Otherwise we
     * will be subscribe multiple times to the same {@link Observable} which leads to wrong usage.
     */
    private void loadMovies() {
        // Dispose the current subscription.
        // Only one subscription is allowed!
        if (mCurrentSubscription != null) {
            mCurrentSubscription.dispose();
        }

        mOnLoading = true;
        mCurrentSubscription = getMoviesObservableFromUseCase()
                .doOnNext(ignore -> mOnLoading = false)
                .doOnError(ignore -> mOnLoading = false)
                .subscribe(list -> {
                            mCurrentMovies = list;
                            sendToView(view -> view.setMovies(list));
                        },
                        throwable -> {
                            if (throwable instanceof LastPageReachedException) {
                                Timber.i(throwable, "Last page reached. Should we handle that?!");
                            } else {
                                Timber.e(throwable, "Error while loading movies.");
                            }
                        });
    }

    /**
     * Get the correct {@link Observable} based on the current {@link SortBy}.
     */
    private Observable<List<Movie>> getMoviesObservableFromUseCase() {
        switch (mSortBy) {
            case POPULARITY:
                if (mPopularObservable == null) {
                    mPopularObservable = mPopularUseCase.execute(BuildConfig.THEMOVIEDBAPIKEY);
                }
                return mPopularObservable;
            case TOP_RATED:
                if (mTopRatedObservable == null) {
                    mTopRatedObservable = mTopRatedUseCase.execute(BuildConfig.THEMOVIEDBAPIKEY);
                }
                return mTopRatedObservable;
            default:
                throw new IllegalArgumentException("Unknown type " + mSortBy);
        }
    }

    /**
     * Loads more movies based on the current {@link SortBy} and onl if {@link #mOnLoading} is set to false.
     * Otherwise a loading process is currently happen.
     */
    void loadMoreMovies() {
        if (!mOnLoading) {
            switch (mSortBy) {
                case POPULARITY:
                    mPopularUseCase.loadMore();
                    break;
                case TOP_RATED:
                    mTopRatedUseCase.loadMore();
                    break;
                default:
                    throw new IllegalArgumentException("Unknown type " + mSortBy);
            }
        }
    }

    /**
     * Update the current sort.
     *
     * UI will be updated as soon as the movies for the new sort are loaded.
     */
    void setSort(final SortBy sort) {
        mSortBy = sort;
        if (sort != SortBy.FAVORITE) {
            loadMovies();
        } else {
            loadFavorites();
        }
    }

    /**
     * Loads the favorites and sent it to the view.
     *
     * The given single will be - of course - directly terminate.
     */
    private void loadFavorites() {
        mFavoriteUseCase.execute(null)
                .subscribe(favoriteMovies -> {
                    mCurrentFavorites = favoriteMovies;
                    sendToView(mainView -> mainView.setFavoriteMovies(favoriteMovies));
                }, throwable -> Timber.e(throwable, "Can't load favorites.. :/"));
    }

    /**
     * On a movie was clicked.
     *
     * Convert {@link Movie} to {@link guru.stefma.popularmovies.presentation.detail.DetailMovieViewModel} and
     * start {@link guru.stefma.popularmovies.presentation.detail.DetailActivity}
     *
     * @param movie the {@link Movie} which was selected.
     */
    void onMovieClicked(final Movie movie) {
        final DetailMovieViewModel detailViewModel = mapMovieToDetailMovieViewModel(movie);
        getViewOrThrow().openMovieDetails(detailViewModel);
    }

    private DetailMovieViewModel mapMovieToDetailMovieViewModel(final Movie movie) {
        @SuppressLint("DefaultLocale") final String average = String.format("%.1f/10", movie.getVoteAverage());
        final String releaseYear = movie.getReleaseDate().split("-")[0];
        return new DetailMovieViewModel(
                movie.getId(),
                movie.getImageUrl(),
                movie.getDescription(),
                movie.getTitle(),
                average,
                releaseYear);
    }

}
