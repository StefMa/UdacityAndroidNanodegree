package guru.stefma.baking.presentation.video;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

public class VideoPlayerHelper {

    public static SimpleExoPlayer createExoPlayerInstance(@NonNull final Context context) {
        return ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(context),
                new DefaultTrackSelector(), new DefaultLoadControl()
        );
    }

    public static MediaSource createHttpMediaSource(@NonNull final Uri uri) {
        return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory("StefMa-Baking-App")).
                createMediaSource(uri);
    }

}
