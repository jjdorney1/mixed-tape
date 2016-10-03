package com.theironyard.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by davehochstrasser on 10/3/16.
 */
@Entity
public class User {

    @Id
    private Integer id; // our id

    private String userId; // from spotify

    private String name;
    private String picture;
    private Integer followers;
    private String country;
    private Date birthday;
    private Boolean active;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Song song;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Playlist playlist;


    public User() {}

    public User(String name, Integer id, String picture, Integer followers, String country, Date birthday, Boolean active) {
        this.name = name;
        this.id = id;
        this.picture = picture;
        this.followers = followers;
        this.country = country;
        this.birthday = birthday;
        this.active = active;
    }

    public User(String name, Integer id, String picture) {
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Integer getFollowers() {
        return followers;
    }

    public void setFollowers(Integer followers) {
        this.followers = followers;
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



}
