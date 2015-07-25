package com.deadbeef.soundfreq.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created by kyle on 7/25/15.
 */
public class FileUploadTask extends AsyncTask<Void,Void,Void> {
    private Cloudinary cloudinary;
    private int fileId;
    private  Activity a;
    private String fileName;

    public FileUploadTask(int fileId, Cloudinary cloudinary, Activity a, String fileName){
        this.fileId = fileId;
        this.cloudinary = cloudinary;
        this.a = a;
        this.fileName = fileName;

    }
    @Override
    protected Void doInBackground(Void... params) {
        InputStream is = null;
            is = a.getResources().openRawResource(fileId);
        try {
            cloudinary.uploader().upload(is, ObjectUtils.asMap("public_id", fileName, "resource_type", "raw"));
        } catch(IOException e){
            e.printStackTrace();
        }
        String rawIdentifier = "raw:upload:"+fileName;
        String[] components = rawIdentifier.split(":");
        String url = cloudinary.url().resourceType(components[0]).type(components[1]).generate(components[2]);
        Log.d("FileUploadTask","uploaded"+ url);
        return null;
    }
}
