package com.deadbeef.soundfreq;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.deadbeef.soundfreq.fragments.MediaPlayerFragment;
import com.deadbeef.soundfreq.models.Song;


public class MainActivity extends AppCompatActivity {

    public static int[] songs = new int[] {R.raw.song, R.raw.song2, R.raw.song3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        performFragmentTransaction(MediaPlayerFragment.newInstance());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    public void performFragmentTransaction(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

}
