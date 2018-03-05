package guru.stefma.popularmovies.presentation.detail;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.Tab;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewAnimator;
import com.bumptech.glide.Glide;
import guru.stefma.popularmovies.R;
import guru.stefma.popularmovies.domain.Review;
import guru.stefma.popularmovies.domain.Video;
import guru.stefma.popularmovies.domain.di.DependencyGraph;
import guru.stefma.popularmovies.domain.usecase.GetReviewsUseCase;
import guru.stefma.popularmovies.domain.usecase.GetVideosUseCase;
import guru.stefma.popularmovies.domain.usecase.favorite.IsMovieFavoriteUseCase;
import guru.stefma.popularmovies.domain.usecase.favorite.UpdateMovieFavoriteStateUseCase;
import guru.stefma.popularmovies.presentation.detail.DetailVideoAdapter.OnVideoClickListener;
import guru.stefma.popularmovies.presentation.detail.review.DetailFullReviewActivity;
import guru.stefma.popularmovies.presentation.detail.review.DetailReviewAdapter;
import guru.stefma.popularmovies.presentation.detail.review.DetailReviewAdapter.OnReviewClickListener;
import guru.stefma.popularmovies.presentation.views.LoadMoreRecyclerViewAdapter.LoadMoreListener;
import java.util.List;
import net.grandcentrix.thirtyinch.TiActivity;
import toothpick.Scope;

