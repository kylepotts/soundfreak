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
import android.widget.Toast;

import com.deadbeef.soundfreq.FileUploadUtil;
import com.deadbeef.soundfreq.MainActivity;
import com.deadbeef.soundfreq.R;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.io.File;
import java.net.URISyntaxException;
import java.util.UUID;

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

    private Socket socket;

    public MediaPlayerFragment() {}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        muted = false;
        musicPlaying = false;
        setUpMediaPlayer();

        try {
            socket = IO.socket("https://soundfreq.herokuapp.com/");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        socket.connect();

        socket.on("play", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "User pressed play", Toast.LENGTH_SHORT).show();
                        playMusic();
                    }
                });

            }
        });
        String filename = UUID.randomUUID().toString()+".jpg";
        FileUploadUtil util = new FileUploadUtil("dwigxrles","576657185946952","tExg7b9_wprcVxoo387BmH-p2uE", getActivity(),filename);
        //util.uploadFile(R.raw.song);

        socket.on("pause", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("WifiP2p", "in call");
                        Toast.makeText(getActivity(), "User pressed pause", Toast.LENGTH_SHORT).show();
                        pauseMusic();
                    }
                });
            }
        });
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

    public void playMusic(){
        if(!musicPlaying){
            play.setImageResource(R.drawable.media_pause_button);
            mediaPlayer.start();
            musicPlaying = !musicPlaying;
        }
    }

    public void  pauseMusic(){
        if(musicPlaying){
            play.setImageResource(R.drawable.media_play_button);
            mediaPlayer.pause();
            musicPlaying = !musicPlaying;
        }
    }

    @OnClick(R.id.media_play_button)
    public void playMedia(View v){
        ImageButton imageButton = (ImageButton) v;
        if ( !musicPlaying ){
            mediaPlayer.start();
            imageButton.setImageResource(R.drawable.media_pause_button);
            socket.emit("play", "hello");
        } else {
            mediaPlayer.pause();
            imageButton.setImageResource(R.drawable.media_play_button);
            socket.emit("pause","hello");

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
