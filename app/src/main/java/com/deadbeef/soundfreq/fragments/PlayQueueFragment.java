package com.deadbeef.soundfreq.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deadbeef.soundfreq.R;

import butterknife.ButterKnife;

public class PlayQueueFragment extends Fragment {

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
        return v;
    }


}
