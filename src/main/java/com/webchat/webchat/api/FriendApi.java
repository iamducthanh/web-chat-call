package com.webchat.webchat.api;

import com.webchat.webchat.entities.Friend;
import com.webchat.webchat.entities.User;
import com.webchat.webchat.pojo.UserPojo;
import com.webchat.webchat.service.IFriendService;
import com.webchat.webchat.utils.SessionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class FriendApi {
    private final IFriendService friendService;
    private final SessionUtil sessionUtil;

    @PutMapping("/api/friend/delete")
    @ResponseBody
    public String deleteFriend(String userId, String friendId){
        return friendService.deleteFriend(userId, friendId);
    }

    @GetMapping("/api/friend")
    @ResponseBody
    public List<UserPojo> getAllFriend(){
        return friendService.getAllFriend();
    }
}
