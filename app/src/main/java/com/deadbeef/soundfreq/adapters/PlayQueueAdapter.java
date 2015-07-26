package com.deadbeef.soundfreq.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deadbeef.soundfreq.R;

public class PlayQueueAdapter extends RecyclerView.Adapter<PlayQueueAdapter.ViewHolder>{

    @Override
    public PlayQueueAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.queue_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(PlayQueueAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.cardView = itemView;
        }

        public void setText(){

        }
    }
}