public class DetailActivity extends TiActivity<DetailPresenter, DetailView>
        implements DetailView, LoadMoreListener, OnVideoClickListener, OnReviewClickListener {

    private static final String INTENT_EXTRA_VIEW_MODEL = "intent_extra_view_model";

    private static final int START_TAB_POSITION = 0;

    private static final String KEY_CURRENT_POSITION = "CURRENT_POSITION";

    private int mCurrentTabPosition = START_TAB_POSITION;

    /**
     * A "marker" boolean which will be used to show the favorite icon and title correctly.
     */
    private boolean mIsFavorite;

    private TextView mDescription;

    private ImageView mImage;

    private TextView mRelease;

    private TabLayout mTabLayout;

    /**
     * A "coordinator" view which can be used two switch between views.
     *
     * Primary used for switching ehe "main" view if a tab got selected.
     *
     * @see #showTabAt(int)
     * @see #setupTabLayout()
     */
    private ViewAnimator mViewAnimator;

    private TextView mVoteAvg;

    private final DetailVideoAdapter mVideoAdapter = new DetailVideoAdapter(this);

    private final DetailReviewAdapter mReviewAdapter = new DetailReviewAdapter(this, this);

    public static Intent getIntent(@NonNull final Context context, @NonNull final DetailMovieViewModel viewModel) {
        final Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(INTENT_EXTRA_VIEW_MODEL, viewModel);
        return intent;
    }

    @NonNull
    @Override
    public DetailPresenter providePresenter() {
        final Scope appScope = DependencyGraph.getApplicationScope();
        final GetVideosUseCase videosUseCase = appScope.getInstance(GetVideosUseCase.class);
        final GetReviewsUseCase reviewsUseCase = appScope.getInstance(GetReviewsUseCase.class);
        final IsMovieFavoriteUseCase isMovieFavoriteUseCase = appScope.getInstance(IsMovieFavoriteUseCase.class);
        final UpdateMovieFavoriteStateUseCase updateMovieFavoriteStateUseCase =
                appScope.getInstance(UpdateMovieFavoriteStateUseCase.class);
        final DetailMovieViewModel viewModel = getIntent().getParcelableExtra(INTENT_EXTRA_VIEW_MODEL);
        return new DetailPresenter(
                viewModel,
                videosUseCase,
                reviewsUseCase,
                isMovieFavoriteUseCase,
                updateMovieFavoriteStateUseCase
        );
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mViewAnimator = findViewById(R.id.activity_detail_viewanimator);
        setupToolbar();
        setupTabLayout();
        setupVideoRecyclerView();
        setupReviewRecyclerView();
        mImage = findViewById(R.id.activity_detail_image);
        mRelease = findViewById(R.id.activity_detail_release);
        mVoteAvg = findViewById(R.id.activity_detail_vote_avg);
        mDescription = findViewById(R.id.activity_detail_description);
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CURRENT_POSITION, mCurrentTabPosition);
    }

    @Override
    protected void onRestoreInstanceState(final Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mTabLayout.getTabAt(savedInstanceState.getInt(KEY_CURRENT_POSITION)).select();
    }

    private void setupToolbar() {
        final Toolbar toolbar = findViewById(R.id.activity_detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupTabLayout() {
        mTabLayout = findViewById(R.id.activity_detail_tabs);
        mTabLayout.addOnTabSelectedListener(new SimpleTabSelectedListener() {
            @Override
            public void onTabSelected(final Tab tab) {
                showTabAt(tab.getPosition());
            }
        });
    }

    /**
     * Shows/Switch a tab at the given position.
     *
     * If the given position equal to the {@link #mCurrentTabPosition} nothing happen.
     */
    private void showTabAt(final int position) {
        if (mCurrentTabPosition == position) {
            return;
        }

        final int diff = position - mCurrentTabPosition;
        mCurrentTabPosition = position;
        if (diff > 0) {
            for (int i = 0; i < diff; i++) {
                mViewAnimator.showNext();
            }
        }
        if (diff < 0) {
            for (int i = 0; i > diff; i--) {
                mViewAnimator.showPrevious();
            }
        }
    }

    private void setupVideoRecyclerView() {
        final RecyclerView videoList = findViewById(R.id.activity_detail_video_recyclerview);
        videoList.setLayoutManager(new LinearLayoutManager(this));
        videoList.setAdapter(mVideoAdapter);
    }

    private void setupReviewRecyclerView() {
        final RecyclerView reviewList = findViewById(R.id.activity_detail_review_recyclerview);
        reviewList.setLayoutManager(new LinearLayoutManager(this));
        reviewList.setAdapter(mReviewAdapter);
        final DividerItemDecoration itemDecoration =
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(getDrawable(R.drawable.item_decoration_review_list));
        reviewList.addItemDecoration(itemDecoration);
    }

    @Override
    public void populateUI(@NonNull final DetailMovieViewModel viewModel) {
        Glide.with(this).load(viewModel.getImageUrl()).into(mImage);
        mRelease.setText(viewModel.getReleaseDate());
        mVoteAvg.setText(viewModel.getVoteAverage());
        mDescription.setText(viewModel.getDescription());
        getSupportActionBar().setTitle(viewModel.getTitle());
    }

    @Override
    public void setVideos(@Nullable final List<Video> videos) {
        mVideoAdapter.setVideos(videos);

        // TODO: Restore instance state
    }

    @Override
    public void setReviews(@Nullable final List<Review> reviews) {
        mReviewAdapter.setItems(reviews);

        // TODO: Restore instance state
    }

    @Override
    public void showFullReview(@NonNull final String author, @NonNull final String content) {
        startActivity(DetailFullReviewActivity.getIntent(this, author, content));
    }

    /**
     * Tries to start the YouTube App via undocumented deeplink.
     *
     * If it's not possible just open the youtube website...
     *
     * @param youtubeKey the key to the youtube video
     */
    @Override
    public void showYouTubeVideo(@NonNull final String youtubeKey) {
        final Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + youtubeKey));
        final Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + youtubeKey));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }

    /**
     * Updates the {@link #mIsFavorite} and call {@link #supportInvalidateOptionsMenu()} which loads to
     * an updated favorite icon.
     */
    @Override
    public void updateFavoriteMenu(final boolean isFavorite) {
        mIsFavorite = isFavorite;
        supportInvalidateOptionsMenu();
    }

    @Override
    public void loadMore() {
        getPresenter().loadMoreReviews();
    }

    @Override
    public void onReviewClick(@NonNull final Review review) {
        getPresenter().onReviewClicked(review);
    }

    @Override
    public void onVideoClick(@NonNull final Video video) {
        getPresenter().onVideoClicked(video);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu_detail, menu);
        return true;
    }

    /**
     * Change the {@link MenuItem} with id {@link R.id#activity_menu_detail_favorite} based on {@link #mIsFavorite}
     */
    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        final MenuItem favoriteMenu = menu.findItem(R.id.activity_menu_detail_favorite);
        if (mIsFavorite) {
            favoriteMenu.setIcon(R.drawable.ic_unfavorite)
                    .setTitle(R.string.activity_menu_detail_unfavorite);
        } else {
            favoriteMenu.setIcon(R.drawable.ic_favorite)
                    .setTitle(R.string.activity_menu_detail_favorite);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.activity_menu_detail_favorite:
                getPresenter().onFavoriteClicked();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
