package com.deadbeef.soundfreq.fragments;


import android.app.ActionBar;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.deadbeef.soundfreq.models.PlayTimeModel;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;

import org.joda.time.DateTime;
import org.joda.time.Period;

import java.io.File;
import java.net.URISyntaxException;
import java.util.UUID;
import java.util.logging.SocketHandler;

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
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isPlaying",false).commit();

        setUpMediaPlayer();

        try {
            socket = IO.socket("https://soundfreq.herokuapp.com/");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        socket.connect();
        socket.emit("enqueue", "http://res.cloudinary.com/dwigxrles/raw/upload/v1437901258/song1.jpg");



        socket.on("play", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Gson gson = new Gson();
                        PlayTimeModel model = gson.fromJson(args[0].toString(), PlayTimeModel.class);
                        Log.d("MediaPlayBackTime", model.getTime());
                        Log.d("MediaPlayBackFile", model.getFileUrl());
                        DateTime time = new DateTime();
                        DateTime time2 = new DateTime(model.getTime());
                        Period period = new Period(time,time2);
                        Log.d("MediaPlayBackTime", "" + period.getMillis());
                        Toast.makeText(getActivity(), "User pressed play", Toast.LENGTH_SHORT).show();
                        new CountDownTimer(period.getMillis(),100){

                            @Override
                            public void onTick(long millisUntilFinished) {

                            }

                            @Override
                            public void onFinish() {
                                playMusic();

                            }
                        }.start();
                    }
                });

            }
        });
        String filename = UUID.randomUUID().toString()+".jpg";
        FileUploadUtil util = new FileUploadUtil("dwigxrles","576657185946952","tExg7b9_wprcVxoo387BmH-p2uE", getActivity(),filename);
        util.uploadFile(R.raw.song);

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
    public void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isPlaying",musicPlaying).commit();
    }

    @Override
    public void onResume(){
        super.onResume();
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        Boolean isPlaying = sharedPreferences.getBoolean("isPlaying", false);
        if(isPlaying){
            play.setImageResource(R.drawable.media_pause_button);
        } else {
            play.setImageResource(R.drawable.media_play_button);
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        socket.disconnect();
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
            //mediaPlayer.start();
            imageButton.setImageResource(R.drawable.media_pause_button);
            socket.emit("play", "hello");
        } else {
            mediaPlayer.pause();
            imageButton.setImageResource(R.drawable.media_play_button);
            musicPlaying = !musicPlaying;
            socket.emit("pause","hello");

        }



    }


    @OnClick(R.id.media_mute_button)
    public void muteMedia(View v){
        ImageButton muteButton =  (ImageButton) v;
        int drawable = muted ? R.drawable.media_volume_on : R.drawable.media_mute_button;
        muteButton.setImageResource(drawable);
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
