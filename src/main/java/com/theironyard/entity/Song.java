package com.theironyard.entity;

import java.util.List;

/**
 * Created by davehochstrasser on 10/3/16.
 */
public class Song {
    private String artist;
    private String id;
    private String name;
    private String album;
    private List<String>genres;
    private String picture;

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Song() {
    }

    public Song(String artist, String id, String name, String album, List<String> genres, String picture) {
        this.artist = artist;
        this.id = id;
        this.name = name;
        this.album = album;
        this.genres = genres;
        this.picture = picture;
    }

    public Song(String artist, String id, String name) {
        this.artist = artist;
        this.id = id;
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }



}
