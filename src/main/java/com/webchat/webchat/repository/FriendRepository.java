//package com.webchat.webchat.repository;
//
//import com.webchat.webchat.entities.Friend;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//import java.util.List;
//
//public interface FriendRepository extends JpaRepository<Friend, Integer> {
//    @Query("select o from Friend o where o.user.username = ?1 or o.friend.username = ?2 order by o.time desc")
//    List<Friend> findByUser(String username1, String username2);
//
//    @Query("select o from Friend o where (o.user.username = ?1 and o.friend.username = ?2) or (o.user.username = ?2 and o.friend.username = ?1)")
//    List<Friend> findBy2User(String username1, String username2);
//
//    @Query("select o from Friend o where o.user.id = ?1 and o.friend.id = ?2")
//    List<Friend> findBy2UserId(Integer userId, Integer friendId);
//}
