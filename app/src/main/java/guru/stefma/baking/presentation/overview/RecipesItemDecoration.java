package guru.stefma.baking.presentation.overview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;

public class RecipesItemDecoration extends ItemDecoration {

    private final int mSpanCount;

    public RecipesItemDecoration(final int spanCount) {
        mSpanCount = spanCount;
    }

    @Override
    public void getItemOffsets(final Rect outRect, final View view, final RecyclerView parent, final State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if (mSpanCount == 1) {
            // Phone
            // Set the bottom always but not for the last item
            if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
                outRect.bottom = 80;
            }
        } else {
            // Tablet
            outRect.set(30, 0, 30, 60);
            // Don't set the the bottom padding on the last raw
            int itemsInLastRow = parent.getAdapter().getItemCount() % mSpanCount;
            // If itemsInLastRow is zero we have to remove the bottom for the mSpanCount
            if (itemsInLastRow == 0) {
                itemsInLastRow = mSpanCount;
            }
            if (parent.getChildAdapterPosition(view) >= parent.getAdapter().getItemCount() - itemsInLastRow) {
                outRect.bottom = 0;
            }
        }

    }
}
