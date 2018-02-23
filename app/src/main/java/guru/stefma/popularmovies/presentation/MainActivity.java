package guru.stefma.popularmovies.presentation;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import guru.stefma.popularmovies.R;
import guru.stefma.popularmovies.data.MoviesRepository;
import guru.stefma.popularmovies.domain.Movie;
import guru.stefma.popularmovies.domain.di.DependencyGraph;
import guru.stefma.popularmovies.domain.usecase.GetPopularMoviesUseCase;
import guru.stefma.popularmovies.domain.usecase.GetTopRatedMoviesUseCase;
import guru.stefma.popularmovies.presentation.MainPresenter.SortBy;
import guru.stefma.popularmovies.presentation.MoviesListAdapter.LoadMoreListener;
import guru.stefma.popularmovies.presentation.MoviesListAdapter.OnMovieClickedListener;
import guru.stefma.popularmovies.presentation.detail.DetailActivity;
import guru.stefma.popularmovies.presentation.detail.DetailMovieViewModel;
import java.util.List;
import javax.inject.Inject;
import net.grandcentrix.thirtyinch.TiActivity;
import toothpick.Scope;

public class MainActivity extends TiActivity<MainPresenter, MainView>
        implements MainView, OnMovieClickedListener, LoadMoreListener {

    private static final String BUNDLE_KEY_LAYOUT_MANAGER_STATE = "layoutManagerState";

    private static final String BUNDLE_KEY_MENU_POPULARITY = "menuPopularity";

    private static final String BUNDLE_KEY_MENU_TOP_RATED = "menuTopRated";

    @Inject
    MoviesRepository mMoviesRepository;

    private GridLayoutManager mLayoutManager;

    @Nullable
    private Parcelable mLayoutManagerInstanceState;

    private boolean mIsMenuPopularityChecked = true;

    private boolean mIsMenuTopRatedChecked;

    @NonNull
    @Override
    public MainPresenter providePresenter() {
        final Scope appScope = DependencyGraph.getApplicationScope();
        final GetPopularMoviesUseCase popularUseCase = appScope.getInstance(GetPopularMoviesUseCase.class);
        final GetTopRatedMoviesUseCase topRatedUseCase = appScope.getInstance(GetTopRatedMoviesUseCase.class);
        SortBy sortBy = null;
        if (mIsMenuPopularityChecked) {
            sortBy = SortBy.POPULARITY;
        } else if (mIsMenuTopRatedChecked) {
            sortBy = SortBy.TOP_RATED;
        } else {
            throw new IllegalArgumentException("One menu item should be set to checked as default!");
        }
        return new MainPresenter(popularUseCase, topRatedUseCase, sortBy);
    }

    @NonNull
    private MoviesListAdapter mMoviesListAdapter = new MoviesListAdapter(this, this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar();
        setupRecyclerView();

        if (savedInstanceState != null) {
            // Recover LayoutManagers instance state to recover the state on orientation change
            mLayoutManagerInstanceState = savedInstanceState.getParcelable(BUNDLE_KEY_LAYOUT_MANAGER_STATE);
            // Recover menu checked state on orientation change
            mIsMenuPopularityChecked = savedInstanceState.getBoolean(BUNDLE_KEY_MENU_POPULARITY);
            mIsMenuTopRatedChecked = savedInstanceState.getBoolean(BUNDLE_KEY_MENU_TOP_RATED);
        }
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save LayoutManagers instance state to recover the state on orientation change
        outState.putParcelable(BUNDLE_KEY_LAYOUT_MANAGER_STATE, mLayoutManager.onSaveInstanceState());
        // Save the checked state to restore on orientation change
        outState.putBoolean(BUNDLE_KEY_MENU_POPULARITY, mIsMenuPopularityChecked);
        outState.putBoolean(BUNDLE_KEY_MENU_TOP_RATED, mIsMenuTopRatedChecked);
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
    private void setupRecyclerView() {
        mLayoutManager = new GridLayoutManager(this, 2);
        final RecyclerView recyclerView = findViewById(R.id.activity_main_movies_list);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mMoviesListAdapter);
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
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setMovies(@NonNull final List<Movie> movies) {
        mMoviesListAdapter.setMovies(movies);

        // Recover the LayoutManagers instance state
        // Afterwards set to null to don't restore it everytime this method is called.
        if (mLayoutManagerInstanceState != null) {
            mLayoutManager.onRestoreInstanceState(mLayoutManagerInstanceState);
            mLayoutManagerInstanceState = null;
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

    /**
     * Movies list reached the end of the {@link RecyclerView}.
     * Call the {@link #getPresenter()} to request more items.
     */
    @Override
    public void loadMore() {
        getPresenter().loadMoreMovies();
    }
}
