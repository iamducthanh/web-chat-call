package com.webchat.webchat.repository;

import com.webchat.webchat.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Integer> {
    @Query("select o from Location o where o.user.username = ?1")
    public List<Location> findByUser(String username);

    @Query("select o from Location o where o.user.username = ?1 and o.ip = ?2")
    public List<Location> findByUserAndIp(String username, String ip);
}
