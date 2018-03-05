package guru.stefma.popularmovies.presentation;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import guru.stefma.popularmovies.R;
import guru.stefma.popularmovies.domain.Movie;
import guru.stefma.popularmovies.domain.di.DependencyGraph;
import guru.stefma.popularmovies.domain.usecase.GetPopularMoviesUseCase;
import guru.stefma.popularmovies.domain.usecase.GetTopRatedMoviesUseCase;
import guru.stefma.popularmovies.domain.usecase.favorite.FavoriteMovie;
import guru.stefma.popularmovies.domain.usecase.favorite.GetFavoritesUseCase;
import guru.stefma.popularmovies.presentation.MainPresenter.SortBy;
import guru.stefma.popularmovies.presentation.MoviesListAdapter.OnMovieClickedListener;
import guru.stefma.popularmovies.presentation.detail.DetailActivity;
import guru.stefma.popularmovies.presentation.detail.DetailMovieViewModel;
import guru.stefma.popularmovies.presentation.views.LoadMoreRecyclerViewAdapter.LoadMoreListener;
import java.util.List;
import net.grandcentrix.thirtyinch.TiActivity;
import toothpick.Scope;

public class MainActivity extends TiActivity<MainPresenter, MainView>
        implements MainView, OnMovieClickedListener, LoadMoreListener {

    private static final String BUNDLE_KEY_LAYOUT_MANAGER_STATE = "layoutManagerState";

    private static final String BUNDLE_KEY_MENU_POPULARITY = "menuPopularity";

    private static final String BUNDLE_KEY_MENU_TOP_RATED = "menuTopRated";

    private static final String BUNDLE_KEY_MENU_FAVORITE = "menuFavorite";

    private GridLayoutManager mMoviesLayoutManager;

    @Nullable
    private Parcelable mMoviesLayoutManagerInstanceState;

    private boolean mIsMenuPopularityChecked = true;

    private boolean mIsMenuTopRatedChecked;

    private boolean mIsMenuFavoriteChecked;

    private RecyclerView mFavoriteList;

    private RecyclerView mMoviesList;

    @NonNull
    private MoviesListAdapter mMoviesListAdapter = new MoviesListAdapter(this, this);

    @NonNull
    private FavoriteMoviesListAdapter mFavoriteListAdapter = new FavoriteMoviesListAdapter();

    @NonNull
    @Override
    public MainPresenter providePresenter() {
        final Scope appScope = DependencyGraph.getApplicationScope();
        final GetPopularMoviesUseCase popularUseCase = appScope.getInstance(GetPopularMoviesUseCase.class);
        final GetTopRatedMoviesUseCase topRatedUseCase = appScope.getInstance(GetTopRatedMoviesUseCase.class);
        final GetFavoritesUseCase favoritesUseCase = appScope.getInstance(GetFavoritesUseCase.class);
        SortBy sortBy;
        if (mIsMenuPopularityChecked) {
            sortBy = SortBy.POPULARITY;
        } else if (mIsMenuTopRatedChecked) {
            sortBy = SortBy.TOP_RATED;
        } else if (mIsMenuFavoriteChecked) {
            sortBy = SortBy.FAVORITE;
        } else {
            throw new IllegalArgumentException("One menu item should be set to checked as default!");
        }
        return new MainPresenter(popularUseCase, topRatedUseCase, favoritesUseCase, sortBy);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar();
        setupMovieRecyclerView();
        setupFavoritesRecyclerView();

        if (savedInstanceState != null) {
            // Recover LayoutManagers instance state to recover the state on orientation change
            mMoviesLayoutManagerInstanceState = savedInstanceState.getParcelable(BUNDLE_KEY_LAYOUT_MANAGER_STATE);
            // Recover menu checked state on orientation change
            mIsMenuPopularityChecked = savedInstanceState.getBoolean(BUNDLE_KEY_MENU_POPULARITY);
            mIsMenuTopRatedChecked = savedInstanceState.getBoolean(BUNDLE_KEY_MENU_TOP_RATED);
            mIsMenuFavoriteChecked = savedInstanceState.getBoolean(BUNDLE_KEY_MENU_FAVORITE);
        }
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save LayoutManagers instance state to recover the state on orientation change
        outState.putParcelable(BUNDLE_KEY_LAYOUT_MANAGER_STATE, mMoviesLayoutManager.onSaveInstanceState());
        // Save the checked state to restore on orientation change
        outState.putBoolean(BUNDLE_KEY_MENU_POPULARITY, mIsMenuPopularityChecked);
        outState.putBoolean(BUNDLE_KEY_MENU_TOP_RATED, mIsMenuTopRatedChecked);
        outState.putBoolean(BUNDLE_KEY_MENU_FAVORITE, mIsMenuFavoriteChecked);
    }

    /**
     * Setup the Toolbar.
     */
    private void setupToolbar() {
        final Toolbar toolbar = findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);
    }

    /**
     * Setup the RecyclerView with a specific {@link android.support.v7.widget.RecyclerView.LayoutManager} and
     * a {@link android.support.v7.widget.RecyclerView.Adapter}.
     */
    private void setupMovieRecyclerView() {
        mMoviesLayoutManager = new GridLayoutManager(this, 2);
        mMoviesList = findViewById(R.id.activity_main_movies_list);
        mMoviesList.setLayoutManager(mMoviesLayoutManager);
        mMoviesList.setAdapter(mMoviesListAdapter);
    }

    /**
     * Setup the RecyclerView for the favorite movies only.
     */
    private void setupFavoritesRecyclerView() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mFavoriteList = findViewById(R.id.activity_main_favorite_movies_list);
        mFavoriteList.setLayoutManager(layoutManager);
        mFavoriteList.setAdapter(mFavoriteListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        int menuItemId;
        if (mIsMenuPopularityChecked) {
            menuItemId = R.id.activity_menu_main_sort_by_popular;
        } else if (mIsMenuTopRatedChecked) {
            menuItemId = R.id.activity_menu_main_sort_by_rated;
        } else if (mIsMenuFavoriteChecked) {
            menuItemId = R.id.activity_menu_main_sort_by_favorite;
        } else {
            throw new IllegalArgumentException("One menu item should be set to checked as default!");
        }
        final MenuItem menuItem = menu.findItem(menuItemId);
        menuItem.setChecked(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.activity_menu_main_sort_by_popular:
                if (item.isChecked()) {
                    // Already checked. Nothing to do
                    return true;
                }

                item.setChecked(!item.isChecked());
                getPresenter().setSort(SortBy.POPULARITY);
                mIsMenuPopularityChecked = true;
                mIsMenuTopRatedChecked = false;
                mIsMenuFavoriteChecked = false;
                return true;
            case R.id.activity_menu_main_sort_by_rated:
                if (item.isChecked()) {
                    // Already checked. Nothing to do
                    return true;
                }

                item.setChecked(!item.isChecked());
                getPresenter().setSort(SortBy.TOP_RATED);
                mIsMenuPopularityChecked = false;
                mIsMenuTopRatedChecked = true;
                mIsMenuFavoriteChecked = false;
                return true;
            case R.id.activity_menu_main_sort_by_favorite:
                if (item.isChecked()) {
                    // Already checked. Nothing to do
                    return true;
                }

                item.setChecked(!item.isChecked());
                getPresenter().setSort(SortBy.FAVORITE);
                mIsMenuPopularityChecked = false;
                mIsMenuTopRatedChecked = false;
                mIsMenuFavoriteChecked = true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setMovies(@NonNull final List<Movie> movies) {
        mMoviesListAdapter.setItems(movies);
        mMoviesList.setVisibility(View.VISIBLE);
        mFavoriteList.setVisibility(View.GONE);

        // Recover the LayoutManagers instance state
        // Afterwards set to null to don't restore it every time this method is called.
        if (mMoviesLayoutManagerInstanceState != null) {
            mMoviesLayoutManager.onRestoreInstanceState(mMoviesLayoutManagerInstanceState);
            mMoviesLayoutManagerInstanceState = null;
        }
    }

    /**
     * User clicked on a item from the {@link RecyclerView}.
     * Open the detail view with the given {@link Movie}.
     *
     * @param movie the {@link Movie} which was clicked.
     */
    @Override
    public void onMovieClicked(final Movie movie) {
        getPresenter().onMovieClicked(movie);
    }

    @Override
    public void openMovieDetails(@NonNull final DetailMovieViewModel detailViewModel) {
        startActivity(DetailActivity.getIntent(this, detailViewModel));
    }

    @Override
    public void setFavoriteMovies(@NonNull final List<FavoriteMovie> favoriteMovies) {
        mFavoriteListAdapter.setItems(favoriteMovies);
        mMoviesList.setVisibility(View.GONE);
        mFavoriteList.setVisibility(View.VISIBLE);
    }

    /**
     * Movies list reached the end of the {@link RecyclerView}.
     * Call the {@link #getPresenter()} to request more items.
     */
    @Override
    public void loadMore() {
        getPresenter().loadMoreMovies();
    }
}
