package com.theironyard.repository;

import com.theironyard.entity.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by davehochstrasser on 10/3/16.
 */
@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Integer> {
}
