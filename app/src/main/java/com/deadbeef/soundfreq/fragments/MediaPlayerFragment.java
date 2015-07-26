package com.deadbeef.soundfreq.fragments;


import android.app.ActionBar;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
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
import com.deadbeef.soundfreq.interfaces.OnFileDownloadedListener;
import com.deadbeef.soundfreq.tasks.FileDownloadTask;

import com.deadbeef.soundfreq.models.PlayTimeModel;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;

import org.joda.time.DateTime;
import org.joda.time.Period;

import java.io.File;
import java.io.FileDescriptor;
import java.net.URISyntaxException;
import java.util.UUID;
import java.util.logging.SocketHandler;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MediaPlayerFragment extends Fragment implements MediaPlayer.OnCompletionListener{

    MediaPlayer mediaPlayer;
    AudioManager audioManager;
    boolean muted, musicPlaying;
    MainActivity mainActivity;
    int index = 0;

    @Bind(R.id.media_play_button)
    ImageButton play;

    @Bind(R.id.media_mute_button)
    ImageButton mute;

    @Bind(R.id.media_music_queue_button)
    ImageButton musicQueue;

    @Bind(R.id.media_skip_next_button)
    ImageButton nextSong;

    @Bind(R.id.media_skip_prev_button)
    ImageButton prevSong;

    String songName;
    String songAuthor;
    String imagePath;
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
        mediaPlayer = MediaPlayer.create(getActivity(), mainActivity.songs[index]);
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

        socket.on("prev", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "User pressed next", Toast.LENGTH_SHORT).show();
                        PlayPrevSong();
                    }
                });
            }
        });


        socket.on("next", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("tylor", "nextnext");
                        PlayNextSong();
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

    public void setUpMediaPlayer(int position){
        if ( mediaPlayer != null ){
            mediaPlayer.reset();
        }
        mediaPlayer = MediaPlayer.create(getActivity(), mainActivity.songs[position]);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.start();
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

    public void PlayNextSong(){
        index = (index + 1) % 3;
        index = Math.abs(index);
        Log.d("tylor", Integer.toString(index));
        setUpMediaPlayer(index);
    }

    public void PlayPrevSong(){
        if ( index != 0 ) {
            index = index - 1;
        } else {
            index = 2;
        }
        Log.d("tylor", Integer.toString(index));
        setUpMediaPlayer(index);
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

    @OnClick(R.id.media_skip_next_button)
    public void nextSong(){
        Log.d("tylor", "next song button");
        if ( !mediaPlayer.isPlaying() ){
            //update the play button to show pause
            musicPlaying = !musicPlaying;
            play.setImageResource(R.drawable.media_pause_button);
        }
        socket.emit("next","hello");
        Log.d("tylor", "next song button");
    }

    @OnClick(R.id.media_skip_prev_button)
    public void previousSong(){
        Log.d("tylor", "prevprev");
        socket.emit("prev","hello");
        Log.d("tylor", Integer.toString(index));
    }

    public void testDownload(){
        FileDownloadTask fileDownloadTask = new FileDownloadTask("http://tylorgarrett.com/images/me.jpg", "magic_file", getActivity(), new OnFileDownloadedListener() {
            @Override
            public void onFileDownloaded(FileDescriptor fd) {
                return ;
            }
        });
        fileDownloadTask.execute();
    }


    @Override
    public void onCompletion(MediaPlayer mp) {
        index = (index+1) % 3;
        Log.d("tylor", Integer.toString(index));
        setUpMediaPlayer(index);
    }
}
