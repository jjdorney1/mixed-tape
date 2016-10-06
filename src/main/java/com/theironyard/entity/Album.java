package com.theironyard.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by davehochstrasser on 10/6/16.
 */
@Entity
public class Album {

    @Id
    private String id;

    private String name;

    @OneToOne
    @JoinColumn(name = "album_id")
    private Image image;

    @OneToMany
    private List<Artist> artists;

    public Album(String id, String name, List<Artist> artists) {
        this.id = id;
        this.name = name;
        this.artists = artists;
    }

    public Album() {
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
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


}
