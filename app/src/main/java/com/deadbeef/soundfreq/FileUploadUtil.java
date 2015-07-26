package com.deadbeef.soundfreq;

import android.app.Activity;

import com.cloudinary.Cloudinary;
import com.deadbeef.soundfreq.tasks.FileUploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by kyle on 7/25/15.
 */
public class FileUploadUtil {
    Map config = new HashMap();
    Cloudinary cloudinary;
    private String cloudname;
    private String apiKey;
    private String apiSecret;
    private Activity a;
    private String fileName;


    public FileUploadUtil(String cloudname, String apiKey, String apiSecret, Activity a, String fileName){
        this.cloudname = cloudname;
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.a = a;
        this.fileName = fileName;

        config.put("cloud_name",cloudname);
        config.put("api_key", apiKey);
        config.put("api_secret",apiSecret);

        cloudinary = new Cloudinary(config);
    }


    public void uploadFile(int fileId){
        FileUploadTask  task = new FileUploadTask(fileId,cloudinary,a,fileName);
        task.execute();


    }
}
