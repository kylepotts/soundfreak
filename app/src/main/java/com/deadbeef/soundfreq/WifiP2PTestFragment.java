package com.deadbeef.soundfreq;


import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class WifiP2PTestFragment extends Fragment  {
    private Socket socket;
    private Button playButton;
    private Button pauseButton;
    Map config = new HashMap();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                    }
                });
            }
        });

         int FileId = R.raw.song;

    }


    public WifiP2PTestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_wifi_p2_ptest, container, false);
        playButton = (Button) v.findViewById(R.id.play_button);
        pauseButton = (Button) v.findViewById(R.id.pause_button);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socket.emit("play", "hello");
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socket.emit("pause","hello");
            }
        });

        return v;

    }


}


