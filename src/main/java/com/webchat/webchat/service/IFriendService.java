package com.webchat.webchat.service;

import com.webchat.webchat.entities.Friend;
import com.webchat.webchat.pojo.UserPojo;

import java.util.List;

public interface IFriendService {
    List<Friend> getFriendByUser(String username);
    Friend findFriendBy2User(String username1, String usernam2);
    Friend findFriendBy2UserId(Integer userId, Integer friendId);
    void saveFriend(Friend friend);
    void deleteFriend(Friend friend);
    String deleteFriend(String userId, String friendId);
    List<UserPojo> getAllFriend();
}
