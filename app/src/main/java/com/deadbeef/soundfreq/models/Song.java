package com.deadbeef.soundfreq.models;

public class Song {
    private String songName;
    private String songArtist;
    private String albumImage;
    private String url;

    public Song(String songName, String songArtist, String albumImage, String url){
        this.songName = songName;
        this.songArtist = songArtist;
        this.albumImage = albumImage;
        this.url = url;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongArtist() {
        return songArtist;
    }

    public void setSongArtist(String songArtist) {
        this.songArtist = songArtist;
    }

    public String getAlbumImage() {
        return albumImage;
    }

    public void setAlbumImage(String albumImage) {
        this.albumImage = albumImage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
