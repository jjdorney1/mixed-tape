package com.theironyard.service;


import com.theironyard.entity.Playlist;
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

    // TODO: method to query the API to get data for the user
    public User getUserData() {
        // query
        // return the collected data
        return new User();
    }

    // TODO: method to save the date to the server for accessing
    public void saveUserData(User user) {
        //userRepository.save(user);
    }



    // TODO: method to query API to get list of playlists
    public ArrayList<Playlist> getListUserPlaylists() {
        // query
        // assign collected data to List<Playlist>
        return new ArrayList<>();
    }
//
//    // TODO: method to parse playlists for tracks
//    public ArrayList<Track> getListUserPlaylistTracks(ArrayList<Playlist> playlists) {
//        // parse the playlists one by one
//        for (Playlist playlist : playlists) {
//            saveUserPlaylistTracks();

        //}
//        // parse list for songs
//        ArrayList<Track> tracks = new ArrayList<>();
//        return tracks;
//    }

    // TODO: method to iterate over List<Song> to save or something
    public void saveUserPlaylistSongs(ArrayList<Track> tracks) {
        for (Track track : tracks) {
            // ????? userRepository.save(song);
        }
    }

    // TODO: method to query the API to get data for the friend(s)
    public User getFriendData() {
        // query
        // return the collected data
        return new User();
    }

    //
    public void refreshToken (Api api){
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
        Page<LibraryTrack> libraryTrackPage;
        Track track;
        ArrayList<String> tracks = new ArrayList<>();

        try {
            // offset initializer
            int offset = 0;

            // DO: get the tracks -- WHILE: next track is not null
            // do {
                // gets a Page of LibraryTracks 50 long at an ever increasing offset
                libraryTrackPage = api.getMySavedTracks()
                        .limit(50)
                        .offset(offset)
                        .build().get();

                // for loop to add each track's ID to an ArrayList of Track
                for (int x = 1; x < 50; x++) {
                    track = libraryTrackPage.getItems().get(x).getTrack();
                    tracks.add(track.getId());
                }
                // increment offset
                offset++;

            // } while (libraryTrackPage.getNext() != null);

        } catch (IOException | WebApiException e) {
            e.printStackTrace();
        }

        return tracks;
    }

    // collect albums
    public ArrayList<String> getSavedAlbums(Api api) throws NullPointerException {
        Page<LibraryTrack> libraryTrackPage;
        Track track;
        ArrayList<String> tracks = new ArrayList<>();

        try {
            // offset initializer
            int offset = 0;

            // DO: get the tracks -- WHILE: next track is not null
            // do {
            // gets a Page of LibraryTracks 50 long at an ever increasing offset
            libraryTrackPage = api.getMySavedTracks()
                    .limit(50)
                    .offset(offset)
                    .build().get();

            // for loop to add each track's ID to an ArrayList of Track
            for (int x = 1; x < 50; x++) {
                track = libraryTrackPage.getItems().get(x).getTrack();
                tracks.add(track.getId());
            }
            // increment offset
            offset++;

            // } while (libraryTrackPage.getNext() != null);

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

    public List<LibraryTrack> getUserSavedTracks(Api api) {
        List<LibraryTrack> tracks = null;

        try {
            tracks = api.getMySavedTracks().build().get().getItems();

            //api.getMySavedTracks().build().get().getItems().get(x).getTrack()

        } catch (WebApiException | IOException e) {
            e.printStackTrace();
        }

        return tracks;
    }

    public String getTracksCurl() {
        String country = "US";
        Integer offset = 0;
        String token;
        return country;
    }

    public String getTracksCurl(String country, Integer offset, String token) {

        return "curl -X GET \"https://api.spotify.com/v1/me/tracks?market=" +
                country + "&limit=50&offset=" + offset +
                "\" -H \"Accept: application/json\" -H \"Authorization: Bearer " +
                token + "\"";
    }

}
