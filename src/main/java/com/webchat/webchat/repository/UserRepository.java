package com.webchat.webchat.repository;

import com.webchat.webchat.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select o from User o where o.username = ?1")
    List<User> findUserByUsername(String username);
}
