package com.example.messagingstompwebsocket.repository;

import com.example.messagingstompwebsocket.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<User,Long> {
    @Query("SELECT u FROM User u WHERE u.email = :username ")
    User findByEmail(String username);

    //@Query("SELECT u FROM User u WHERE u.email = :username or u.mobile= :username")
    //Optional<User> findUser();
}
