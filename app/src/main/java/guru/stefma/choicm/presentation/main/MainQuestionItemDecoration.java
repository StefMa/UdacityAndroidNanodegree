package guru.stefma.choicm.presentation.main;

import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

/**
 * Decorates the items for the {@link MainQuestionAdapter}.
 */
class MainQuestionItemDecoration extends ItemDecoration {

    private final int mLeftRightPadding;

    private final int mTopBottomPadding;

    MainQuestionItemDecoration() {
        final DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        mLeftRightPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, displayMetrics);
        mTopBottomPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, displayMetrics);
    }

    @Override
    public void getItemOffsets(final Rect outRect, final View view, final RecyclerView parent, final State state) {
        outRect.set(mLeftRightPadding, mTopBottomPadding, mLeftRightPadding, mTopBottomPadding);
    }
}

