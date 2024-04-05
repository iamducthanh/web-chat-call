package com.webchat.webchat.service.impl;

import com.webchat.webchat.entities.Friend;
import com.webchat.webchat.entities.User;
import com.webchat.webchat.pojo.UserPojo;
import com.webchat.webchat.repository.FriendRepository;
import com.webchat.webchat.service.IFriendService;
import com.webchat.webchat.utils.SessionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService implements IFriendService {
    private final FriendRepository repo;
    private final SessionUtil sessionUtil;

//    @Override
//    public List<Friend> getFriendByUser(String username) {
//        List<Friend> list = repo.findByUser(username, username);
//        return list.isEmpty() ? null : list;
//    }

    @Override
    public Mono<Friend> findFriendBy2User(String username1, String usernam2) {
        Flux<Friend> list = repo.findByUserAndUser(username1, usernam2);
        return list.next();
    }

    @Override
    public Mono<Friend> findFriendBy2UserId(Integer userId, Integer friendId) {
        Flux<Friend> list = repo.findByUserId(userId, friendId);
        return list.next();
    }
//
//    @Override
//    public void saveFriend(Friend friend) {
//        repo.save(friend);
//    }
//
//    @Override
//    public void deleteFriend(Friend friend) {
//        repo.delete(friend);
//    }
//
    @Override
    public Mono<String> deleteFriend(String userId, String friendId) {
        repo.findByUserId(Integer.parseInt(userId), Integer.parseInt(friendId)).next().subscribe(fr -> {
            repo.delete(fr);
        });
        return Mono.just("");
    }

    @Override
    public Flux<UserPojo> getAllFriend() {
        List<User> friends = (List<User>) sessionUtil.getObject("FRIENDS");
        Flux<UserPojo> friendOut = Flux.empty();
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
                friendOut.concatWith(Mono.just(userPojo));
            }
        }
        return friendOut;
    }
}
