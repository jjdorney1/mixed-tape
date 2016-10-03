package com.theironyard.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.List;

/**
 * Created by davehochstrasser on 10/3/16.
 */
@Entity
public class Song {

    @Id
    private Integer id; // our id

    private String songId; // spotify id

    private String artist;
    private String name;
    private String album;
    private List<String>genres;
    private String picture;

    @ManyToOne
    private User user;


    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Song() {
    }

    public Song(String artist, Integer id, String songId, String name, String album, List<String> genres, String picture) {
        this.artist = artist;
        this.id = id;
        this.songId = songId;
        this.name = name;
        this.album = album;
        this.genres = genres;
        this.picture = picture;
    }

    public Song(String artist, Integer id, String songId, String name) {
        this.artist = artist;
        this.id = id;
        this.songId = songId;
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
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

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
