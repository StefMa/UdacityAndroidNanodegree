package guru.stefma.popularmovies.presentation.views;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import java.util.ArrayList;
import java.util.List;

/**
 * A generic {@link Adapter} which can be used if you have a "endless" list of items which will/should be loaded
 * step by step.
 *
 * You have to pass a {@link LoadMoreListener} which will be called each time we reached the end of the list.
 *
 * You can call {@link #setItems(List)} to set new items. Set it to {@link null} will be hide/empty the list.
 */
public abstract class LoadMoreRecyclerViewAdapter<Item, VH extends ViewHolder> extends Adapter<VH> {

    /**
     * A listener which got called if we reached near the end of the list.
     *
     * You can then set new/more items via {@link LoadMoreRecyclerViewAdapter#setItems(List)}.
     */
    public interface LoadMoreListener {

        void loadMore();

    }

    public static final int THRESHOLD = 4;

    @NonNull
    private List<Item> mItems = new ArrayList<>();

    @NonNull
    private final LoadMoreListener mLoadMoreListener;

    public LoadMoreRecyclerViewAdapter(@NonNull final LoadMoreListener loadMoreListener) {
        mLoadMoreListener = loadMoreListener;
    }

    @CallSuper
    @Override
    public void onBindViewHolder(final VH holder, final int position) {
        callListener(position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    private void callListener(final int position) {
        if (getItemCount() - THRESHOLD == position) {
            mLoadMoreListener.loadMore();
        }
    }

    public void setItems(@Nullable List<Item> items) {
        if (items == null) {
            items = new ArrayList<>();
        }
        mItems = items;
        // TODO: Calculate the Diff via DiffUtil...
        notifyDataSetChanged();
    }

    @NonNull
    protected List<Item> getItems() {
        return mItems;
    }
}
