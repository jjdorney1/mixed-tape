package com.theironyard.service;

import com.theironyard.entity.Playlist;
import com.theironyard.entity.Song;
import com.theironyard.entity.User;
import com.theironyard.repository.PlaylistRepository;
import com.theironyard.repository.SongRepository;
import com.theironyard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by davehochstrasser on 10/3/16.
 */
@Service
public class UserService {

    @Autowired
    PlaylistRepository playlistRepository;

    @Autowired
    SongRepository songRepository;

    @Autowired
    UserRepository userRepository;

    // TODO: method to query the API to get data for the user
public User getUserData(){
    // query
    // return the collected data
    return new User();
}

    // TODO: method to save the date to the server for accessing
public void saveUserData(User user){
    userRepository.save(user);
}

    // TODO: method to query API to get list of songs
public ArrayList<Song> getListUserSongs(){
    // query
    // assign collected data to List<Song>
    ArrayList<Song> songs;
    return songs;
}

    // TODO: method to query API to get list of playlists
public ArrayList<Playlist> getListUserPlaylists(){
    // query
    // assign collected data to List<Playlist>
    ArrayList<Playlist> playlists = new ArrayList<>();
    return playlists;
}

    // TODO: method to parse playlists for songs
public ArrayList<Song> getListUserPlaylistSongs(ArrayList<Playlist> playlists){
    // parse the playlists one by one
    for (Playlist playlist : playlists) {
        saveUserPlaylistSongs();

    }
    // parse list for songs
    ArrayList<Song> songs = new ArrayList<>();
    return songs;
}

    // TODO: method to iterate over List<Song> to save or something
public void saveUserPlaylistSongs(ArrayList<Song> songs){
    for(Song song : songs) {
        // ????? userRepository.save(song);
    }
}

    // TODO: method to query the API to get data for the friend(s)
public User getFriendData(){
    // query
    // return the collected data
    return new User();
}

    //
public void saveFriendData(User friend){

}

}
