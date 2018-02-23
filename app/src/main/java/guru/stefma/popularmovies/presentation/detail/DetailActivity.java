package guru.stefma.popularmovies.presentation.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import guru.stefma.popularmovies.R;
import net.grandcentrix.thirtyinch.TiActivity;

public class DetailActivity extends TiActivity<DetailPresenter, DetailView>
        implements DetailView {

    public static final String INTENT_EXTRA_VIEW_MODEL = "intent_extra_view_model";

    private TextView mDescription;

    private ImageView mImage;

    private TextView mRelease;

    private TextView mVoteAvg;

    public static Intent getIntent(@NonNull final Context context, @NonNull final DetailMovieViewModel viewModel) {
        final Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(INTENT_EXTRA_VIEW_MODEL, viewModel);
        return intent;
    }

    @NonNull
    @Override
    public DetailPresenter providePresenter() {
        final DetailMovieViewModel viewModel = getIntent().getParcelableExtra(INTENT_EXTRA_VIEW_MODEL);
        return new DetailPresenter(viewModel);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setupToolbar();
        mImage = findViewById(R.id.activity_detail_image);
        mRelease = findViewById(R.id.activity_detail_release);
        mVoteAvg = findViewById(R.id.activity_detail_vote_avg);
        mDescription = findViewById(R.id.activity_detail_description);
    }

    private void setupToolbar() {
        final Toolbar toolbar = findViewById(R.id.activity_detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
