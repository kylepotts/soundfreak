package com.deadbeef.soundfreq.fragments;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
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
    AudioManager audioManager;
    boolean muted;

    @Bind(R.id.media_play)
    Button play;

    @Bind(R.id.media_pause)
    Button pause;

    @Bind(R.id.media_mute)
    Button mute;

    public MediaPlayerFragment() {}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        muted = false;
        setUpMediaPlayer();
    }

    public static MediaPlayerFragment newInstance(){
        MediaPlayerFragment f = new MediaPlayerFragment();
        return f;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.media_player_fragment, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    public void setUpMediaPlayer(){
        mediaPlayer = MediaPlayer.create(getActivity(), R.raw.song);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    @OnClick(R.id.media_play)
    public void playMedia(){
        mediaPlayer.start();
    }

    @OnClick(R.id.media_pause)
    public void pauseMedia(){
        mediaPlayer.pause();
    }

    @OnClick(R.id.media_mute)
    public void muteMedia(){
        audioManager.setStreamMute(AudioManager.STREAM_MUSIC, !muted);
        muted = !muted;
    }






}