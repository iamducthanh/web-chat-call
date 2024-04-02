package com.webchat.webchat.repository;

import com.webchat.webchat.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface UserRepository extends R2dbcRepository<User, Integer> {
    @Query("select o from User o where o.username = ?1")
    Mono<User> findUserByUsername(String username);

    @Query("select o from User o where o.id = ?1")
    Mono<User> findUserById(Integer id);

    @Query("select o from User o where o.id in ?1")
    Flux<User> findUserByGroupUserId(List<Integer> userIds);

    @Query("select o from User o where (o.username like ?1 or o.email like ?2) and o.username <> ?3")
    Flux<User> findUserByKeyword(String username, String email, String user, Pageable pageable);

    @Query("select o from User o where o.email = ?1")
    Mono<User> findUserByEmail(String email);

    @Query("select o.user from RoomDetail o where o.user.id <> ?1 and o.room.id = ?2")
    Flux<User> findUserInRoom(Integer userId, String roomId);
}
