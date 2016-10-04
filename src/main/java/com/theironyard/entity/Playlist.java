package com.theironyard.entity;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.List;

/**
 * Created by davehochstrasser on 10/3/16.
 */
@Entity
public class Playlist {

    @Id
    @NotBlank
    private String id; // from spotify

    private String title;
    private Boolean status;
    private String picture;
    private Integer length;

    @ManyToMany
    @JoinColumn(name = "playlist_id")
    private List<Song> songs;

    public Playlist() {
    }

    public Playlist(String id, String title, Boolean status, List<Song> songs, String picture, Integer length) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.songs = songs;
        this.picture = picture;
        this.length = length;
    }

    public Playlist(String id,  String title, String picture) {
        this.id = id;
        this.title = title;
        this.picture = picture;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
