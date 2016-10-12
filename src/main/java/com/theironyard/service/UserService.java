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

            // catch exceptions
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
            // catches exceptions
        } catch (IOException | WebApiException e) {
            e.printStackTrace();
        }
        // returns the user
        return user;
    }

    public User getFriend(Api api, String friendId) {
        User friend = null;

        try {
            // gets the friend's data
            friend = api.getUser(friendId).build().get();
            // catches exceptions
        } catch (IOException | WebApiException e) {
            e.printStackTrace();
        }
        // returns the friend
        return friend;
    }

    // checks the entered friend ID to see if it's a uri or a username
    public String trimFriendId(String friendUri) {
        String friendId;

        if (friendUri.contains("spotify")) {
            // if it's the uri it will trim it
            friendId = friendUri.substring(13);
        } else {
            // if it's just the username it will set it
            friendId = friendUri;
        }
        // return the friend's id
        return friendId;
    }


    // method to gather the user's saved tracks and return their ID #'s in an arraylist of strings
    public ArrayList<String> getSavedTracks(Api api, String friendId) throws NullPointerException, InterruptedException {

        // arraylist of tracks to return
        ArrayList<String> tracks = new ArrayList<>();

        // collections of simpleplaylists
        Page<SimplePlaylist> friendPlaylists = new Page<>();
        List<SimplePlaylist> friendPlaylistList = null;

        try {
            // run api to get the playlists for the user
            friendPlaylists = api.getPlaylistsForUser(friendId).build().get();
            friendPlaylistList = friendPlaylists.getItems();
            // catches exceptios
        } catch (IOException | WebApiException e) {
            e.printStackTrace();
        }

        assert friendPlaylistList != null;

        for (SimplePlaylist friendPlaylist : friendPlaylistList) {
            String playlistId = friendPlaylist.getId();
            String playlistOwner = friendPlaylist.getOwner().getId();
            ArrayList<String> friendTracks = getPlaylistTracks(api, playlistOwner, playlistId);

            for (String friendTrack : friendTracks) {
                if (!tracks.contains(friendTrack)) {
                    tracks.add(friendTrack);
                }
            }
        }
        return tracks;
    }

    // method to gather the user's saved tracks and return their ID #'s in an arraylist of strings
    public ArrayList<String> getSavedTracks(Api api) throws NullPointerException, InterruptedException {

        // page of library tracks
        Page<LibraryTrack> libraryTrackPage;
        List<LibraryTrack> libraryTrackList;

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

                // creates list from paged items
                libraryTrackList = libraryTrackPage.getItems();

                // for-each loop to add each track's ID to an ArrayList of Track
                for (LibraryTrack libraryTrack : libraryTrackList) {
                    tracks.add(libraryTrack.getTrack().getId());
                }
                // increment offset
                offset = offset + 50;

                // REMOVED TEMPORARILY UNLESS NEEDED
                // delay between searches (in milliseconds) - to keep spotify from booting us out of process
                // Thread.sleep(10);

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

        // list of tracks to add
        ArrayList<String> tracksToAdd;

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

                // iterate over track arraylist to add them to the tracks object
                for (String trackIds : tracksToAdd) {
                    if (!tracks.contains(trackIds)) {
                        tracks.add(trackIds);
                    }
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
        int offset = 0;

        try {
            do {
                // created playlist track list to iterate over
                playlistTracksPage = api.getPlaylistTracks(userId, playlistId)
                        .limit(100)
                        .offset(offset)
                        .build().get();
                playlistTracks = playlistTracksPage.getItems();

                // iterates over the list to pull out the track id
                for (PlaylistTrack playlistTrack : playlistTracks) {
                    String trackId = playlistTrack.getTrack().getId();
                    tracks.add(trackId);
                }
                offset = offset + 100;
            } while (playlistTracksPage.getNext() != null);
            // exception catches
        } catch (IOException | WebApiException e) {
            e.printStackTrace();
        }
        // returns the list of track ids
        return tracks;
    }


    // adding missing playlist tracks to the list of users saved tracks from their library
    public ArrayList<String> getFinalNewMusicList(ArrayList<String> userSavedTracks, ArrayList<String> userPlaylistTracks) {

        // iterate over the playlist object to do comparison with what songs are already present
        for (String playlistId : userPlaylistTracks) {

            // checks against the list of user tracks that we've already obtained
            if (!userSavedTracks.contains(playlistId)) {
                userSavedTracks.add(userSavedTracks.size(), playlistId);
            }
        }
        return userSavedTracks;
    }


}
