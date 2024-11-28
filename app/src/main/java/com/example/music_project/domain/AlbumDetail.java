package com.example.music_project.domain;

public class AlbumDetail {
    private int songId;
    private int albumId;

    public AlbumDetail(){

    }
    public AlbumDetail(int songId, int albumId) {
        this.songId = songId;
        this.albumId = albumId;
    }

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }
}
