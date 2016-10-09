package com.theironyard.entity;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.List;

/**
 * Created by davehochstrasser on 10/3/16.
 */
@Entity
public class Track {

    @Id
    @NotBlank
    private String id; // spotify id

    @OneToMany
    private List<Artist>artists;


    private String name;

    @ManyToOne
    private Album album;

    public Track() {
    }

    public Track(String id, List<Artist> artists, String name, Album album) {
        this.id = id;
        this.artists = artists;
        this.name = name;
        this.album = album;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    //    @ManyToMany
//    @Fetch(FetchMode.SELECT)
//    @JoinColumn(name = "song_id")
//    private List<User>users;




}
