package com.webchat.webchat.controller.web;

import com.webchat.webchat.dto.FriendRequestDto;
import com.webchat.webchat.entities.Friend;
import com.webchat.webchat.entities.User;
import com.webchat.webchat.service.IFriendService;
import com.webchat.webchat.service.IUserService;
import com.webchat.webchat.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.Date;

@Controller
public class FriendController {
    @Autowired
    private IUserService userService;

    @Autowired
    private IFriendService friendService;

    @MessageMapping("/system/friend/{id}")
    @SendTo("/topic/system/friend/{id}")
    public FriendRequestDto addUser(@Payload FriendRequestDto friendRequest, SimpMessageHeaderAccessor headerAccessor) {
        System.out.println(friendRequest.toString());
        User user = userService.findById(Integer.parseInt(friendRequest.getSenderId()));
        if(friendRequest.getType().equals("REQUEST")){
            addFriendRequest(user, friendRequest.getUserId());
            friendRequest.setFullname(user.getFullname());
            friendRequest.setImage(user.getImage());
            friendRequest.setTime(friendRequest.getTimeString());
        }
        return friendRequest;
    }

    public void addFriendRequest(User user, String friendId){
        User friend = new User();
        friend.setId(Integer.parseInt(friendId));
        Friend friendNew = new Friend();
        friendNew.setUser(user);
        friendNew.setFriend(friend);
        friendNew.setStatus("WAIT");
        friendNew.setTime(new Date());
//        friendService.saveFriend(friendNew);
    }
}
