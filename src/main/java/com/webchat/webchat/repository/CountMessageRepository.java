//package com.webchat.webchat.repository;
//
//import com.webchat.webchat.entities.CountMessage;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.jpa.repository.query.Procedure;
//
//import java.util.List;
//
//public interface CountMessageRepository extends JpaRepository<CountMessage, Integer> {
//    @Query(value = "CALL count_message_by_month_and_year(?1,?2)", nativeQuery = true)
//    List<CountMessage> count(String month, String year);
//}
