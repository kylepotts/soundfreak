package com.deadbeef.soundfreq.fragments;


import android.app.ActionBar;
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
import android.widget.ImageButton;

import com.deadbeef.soundfreq.MainActivity;
import com.deadbeef.soundfreq.R;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MediaPlayerFragment extends Fragment {

    MediaPlayer mediaPlayer;
    AudioManager audioManager;
    boolean muted, musicPlaying;
    MainActivity mainActivity;

    @Bind(R.id.media_play_button)
    ImageButton play;

    @Bind(R.id.media_mute_button)
    ImageButton mute;

    @Bind(R.id.media_music_queue_button)
    ImageButton musicQueue;

    public MediaPlayerFragment() {}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        muted = false;
        musicPlaying = false;
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

    @OnClick(R.id.media_play_button)
    public void playMedia(View v){
        ImageButton imageButton = (ImageButton) v;
        if ( !musicPlaying ){
            mediaPlayer.start();
            imageButton.setImageResource(R.drawable.media_pause_button);
        } else {
            mediaPlayer.pause();
            imageButton.setImageResource(R.drawable.media_play_button);
        }
        musicPlaying = !musicPlaying;
    }


    @OnClick(R.id.media_mute_button)
    public void muteMedia(){
        audioManager.setStreamMute(AudioManager.STREAM_MUSIC, !muted);
        muted = !muted;
    }

    @OnClick(R.id.media_music_queue_button)
    public void openQueue(View v){
        mainActivity.performFragmentTransaction(PlayQueueFragment.newInstance());
    }

    public void testFileStuff(){

    }

}
