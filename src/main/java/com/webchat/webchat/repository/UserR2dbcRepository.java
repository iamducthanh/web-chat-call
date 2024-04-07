//package com.webchat.webchat.repository;
//
//import com.webchat.webchat.entities.User;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.r2dbc.repository.R2dbcRepository;
//import reactor.core.publisher.Flux;
//
//public interface UserR2dbcRepository extends R2dbcRepository<User, Integer> {
//    @Query("select o from User o where (o.username like ?1 or o.email like ?2) and o.username <> ?3")
//    Flux<User> findUserByKeyword(String username, String email, String user, Pageable pageable);
//}
