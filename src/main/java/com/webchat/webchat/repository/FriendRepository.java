package com.webchat.webchat.repository;

import com.webchat.webchat.entities.Friend;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface FriendRepository extends R2dbcRepository<Friend, Integer> {
    @Query("select o from Friend o where o.user.username = ?1 or o.friend.username = ?2 order by o.time desc")
    Flux<Friend> findByUser(String username1, String username2);

    @Query("select o from Friend o where (o.user.username = ?1 and o.friend.username = ?2) or (o.user.username = ?2 and o.friend.username = ?1)")
    Flux<Friend> findByUserAndUser(String username1, String username2);

    @Query("select o from Friend o where o.user.id = ?1 and o.friend.id = ?2")
    Flux<Friend> findByUserId(Integer userId, Integer friendId);
}
