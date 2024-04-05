package com.webchat.webchat.service;

import com.webchat.webchat.entities.Friend;
import com.webchat.webchat.pojo.UserPojo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IFriendService {
  //  Flux<Friend> getFriendByUser(String username);
    Mono<Friend> findFriendBy2User(String username1, String usernam2);
    Mono<Friend> findFriendBy2UserId(Integer userId, Integer friendId);
 //   void saveFriend(Friend friend);
  //  void deleteFriend(Friend friend);
    Mono<String> deleteFriend(String userId, String friendId);
    Flux<UserPojo> getAllFriend();
}
