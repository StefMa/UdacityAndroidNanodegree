package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * A {@link FrameLayout} which holds nothing. It is just empty.
 *
 * This will be used inside the {@link MainActivityFragment} to display nothing instead of an ad in the paid version
 */
public class AdWrapperView extends FrameLayout {

    public AdWrapperView(@NonNull final Context context) {
        super(context);
    }

    public AdWrapperView(@NonNull final Context context, @Nullable final AttributeSet attrs) {
        super(context, attrs);
    }

    public AdWrapperView(@NonNull final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
