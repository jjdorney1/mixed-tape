package com.theironyard.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by davehochstrasser on 10/3/16.
 */
@Entity
public class Playlist {

    @Id
    private Integer id; // our id

    private String playlistId; // from spotify

    private String name;
    private Boolean status;
    private List<Song>songs;
    private String picture;
    private Integer length;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Song song;

    public Playlist() {
    }

    public Playlist(Integer id, String playlistId, String name, Boolean status, List<Song> songs, String picture, Integer length) {
        this.id = id;
        this.playlistId = playlistId;
        this.name = name;
        this.status = status;
        this.songs = songs;
        this.picture = picture;
        this.length = length;
    }

    public Playlist(Integer id, String playlistId, String name, String picture) {
        this.id = id;
        this.playlistId = playlistId;
        this.name = name;
        this.picture = picture;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }
}
