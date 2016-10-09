package com.theironyard.repository;

import com.theironyard.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by davehochstrasser on 10/6/16.
 */
@Repository
public interface ArtistRepository extends JpaRepository<Artist, Integer>{
}
