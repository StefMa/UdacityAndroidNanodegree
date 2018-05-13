package guru.stefma.baking.presentation.detail.step;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import guru.stefma.baking.R;
import guru.stefma.baking.presentation.model.StepViewModel;
import guru.stefma.baking.presentation.video.VideoPlayerHelper;
import net.grandcentrix.thirtyinch.TiFragment;

public class RecipeDetailStepFragment extends TiFragment<RecipeDetailStepPresenter, RecipeDetailStepView>
        implements RecipeDetailStepView {

    private static final String BUNDLE_KEY_STEP = "bundle_key_step";

    public static final String BUNDLE_KEY_PLAYER_POSITION = "PLAYER_POSITION";

    public static final String BUNDLE_KEY_PLAYER_WINDOW_INDEX = "PLAYER_WINDOW_INDEX";

    public static final String BUNDLE_KEY_PLAYER_WHEN_READY = "PLAYER_WHEN_READY";

    private long mPlayPos;

    private boolean mPlayWhenReady;

    private SimpleExoPlayer mPlayer;

    private PlayerView mPlayerView;

    @Nullable
    private StepViewModel mStep;

    private int mWindowIndex;

    public static RecipeDetailStepFragment newInstance() {
        return new RecipeDetailStepFragment();
    }

    @NonNull
    @Override
    public RecipeDetailStepPresenter providePresenter() {
        return new RecipeDetailStepPresenter();
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.containsKey(BUNDLE_KEY_STEP)) {
            mStep = savedInstanceState.getParcelable(BUNDLE_KEY_STEP);
        }
    }

    @Nullable
    @Override
    public View onCreateView(
            final LayoutInflater inflater,
            @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState
    ) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.fragment_recipe_step, container, false);
        mPlayerView = view.findViewById(R.id.fragment_recipe_step_video);
        if (mStep != null) {
            updateUiWithStep(view, mStep, savedInstanceState);
        }
        return view;
    }

    public void setStep(@NonNull final StepViewModel step, @Nullable Bundle savedInstanceState) {
        mStep = step;
        updateUiWithStep(getView(), step, savedInstanceState);
    }

    private void updateUiWithStep(
            @NonNull final View view,
            @NonNull final StepViewModel step,
            @Nullable Bundle savedInstanceState) {
        final TextView stepTitle = view.findViewById(R.id.fragment_recipe_step_title);
        stepTitle.setText(step.getStep());

        final TextView stepDesc = view.findViewById(R.id.fragment_recipe_step_description);
        stepDesc.setText(step.getDescription());

        final ImageView thumbnailView = view.findViewById(R.id.fragment_recipe_step_thumbnail);
        final String thumbnailUrl = step.getThumbNailUrl();
        if (!TextUtils.isEmpty(thumbnailUrl)) {
            Glide.with(this).load(thumbnailUrl).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable final GlideException e, final Object model,
                        final Target<Drawable> target,
                        final boolean isFirstResource) {
                    thumbnailView.setVisibility(View.GONE);
                    return true;
                }

                @Override
                public boolean onResourceReady(final Drawable resource, final Object model,
                        final Target<Drawable> target, final DataSource dataSource,
                        final boolean isFirstResource) {
                    thumbnailView.setVisibility(View.VISIBLE);
                    return false;
                }
            }).into(thumbnailView);
        } else {
            thumbnailView.setVisibility(View.GONE);
        }

        if (TextUtils.isEmpty(step.getVideoUrl())) {
            mPlayerView.setVisibility(View.GONE);
            return;
        } else {
            mPlayerView.setVisibility(View.VISIBLE);
        }

        // https://codelabs.developers.google.com/codelabs/exoplayer-intro/
        if (mPlayer == null) {
            mPlayer = VideoPlayerHelper.createExoPlayerInstance(requireContext());
            final MediaSource source = VideoPlayerHelper.createHttpMediaSource(Uri.parse(mStep.getVideoUrl()));
            mPlayer.prepare(source, true, false);
        }
        mPlayerView.setPlayer(mPlayer);

        if (savedInstanceState != null) {
            mPlayPos = savedInstanceState.getLong(BUNDLE_KEY_PLAYER_POSITION);
            mWindowIndex = savedInstanceState.getInt(BUNDLE_KEY_PLAYER_WINDOW_INDEX);
            mPlayer.seekTo(mWindowIndex, mPlayPos);
            mPlayWhenReady = savedInstanceState.getBoolean(BUNDLE_KEY_PLAYER_WHEN_READY);
            mPlayer.setPlayWhenReady(mPlayWhenReady);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mPlayer == null && mPlayerView != null && mStep != null) {
            mPlayer = VideoPlayerHelper.createExoPlayerInstance(requireContext());
            final MediaSource source = VideoPlayerHelper.createHttpMediaSource(Uri.parse(mStep.getVideoUrl()));
            mPlayer.prepare(source, true, false);
            mPlayer.seekTo(mWindowIndex, mPlayPos);
            mPlayer.setPlayWhenReady(mPlayWhenReady);
            mPlayerView.setPlayer(mPlayer);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_KEY_STEP, mStep);
        if (mPlayer != null) {
            mPlayPos = mPlayer.getContentPosition();
            outState.putLong(BUNDLE_KEY_PLAYER_POSITION, mPlayPos);
            mWindowIndex = mPlayer.getCurrentWindowIndex();
            outState.putInt(BUNDLE_KEY_PLAYER_WINDOW_INDEX, mWindowIndex);
            mPlayWhenReady = mPlayer.getPlayWhenReady();
            outState.putBoolean(BUNDLE_KEY_PLAYER_WHEN_READY, mPlayWhenReady);
        }
    }
}
