
package com.theironyard.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * Created by davehochstrasser on 10/3/16.
 */

@Entity
@Table(name = "users")
public class User {


    @Id
    private String id; // from spotify

    private String name;
    private String picture;
    private Integer follower;
    private String country;
    private Date birthday;
    private Boolean active;

    @ManyToMany
    @Fetch(FetchMode.SELECT)
    private List<Track> tracks;

    @ManyToMany
    @Fetch(FetchMode.SELECT)
    private List<Playlist>playlists;


    public User() {}

    public User(String name, String id, String picture, Integer followers, String country, Date birthday, Boolean active) {
        this.name = name;
        this.id = id;
        this.picture = picture;
        this.follower = followers;
        this.country = country;
        this.birthday = birthday;
        this.active = active;
    }

    public User(String name, String id, String picture) {
        this.name = name;
        this.id = id;
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Integer getFollower() {
        return follower;
    }

    public void setFollower(Integer follower) {
        this.follower = follower;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }
}
