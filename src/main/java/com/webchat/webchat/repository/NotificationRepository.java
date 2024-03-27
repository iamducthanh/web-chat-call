//package com.webchat.webchat.repository;
//
//import com.webchat.webchat.entities.Notification;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//import java.util.List;
//
//public interface NotificationRepository extends JpaRepository<Notification, Integer> {
//    @Query("select o from Notification o where o.user.username = ?1 order by o.time desc")
//    List<Notification> findByUser(String username);
//
//    @Query("select o from Notification o where o.user.username = ?1 and o.status = ?2")
//    List<Notification> findByUserAndStatus(String username, String status);
//}
