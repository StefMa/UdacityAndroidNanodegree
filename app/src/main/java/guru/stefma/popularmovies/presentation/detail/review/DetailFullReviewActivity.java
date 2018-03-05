package guru.stefma.popularmovies.presentation.detail.review;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import guru.stefma.popularmovies.R;

public class DetailFullReviewActivity extends AppCompatActivity {

    private static final String INTENT_EXTRA_AUTHOR = "INTENT_EXTRA_AUTHOR";

    private static final String INTENT_EXTRA_REVIEW = "INTENT_EXTRA_REVIEW";

    public static Intent getIntent(
            @NonNull final Context context,
            @NonNull final String author,
            @NonNull final String review) {
        final Intent intent = new Intent(context, DetailFullReviewActivity.class);
        intent.putExtra(INTENT_EXTRA_AUTHOR, author);
        intent.putExtra(INTENT_EXTRA_REVIEW, review);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_review);

        final Toolbar toolbar = findViewById(R.id.activity_full_review_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final TextView authorView = findViewById(R.id.activity_full_review_author);
        final String author = getIntent().getStringExtra(INTENT_EXTRA_AUTHOR);
        authorView.setText(author);

        final TextView reviewView = findViewById(R.id.activity_full_review_review);
        final String review = getIntent().getStringExtra(INTENT_EXTRA_REVIEW);
        reviewView.setText(review);
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
