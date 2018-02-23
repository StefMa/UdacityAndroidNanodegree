package guru.stefma.popularmovies.presentation;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import guru.stefma.popularmovies.R;
import guru.stefma.popularmovies.domain.Movie;
import guru.stefma.popularmovies.presentation.MoviesListAdapter.MoviesListViewHolder;
import java.util.ArrayList;
import java.util.List;

class MoviesListAdapter extends Adapter<MoviesListViewHolder> {

    public interface OnMovieClickedListener {

        void onMovieClicked(final Movie movie);

    }

    /**
     * A listener which got called if we reached near the end of the list.
     *
     * You then set new/more items via {@link MoviesListAdapter#setMovies(List)}.
     */
    public interface LoadMoreListener {

        void loadMore();

    }

    @NonNull
    private List<Movie> mMovies = new ArrayList<>();

    @NonNull
    private final OnMovieClickedListener mMovieClickedListener;

    @NonNull
    private final LoadMoreListener mLoadMoreListener;

    public MoviesListAdapter(
            @NonNull final OnMovieClickedListener clickListener,
            @NonNull final LoadMoreListener loadMoreListener) {
        mMovieClickedListener = clickListener;
        mLoadMoreListener = loadMoreListener;
    }

    @Override
    public MoviesListViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View itemView = inflater.inflate(R.layout.item_movies_list, parent, false);
        return new MoviesListViewHolder(parent, itemView);
    }

    @Override
    public void onBindViewHolder(final MoviesListViewHolder holder, final int position) {
        Glide.with(holder.itemView).load(mMovies.get(position).getImageUrl()).into(holder.mMovieImage);
        holder.itemView.setOnClickListener(view ->
                mMovieClickedListener.onMovieClicked(mMovies.get(holder.getAdapterPosition())));

        // Call loadMoreListener if we reached near the bottom
        if (getItemCount() - 4 == position) {
            mLoadMoreListener.loadMore();
        }
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public void setMovies(@Nullable final List<Movie> movies) {
        if (movies == null) {
            mMovies = new ArrayList<>();
            notifyDataSetChanged();
            return;
        }
        mMovies = movies;
        notifyDataSetChanged();
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
