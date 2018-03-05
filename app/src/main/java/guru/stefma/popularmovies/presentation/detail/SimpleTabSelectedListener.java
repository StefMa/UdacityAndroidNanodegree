package guru.stefma.popularmovies.presentation.detail;

import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.design.widget.TabLayout.Tab;

/**
 * A simple interface extension of the {@link OnTabSelectedListener} which does nothing by default.
 *
 * You can use that for simply override only these methods you need.
 */
public interface SimpleTabSelectedListener extends OnTabSelectedListener {

    @Override
    default void onTabSelected(final Tab tab) {

    }

    @Override
    default void onTabUnselected(final Tab tab) {

    }

    @Override
    default void onTabReselected(final Tab tab) {

    }
}
