package guru.stefma.popularmovies.presentation;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import guru.stefma.popularmovies.R;
import guru.stefma.popularmovies.domain.Movie;
import guru.stefma.popularmovies.presentation.MoviesListAdapter.MoviesListViewHolder;
import guru.stefma.popularmovies.presentation.views.LoadMoreRecyclerViewAdapter;

class MoviesListAdapter extends LoadMoreRecyclerViewAdapter<Movie, MoviesListViewHolder> {

    public interface OnMovieClickedListener {

        void onMovieClicked(final Movie movie);

    }

    @NonNull
    private final OnMovieClickedListener mMovieClickedListener;

    public MoviesListAdapter(
            @NonNull final LoadMoreListener loadMoreListener,
            @NonNull final OnMovieClickedListener clickListener) {
        super(loadMoreListener);
        mMovieClickedListener = clickListener;
    }

    @Override
    public MoviesListViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View itemView = inflater.inflate(R.layout.item_movies_list, parent, false);
        return new MoviesListViewHolder(parent, itemView);
    }

    @Override
    public void onBindViewHolder(final MoviesListViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        Glide.with(holder.itemView).load(getItems().get(position).getImageUrl()).into(holder.mMovieImage);
        holder.itemView.setOnClickListener(view ->
                mMovieClickedListener.onMovieClicked(getItems().get(holder.getAdapterPosition())));
    }

    /**
     * A {@link RecyclerView.ViewHolder} which will set the given itemView height based on the parent.
     */
    static class MoviesListViewHolder extends RecyclerView.ViewHolder {

        final ImageView mMovieImage;

        MoviesListViewHolder(final View parent, final View itemView) {
            super(itemView);
            setItemViewHeight(parent, itemView);

            mMovieImage = itemView.findViewById(R.id.item_movies_list_image);
        }

        private void setItemViewHeight(final View parent, final View itemView) {
            final int itemViewHeight = parent.getMeasuredHeight() / 2;
            itemView.setMinimumHeight(itemViewHeight);
        }

    }

}
