package guru.stefma.choicm.presentation.common;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.View;

/**
 * A {@link OnScrollListener} which can be attached to any {@link RecyclerView} via {@link RecyclerView#addOnScrollListener(OnScrollListener)}
 * to {@link FloatingActionButton#hide()} or {@link FloatingActionButton#show()} the given
 * {@link FloatingActionButton} based on the scroll state.
 */
public class RecyclerViewFabListener extends OnScrollListener {

    private static final int HIDE_SHOW_DELAY_IN_MS = 200;

    @NonNull
    private final FloatingActionButton mFab;

    public RecyclerViewFabListener(@NonNull final FloatingActionButton fab) {
        mFab = fab;
    }

    @Override
    public void onScrolled(final RecyclerView recyclerView, final int dx, final int dy) {
        if (dy > 0 && mFab.getVisibility() == View.VISIBLE) {
            mFab.postDelayed(mFab::hide, HIDE_SHOW_DELAY_IN_MS);
        } else if (dy < 0 && mFab.getVisibility() != View.VISIBLE) {
            mFab.postDelayed(mFab::show, HIDE_SHOW_DELAY_IN_MS);
        }
    }
}
