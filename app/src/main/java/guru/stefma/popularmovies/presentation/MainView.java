package guru.stefma.popularmovies.presentation;

import android.support.annotation.NonNull;
import guru.stefma.popularmovies.domain.Movie;
import guru.stefma.popularmovies.domain.usecase.favorite.FavoriteMovie;
import guru.stefma.popularmovies.presentation.detail.DetailMovieViewModel;
import java.util.List;
import net.grandcentrix.thirtyinch.TiView;

public interface MainView extends TiView {

    /**
     * Sets the movies to the UI.
     */
    void setMovies(@NonNull final List<Movie> movies);

    /**
     * Starts the movie details activity ({@link guru.stefma.popularmovies.presentation.detail.DetailActivity})
     * with the given {@link DetailMovieViewModel}.
     *
     * @param detailViewModel the viewModel which will be used to display the details
     */
    void openMovieDetails(@NonNull final DetailMovieViewModel detailViewModel);

    /**
     * Set all favorite movies to the UI.
     */
    void setFavoriteMovies(@NonNull final List<FavoriteMovie> favoriteMovies);

}
