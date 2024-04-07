package com.webchat.webchat.api;

import com.webchat.webchat.pojo.UserPojo;
import com.webchat.webchat.service.IFriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FriendApi {
    private final IFriendService friendService;

    @PutMapping("/api/friend/delete")
    public String deleteFriend(String userId, String friendId){
        return friendService.deleteFriend(userId, friendId);
    }

    @GetMapping("/api/friend")
    public List<UserPojo> getAllFriend(){
        return friendService.getAllFriend();
    }
}
