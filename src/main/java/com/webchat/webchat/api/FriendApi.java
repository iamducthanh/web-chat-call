package com.webchat.webchat.api;

import com.webchat.webchat.entities.Friend;
import com.webchat.webchat.service.IFriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FriendApi {
    @Autowired
    private IFriendService friendService;

    @PutMapping("/api/friend/delete")
    @ResponseBody
    public String deleteFriend(String userId, String friendId){
        System.out.println(userId);
        System.out.println(friendId);
        Friend friend = friendService.findFriendBy2UserId(Integer.parseInt(userId), Integer.parseInt(friendId));
        friendService.deleteFriend(friend);
        return "";
    }
}
