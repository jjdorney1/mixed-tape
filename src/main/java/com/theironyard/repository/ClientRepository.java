package com.theironyard.repository;

import com.theironyard.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by jeffreydorney on 10/18/16.
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

}
