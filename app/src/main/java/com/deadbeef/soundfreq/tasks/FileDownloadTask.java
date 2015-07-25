package com.deadbeef.soundfreq.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.deadbeef.soundfreq.interfaces.OnFileDownloadedListener;

import org.apache.commons.io.IOUtils;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by tylorgarrett on 7/25/15.
 */
public class FileDownloadTask extends AsyncTask<Void, Void, Void>{
    private String stringUrl;
    private String name;
    private Context context;
    private OnFileDownloadedListener listener;

    public FileDownloadTask(String url, String name, Context context, OnFileDownloadedListener listener){
        this.name = name;
        this.stringUrl = url;
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(Void... params){
        try {
            URL u = new URL(stringUrl);
            DataInputStream stream = new DataInputStream(u.openStream());
            byte[] buffer = IOUtils.toByteArray(stream);
            FileOutputStream fos = context.openFileOutput(name, Context.MODE_PRIVATE);
            fos.write(buffer);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        FileOutputStream fos = null;
        super.onPostExecute(aVoid);
        try {
             fos = context.openFileOutput(name,Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            listener.onFileDownloaded(fos.getFD());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
