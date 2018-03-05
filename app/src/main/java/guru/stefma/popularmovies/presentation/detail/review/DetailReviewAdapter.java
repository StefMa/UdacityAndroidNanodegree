package guru.stefma.popularmovies.presentation.detail.review;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import guru.stefma.popularmovies.R;
import guru.stefma.popularmovies.domain.Review;
import guru.stefma.popularmovies.presentation.detail.review.DetailReviewAdapter.DetailReviewViewHolder;
import guru.stefma.popularmovies.presentation.views.LoadMoreRecyclerViewAdapter;

// TODO: Document
public class DetailReviewAdapter extends LoadMoreRecyclerViewAdapter<Review, DetailReviewViewHolder> {

    public interface OnReviewClickListener {

        void onReviewClick(@NonNull final Review review);

    }

    @NonNull
    private final OnReviewClickListener mReviewClickListener;

    public DetailReviewAdapter(
            @NonNull final LoadMoreListener loadMoreListener,
            @NonNull final OnReviewClickListener reviewClickListener) {
        super(loadMoreListener);
        mReviewClickListener = reviewClickListener;
    }

    @Override
    public DetailReviewViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.item_review_list, parent, false);
        return new DetailReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DetailReviewViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);

        final Review review = getItems().get(position);
        holder.mMore.setOnClickListener(view -> mReviewClickListener.onReviewClick(review));
        holder.mAuthor.setText(review.getAuthor());
        holder.mContent.setText(review.getContent());
    }

    public static class DetailReviewViewHolder extends RecyclerView.ViewHolder {

        private final TextView mAuthor;

        private final TextView mContent;

        private final View mMore;

        public DetailReviewViewHolder(final View itemView) {
            super(itemView);

            mAuthor = itemView.findViewById(R.id.item_review_list_author);
            mContent = itemView.findViewById(R.id.item_review_list_content);
            mMore = itemView.findViewById(R.id.item_review_list_more);
        }

    }
}
