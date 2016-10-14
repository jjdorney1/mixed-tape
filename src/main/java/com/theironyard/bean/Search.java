package com.theironyard.bean;

/**
 * Created by jeffreydorney on 10/14/16.
 */
public class Search {

    private String uri;

    public Search(String uri) {
        this.uri = uri;
    }

    public Search() {
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
