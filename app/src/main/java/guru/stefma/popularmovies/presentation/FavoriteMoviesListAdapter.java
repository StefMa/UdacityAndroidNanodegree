package guru.stefma.popularmovies.presentation;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import guru.stefma.popularmovies.R;
import guru.stefma.popularmovies.domain.usecase.favorite.FavoriteMovie;
import guru.stefma.popularmovies.presentation.FavoriteMoviesListAdapter.FavoriteMoviesListViewHolder;
import java.util.ArrayList;
import java.util.List;

class FavoriteMoviesListAdapter extends Adapter<FavoriteMoviesListViewHolder> {

    @NonNull
    private List<FavoriteMovie> mFavoriteMovies = new ArrayList<>();

    @Override
    public FavoriteMoviesListViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.item_favorite_list, parent, false);
        return new FavoriteMoviesListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FavoriteMoviesListViewHolder holder, final int position) {
        holder.mFavorite.setText(mFavoriteMovies.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mFavoriteMovies.size();
    }

    public void setItems(@Nullable List<FavoriteMovie> favoriteMovies) {
        if (favoriteMovies == null) {
            favoriteMovies = new ArrayList<>();
        }
        mFavoriteMovies = favoriteMovies;
        notifyDataSetChanged();
    }

    static class FavoriteMoviesListViewHolder extends RecyclerView.ViewHolder {

        final TextView mFavorite;

        public FavoriteMoviesListViewHolder(final View itemView) {
            super(itemView);

            mFavorite = itemView.findViewById(R.id.item_favorite_list_name);
        }

    }
}
