package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * A {@link FrameLayout} which holds a simple {@link com.google.android.gms.ads.AdView}.
 *
 * This will be used inside the {@link MainActivityFragment} to display an ad in the free version of the app.
 */
public class AdWrapperView extends FrameLayout {

    public AdWrapperView(@NonNull final Context context) {
        this(context, null);
    }

    public AdWrapperView(@NonNull final Context context, @Nullable final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AdWrapperView(@NonNull final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        final View view = inflate(getContext(), R.layout.view_ad_wrapper, this);
        final AdView adView = view.findViewById(R.id.adView);

        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        adView.loadAd(adRequest);
    }
}
