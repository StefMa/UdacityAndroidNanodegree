package guru.stefma.popularmovies.presentation.detail;

import android.support.annotation.NonNull;
import net.grandcentrix.thirtyinch.TiView;

interface DetailView extends TiView {

    void populateUI(@NonNull final DetailMovieViewModel viewModel);

}
