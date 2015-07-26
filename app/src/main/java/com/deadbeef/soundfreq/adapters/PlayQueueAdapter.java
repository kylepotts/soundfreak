package com.deadbeef.soundfreq.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.deadbeef.soundfreq.MainActivity;
import com.deadbeef.soundfreq.R;

public class PlayQueueAdapter extends RecyclerView.Adapter<PlayQueueAdapter.ViewHolder>{

    static MainActivity main;

    @Override
    public PlayQueueAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.queue_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public PlayQueueAdapter(Context context) {
        this.main = (MainActivity) context;
    }

    @Override
    public void onBindViewHolder(PlayQueueAdapter.ViewHolder holder, int position) {
        TextView title = (TextView) holder.cardView.findViewById(R.id.queue_songTitle_textview);
        title.setText(main.names[position]);
        TextView artist = (TextView) holder.cardView.findViewById(R.id.queue_contributor_textview);
        artist.setText(main.artists[position]);
        ImageView cover = (ImageView) holder.cardView.findViewById(R.id.queue_albumCover_image_view);
        cover.setImageResource(main.album_covers[position]);
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
