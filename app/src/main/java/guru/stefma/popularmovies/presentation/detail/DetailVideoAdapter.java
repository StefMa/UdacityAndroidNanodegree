package guru.stefma.popularmovies.presentation.detail;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import guru.stefma.popularmovies.R;
import guru.stefma.popularmovies.domain.Video;
import guru.stefma.popularmovies.presentation.detail.DetailVideoAdapter.DetailVideoViewHolder;
import java.util.ArrayList;
import java.util.List;

/**
 * A implementation of {@link RecyclerView.Adapter} which can be used to display a list of Videos.
 */
public class DetailVideoAdapter extends RecyclerView.Adapter<DetailVideoViewHolder> {

    public interface OnVideoClickListener {

        void onVideoClick(@NonNull final Video video);

    }

    @NonNull
    private List<Video> mVideos = new ArrayList<>();

    @NonNull
    private final OnVideoClickListener mOnVideoClickListener;

    public DetailVideoAdapter(@NonNull final OnVideoClickListener onVideoClickListener) {
        mOnVideoClickListener = onVideoClickListener;
    }

    @Override
    public DetailVideoViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View view = layoutInflater.inflate(R.layout.item_video_list, parent, false);
        return new DetailVideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DetailVideoViewHolder holder, final int position) {
        final Video video = mVideos.get(position);
        holder.itemView.setOnClickListener(view -> {
            mOnVideoClickListener.onVideoClick(video);
        });
        holder.mName.setText(video.getName());
    }

    @Override
    public int getItemCount() {
        return mVideos.size();
    }

    /**
     * Set the videos to this {@link RecyclerView.Adapter}.
     *
     * Will immediately update the adapter with {@link Adapter#notifyDataSetChanged()}.
     */
    public void setVideos(@Nullable List<Video> videos) {
        if (videos == null) {
            videos = new ArrayList<>();
        }
        mVideos = videos;
        notifyDataSetChanged();
    }

    public static class DetailVideoViewHolder extends RecyclerView.ViewHolder {

        private final TextView mName;

        public DetailVideoViewHolder(final View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.item_video_list_name);
        }

    }
}
