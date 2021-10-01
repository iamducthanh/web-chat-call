package com.webchat.webchat.service.impl;

import com.webchat.webchat.entities.Friend;
import com.webchat.webchat.repository.FriendRepository;
import com.webchat.webchat.service.IFriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendService implements IFriendService {
    @Autowired
    private FriendRepository repo;

    @Override
    public List<Friend> getFriendByUser(String username) {
        List<Friend> list = repo.findByUser(username, username);
        return list.isEmpty() ? null : list;
    }

    @Override
    public Friend findFriendBy2User(String username1, String usernam2) {
        List<Friend> list = repo.findBy2User(username1, usernam2);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public Friend findFriendBy2UserId(Integer userId, Integer friendId) {
        List<Friend> list = repo.findBy2UserId(userId, friendId);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public void saveFriend(Friend friend) {
        repo.save(friend);
    }

    @Override
    public void deleteFriend(Friend friend) {
        repo.delete(friend);
    }
}
