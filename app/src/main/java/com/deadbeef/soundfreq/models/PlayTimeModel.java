package com.deadbeef.soundfreq.models;

/**
 * Created by kyle on 7/25/15.
 */
public class PlayTimeModel {
    private String time;
    private String fileUrl;

    public PlayTimeModel(String time, String fileUrl){
        this.time = time;
        this.fileUrl = fileUrl;
    }

    public String getTime(){
        return time;
    }

    public  String getFileUrl(){
        return fileUrl;
    }
}
