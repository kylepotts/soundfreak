package com.deadbeef.soundfreq.fragments;


import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.deadbeef.soundfreq.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MediaPlayerFragment extends Fragment {

    MediaPlayer mediaPlayer;

    @Bind(R.id.media_play)
    Button play;

    @Bind(R.id.media_pause)
    Button pause;

    public MediaPlayerFragment() {}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpMediaPlayer();
    }

    public static MediaPlayerFragment newInstance(){
        MediaPlayerFragment f = new MediaPlayerFragment();
        return f;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_media_player, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    public void setUpMediaPlayer(){
        mediaPlayer = MediaPlayer.create(getActivity(), R.raw.song);
    }

    @OnClick(R.id.media_play)
    public void playMedia(){
        mediaPlayer.start();
    }

    @OnClick(R.id.media_pause)
    public void pauseMedia(){
        mediaPlayer.pause();
    }




}
