package com.theironyard.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by davehochstrasser on 10/6/16.
 */
@Entity
public class Artist {

    @Id
    private String id;

    private String name;

    public Artist(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Artist() {
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
