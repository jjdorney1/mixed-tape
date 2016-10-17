package com.theironyard.repository;

import com.theironyard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by davehochstrasser on 10/3/16.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    List<User>findByName(String name);

}
