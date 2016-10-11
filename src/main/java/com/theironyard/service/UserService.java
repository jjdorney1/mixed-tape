package com.theironyard.service;

import com.theironyard.repository.*;
import com.wrapper.spotify.Api;
import com.wrapper.spotify.exceptions.WebApiException;
import com.wrapper.spotify.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by davehochstrasser on 10/3/16.
 */

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


    // setups up the refresh token for the api
    public void refreshToken(Api api) {
        try {
            RefreshAccessTokenCredentials refresh = api.refreshAccessToken().build().get();
            api.setAccessToken(refresh.getAccessToken());

        } catch (IOException | WebApiException e) {
            e.printStackTrace();
        }
    }


    // gets the user from the api
    public User getUser(Api api) {
        User user = null;

        try {
            // gets the user's data
            user = api.getMe().build().get();
        } catch (IOException | WebApiException e) {
            e.printStackTrace();
        }

        // returns the user
        return user;
    }


    // method to gather the user's saved tracks and return their ID #'s in an arraylist of strings
    public ArrayList<String> getSavedTracks(Api api) throws NullPointerException, InterruptedException {

        // page of library tracks
        Page<LibraryTrack> libraryTrackPage;

        // size of the user's library
        int librarySize;

        // track object to hold the track
        Track track;

        // arraylist of tracks to return
        ArrayList<String> tracks = new ArrayList<>();

        try {
            // offset initializer
            int offset = 0;

            // DO: go gather the tracks from the user
            do {

                // gets a Page of LibraryTracks 50 long at an ever increasing offset
                libraryTrackPage = api.getMySavedTracks()
                        .limit(50)
                        .offset(offset)
                        .build().get();

                librarySize = libraryTrackPage.getTotal();

                // for loop to add each track's ID to an ArrayList of Track
                for (int x = 1; x < librarySize; x++) {
                    track = libraryTrackPage.getItems().get(x).getTrack();
                    tracks.add(track.getId());
                }
                // increment offset
                offset++;

                // delay between searches (in milliseconds) - to keep spotify from booting us out of process
                Thread.sleep(10);

                // WHILE: next page of tracks is not null
            } while (libraryTrackPage.getNext() != null);

            // catch exceptions
        } catch (IOException | WebApiException e) {
            e.printStackTrace();
        }

        // returns the list of track ids
        return tracks;
    }


    // gets a list of playlists from the user, and then ultimately returns all the tracks
    public ArrayList<String> getSavedPlaylists(Api api, String userId) {

        // sets default playlists amount to zero
        Page<SimplePlaylist> pageTotalPlaylists = new Page<>();

        // playlist total count
        int totalPlaylists = 0;

        // list of playlists from the userId
        List<SimplePlaylist> playlists;

        // track id object
        String trackId;

        // list of track ids from the playlist
        ArrayList<String> playlistTrackIds;

        // list of tracks to add
        ArrayList<String> tracksToAdd = new ArrayList<>();

        // list of tracks to return
        ArrayList<String> tracks = new ArrayList<>();

        // collects page of total playlists from api
        try {
            pageTotalPlaylists = api.getPlaylistsForUser(userId).build().get();
        } catch (IOException | WebApiException e) {
            e.printStackTrace();
        }

        // counts the total playlists
        totalPlaylists = pageTotalPlaylists.getTotal();

        // checks if the user has playlists
        if (totalPlaylists != 0) {

            // gathers all the user's playlists
            playlists = pageTotalPlaylists.getItems();

            // for each to iterate over the playlists and get uid and playlist id
            for (SimplePlaylist playlist : playlists) {

                // gets the playlist id from the playlist
                String playlistId = playlist.getId();

                // gets the user id from the playlist
                String uid = playlist.getOwner().getId();

                // sets the tracks to the searched playlist
                tracksToAdd = getPlaylistTracks(api, uid, playlistId);

                for (int x = 0; x < tracksToAdd.size(); x++) {
                    trackId = tracksToAdd.get(x);
                    tracks.add(tracks.size(), trackId);
                }
            }
        }
        // return the collected tracks from the playlists
        return tracks;
    }


    // get a list of tracks from the api for a given user and playlistid
    public ArrayList<String> getPlaylistTracks(Api api, String userId, String playlistId) {
        // creates object to hold the track ids
        ArrayList<String> tracks = new ArrayList<>();

        Page<PlaylistTrack> playlistTracksPage;
        List<PlaylistTrack> playlistTracks;

        try {
            // created playlisttrack list to iterate over
            playlistTracksPage = api.getPlaylistTracks(userId, playlistId).build().get();

            if (playlistTracksPage.getTotal() == 0) {
                playlistTracksPage = api.getPlaylistTracks("spotify", playlistId).build().get();
            }

            playlistTracks = playlistTracksPage.getItems();
            int playlistSize = playlistTracks.size();

            // iterates over the list to pull out the track id
            for (int x = 0; x < playlistSize; x++) {
                String trackId = playlistTracks.get(x).getTrack().getId();
                playlistTracks.get(x).getTrack().getUri();
                tracks.add(x, trackId);
            }
            // exception catches
        } catch (IOException | WebApiException e) {
            e.printStackTrace();
        }
        // returns the list of track ids
        return tracks;
    }


    // building this out -- work in progress
    public ArrayList<String> getFinalNewMusicList(Api api, String userId, ArrayList<String> userSavedTracks, ArrayList<String> userPlaylistTracks) {
        ArrayList<String> tracks = new ArrayList<>();

        int playlistTrackCounter = userPlaylistTracks.size();
        String playlistTrackToCheck;

        for (int x = 0; x < playlistTrackCounter; x++) {
            playlistTrackToCheck = userPlaylistTracks.get(x);


        }


        return tracks;
    }



}
