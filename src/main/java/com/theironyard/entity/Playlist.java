package com.theironyard.entity;

import java.util.List;

/**
 * Created by davehochstrasser on 10/3/16.
 */
public class Playlist {

    private String id;
    private String name;
    private Boolean status;
    private List<Song>songs;
    private String picture;
    private Integer length;

    public Playlist() {
    }

    public Playlist(String id, String name, Boolean status, List<Song> songs, String picture, Integer length) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.songs = songs;
        this.picture = picture;
        this.length = length;
    }

    public Playlist(String id, String name, String picture) {
        this.id = id;
        this.name = name;
        this.picture = picture;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }
}
