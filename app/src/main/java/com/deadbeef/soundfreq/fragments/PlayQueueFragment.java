package com.deadbeef.soundfreq.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deadbeef.soundfreq.R;
import com.deadbeef.soundfreq.adapters.PlayQueueAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PlayQueueFragment extends Fragment {

    @Bind(R.id.play_queue_recyclerview)
    public RecyclerView recyclerView;

    public RecyclerView.Adapter adapter;
    public RecyclerView.LayoutManager layoutManager;

    @Bind(R.id.toolbar)
    public Toolbar toolbar;

    public static PlayQueueFragment newInstance() {
        PlayQueueFragment fragment = new PlayQueueFragment();
        return fragment;
    }

    public PlayQueueFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_play_queue, container, false);
        ButterKnife.bind(this, v);
        toolbar.setTitle("Queue");
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PlayQueueAdapter();
        recyclerView.setAdapter(adapter);
        return v;
    }


}
