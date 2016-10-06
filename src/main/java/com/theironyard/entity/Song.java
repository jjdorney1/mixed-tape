package com.theironyard.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import java.util.List;

/**
 * Created by davehochstrasser on 10/3/16.
 */
@Entity
public class Song {

    @Id
    @NotBlank
    private String id; // spotify id

    private String artist;
    private String song;
    private String album;
    private String picture;

    @ManyToMany
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "song_id")
    private List<User> users;

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Song() {
    }

    public Song(String artist, String id,  String song, String album, String picture) {
        this.artist = artist;
        this.id = id;
        this.song = song;
        this.album = album;
        this.picture = picture;
    }

    public Song(String artist, String id,  String song) {
        this.artist = artist;
        this.id = id;
        this.song = song;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }


}
