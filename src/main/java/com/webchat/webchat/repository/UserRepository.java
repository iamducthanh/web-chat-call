package com.webchat.webchat.repository;

import com.webchat.webchat.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select o from User o where o.username = ?1")
    List<User> findUserByUsername(String username);

    @Query("select o from User o where o.id = ?1")
    List<User> findUserById(Integer id);

    @Query("select o from User o where o.id in ?1")
    List<User> findUserByGroupUserId(List<Integer> userIds);

    @Query("select o from User o where o.username = ?1 and o.password = ?2")
    List<User> findUserByUsernameAndPassword(String username, String password);

    @Query("select o from User o where (o.username like ?1 or o.email like ?2) and o.username <> ?3")
    List<User> findUserByKeyword(String username, String email, String user, Pageable pageable);

    @Query("select o from User o where o.email = ?1")
    List<User> findUserByEmail(String email);

    @Query("select o.user from RoomDetail o where o.user.id <> ?1 and o.room.id = ?2")
    List<User> findUserInRoom(Integer userId, String roomId);
}
