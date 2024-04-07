package com.webchat.webchat.service.impl;

import com.webchat.webchat.entities.Friend;
import com.webchat.webchat.entities.User;
import com.webchat.webchat.pojo.UserPojo;
import com.webchat.webchat.repository.FriendRepository;
import com.webchat.webchat.service.IFriendService;
import com.webchat.webchat.utils.SessionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService implements IFriendService {
    private final FriendRepository repo;
    private final SessionUtil sessionUtil;

    @Override
    public List<Friend> getFriendByUser(String username) {
        List<Friend> list = repo.findByUser(username, username);
        return list.isEmpty() ? null : list;
    }

    @Override
    public Mono<Friend> findFriendBy2User(String username1, String usernam2) {
        List<Friend> list = repo.findBy2User(username1, usernam2);
        return Mono.just(list.get(0));
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

    @Override
    public String deleteFriend(String userId, String friendId) {
        List<Friend> list = repo.findBy2UserId(Integer.parseInt(userId), Integer.parseInt(friendId));
        Friend friend  = list.isEmpty() ? null : list.get(0);
        repo.delete(friend);
        return "";
    }

    @Override
    public List<UserPojo> getAllFriend() {
        List<User> friends = (List<User>) sessionUtil.getObject("FRIENDS");
        List<UserPojo> friendOut = new ArrayList<>();
        if(friends != null){
            for(User user : friends){
                UserPojo userPojo = new UserPojo(
                        user.getId(),
                        user.getUsername(),
                        user.getFullname(),
                        user.getEmail(),
                        user.getImage(),
                        user.getLastonline(),
                        user.isGender(),
                        user.getRole(),
                        user.getBirthDate(),
                        "",
                        user.isOnline(),
                        user.getPhone(),
                        user.getDescription(),
                        user.getLastOnlineString()
                );
                friendOut.add(userPojo);
            }
        }
        return friendOut;
    }
}
