package com.theironyard.repository;

import com.theironyard.entity.Invitee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by davehochstrasser on 10/6/16.
 */
@Repository
public interface InviteeRepository extends JpaRepository<Invitee, Integer>{
}
