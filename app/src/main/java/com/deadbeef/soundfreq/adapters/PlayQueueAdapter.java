package com.deadbeef.soundfreq.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by tylorgarrett on 7/25/15.
 */
public class PlayQueueAdapter extends RecyclerView.Adapter<PlayQueueAdapter.ViewHolder>{

    @Override
    public PlayQueueAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(PlayQueueAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
