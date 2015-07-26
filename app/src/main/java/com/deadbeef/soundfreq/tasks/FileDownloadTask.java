package com.deadbeef.soundfreq.tasks;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
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

    public FileDownloadTask(String url){
        this.stringUrl = url;
    }

    @Override
    protected Void doInBackground(Void... params){
        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;

        try {
            URL url = new URL(stringUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            if ( connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }

            input = connection.getInputStream();
            output = new FileOutputStream(Environment.getExternalStorageDirectory());

            byte data[] = new byte[10240];
            int count;
            while ((count = input.read(data)) != -1 ){
                output.write(data, 0, count);
            }

        } catch (IOException e){
            return null;
        } finally {
            try {
                if ( output != null ){
                    output.close();
                }
                if ( input != null ){
                    input.close();
                }
            } catch (IOException e) {
                return null;
            }

            if(connection != null){
                connection.disconnect();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        File f = Environment.getExternalStorageDirectory();
        Log.d("tylor", "File name: " + f);
        super.onPostExecute(aVoid);
    }
}
