package guru.stefma.popularmovies.presentation.detail;

import android.support.annotation.NonNull;
import net.grandcentrix.thirtyinch.TiPresenter;

class DetailPresenter extends TiPresenter<DetailView> {

    private final DetailMovieViewModel mViewModel;

    public DetailPresenter(final DetailMovieViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Override
    protected void onAttachView(@NonNull final DetailView view) {
        super.onAttachView(view);

        view.populateUI(mViewModel);
    }
}
