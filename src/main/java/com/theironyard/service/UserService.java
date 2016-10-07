package com.theironyard.service;

import com.sun.org.apache.xerces.internal.impl.dv.xs.BooleanDV;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.theironyard.entity.*;
import com.theironyard.repository.*;
import com.wrapper.spotify.Api;
import com.wrapper.spotify.exceptions.WebApiException;
import com.wrapper.spotify.methods.TrackSearchRequest;
import com.wrapper.spotify.models.*;
import com.wrapper.spotify.models.Track;
import com.wrapper.spotify.models.User;
import org.apache.commons.collections.keyvalue.AbstractKeyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    InviteeRepository inviteeRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    TrackRepository trackRepository;

    @Autowired
    PlaylistRepository playlistRepository;

    @Autowired
    ArtistRepository artistRepository;

    @Autowired
    UserRepository userRepository;



    public void refreshToken(Api api){
        try {
            RefreshAccessTokenCredentials refresh = api.refreshAccessToken().build().get();
            api.setAccessToken(refresh.getAccessToken());

        } catch (IOException | WebApiException e) {
            e.printStackTrace();
        }
    }

    public User getUser(Api api) {
        User user = null;

        try {
            user = api.getMe().build().get();
        } catch (IOException | WebApiException e) {
            e.printStackTrace();
        }

        return user;
    }

    public ArrayList<String> getSavedTracks(Api api) throws NullPointerException {
        Page<LibraryTrack> libraryTrackPage = null;
        List<LibraryTrack> libraryTracks = null;
        LibraryTrack libraryTrack;
        Track track;
        ArrayList<Track> trackArrayList = null;
        ArrayList<String> tracks = new ArrayList<>();


        try {
            // offset initializer
            int offset = 0;

            // DO: get the tracks -- WHILE: next track is not null
            do {
                // gets a Page of LibraryTracks 50 long at an ever increasing offset
                libraryTrackPage = api.getMySavedTracks()
                        .limit(50)
                        .offset(offset)
                        .build().get();

                // for loop to add each track's ID to an ArrayList of Track
                for(int x = 1; x < 50; x++){
                    track = libraryTrackPage.getItems().get(x).getTrack();
                    tracks.add(track.getId());
                }
                // increment offset
                    offset++;

            }while(libraryTrackPage.getNext() != null);

        } catch (IOException | WebApiException e) {
            e.printStackTrace();
        }

        return tracks;
    }

    public Track getSavedTrack(Api api) {
        Track track = null;

        try {
            track = api.getTrack("6QttDorIxNUehXGiqtiq1P").build().get();
        } catch (IOException | WebApiException e) {
            e.printStackTrace();
        }

        return track;
    }



    public List<LibraryTrack> getUserSavedTracks(Api api){
        List<LibraryTrack> tracks = null;

            try {
                tracks = api.getMySavedTracks().build().get().getItems();

                //api.getMySavedTracks().build().get().getItems().get(x).getTrack()

            } catch ( WebApiException | IOException e) {
                e.printStackTrace();
            }

        return tracks;
    }

    public String getTracksCurl(){
        String country = "US";
        Integer offset = 0;
        String token;
        return country;
    }

    public String getTracksCurl(String country, Integer offset, String token){

        return "curl -X GET \"https://api.spotify.com/v1/me/tracks?market=" +
                country + "&limit=50&offset=" + offset +
                "\" -H \"Accept: application/json\" -H \"Authorization: Bearer " +
                token + "\"";
    }



}
