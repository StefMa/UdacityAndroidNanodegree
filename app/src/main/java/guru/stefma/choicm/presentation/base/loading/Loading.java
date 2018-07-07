package guru.stefma.choicm.presentation.base.loading;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import java.lang.ref.WeakReference;

/**
 * A generic implementation of a Loading "View".
 *
 * Construct the class with a instance of the {@link FragmentManager}.
 * We need that to show the {@link LoadingFragment} as part of the current View.
 *
 * The given {@link FragmentManager} instance can be destroyed with {@link #destroy()}.
 * But do not worries about this. We pack the given instance into a {@link WeakReference} which
 * means it can be collected even without calling {@link #destroy()}.
 */
public class Loading {

    private static final String LOADING_TAG = "LOADING_TAG";

    @Nullable
    private WeakReference<FragmentManager> mFragmentManager;

    /**
     * Shows a loading view as part of the current view
     */
    public void show(@NonNull final FragmentManager fragmentManager) {
        mFragmentManager = new WeakReference<>(fragmentManager);
        new LoadingFragment().show(fragmentManager, LOADING_TAG);
    }

    /**
     * Hides the previous showed loading view
     */
    public void hide() {
        if (mFragmentManager != null && mFragmentManager.get() != null) {
            final FragmentManager fragmentManager = mFragmentManager.get();
            final Fragment loadingFragment = fragmentManager.findFragmentByTag(LOADING_TAG);
            if (loadingFragment != null) {
                fragmentManager.beginTransaction().remove(loadingFragment).commitNow();
            }
            destroy();
        }
    }

    public void destroy() {
        if (mFragmentManager != null) {
            mFragmentManager.clear();
        }
    }

}
