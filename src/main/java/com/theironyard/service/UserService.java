package com.theironyard.service;

import com.theironyard.repository.PlaylistRepository;
import com.theironyard.repository.SongRepository;
import com.theironyard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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




}
