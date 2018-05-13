package guru.stefma.baking.presentation.detail.overview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;

class StepItemDecoration extends ItemDecoration {

    @Override
    public void getItemOffsets(final Rect outRect, final View view, final RecyclerView parent, final State state) {
        super.getItemOffsets(outRect, view, parent, state);

        // Set the bottom always but not for the last item
        if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
            outRect.bottom = 16;
        }
    }
}
