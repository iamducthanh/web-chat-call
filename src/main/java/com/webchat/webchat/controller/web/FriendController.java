package com.webchat.webchat.controller.web;

import com.webchat.webchat.dto.FriendRequestDto;
import com.webchat.webchat.entities.Friend;
import com.webchat.webchat.entities.Notification;
import com.webchat.webchat.entities.User;
import com.webchat.webchat.pojo.FriendRequestPojo;
import com.webchat.webchat.service.IFriendService;
import com.webchat.webchat.service.INotificationService;
import com.webchat.webchat.service.IUserService;
import com.webchat.webchat.utils.SessionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class FriendController {
    private final IUserService userService;

    private final IFriendService friendService;

    private final INotificationService notificationService;

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

        } else if(friendRequest.getType().equals("CANCEL")){
            deleteFriend(Integer.parseInt(friendRequest.getSenderId()), Integer.parseInt(friendRequest.getUserId()));
        } else if(friendRequest.getType().equals    ("AGREE")){
            agreeFriendRequest(friendRequest, Integer.parseInt(friendRequest.getSenderId()), Integer.parseInt(friendRequest.getUserId()));
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
        friendService.saveFriend(friendNew);
    }

    public void deleteFriend(Integer userId, Integer friendId){
        Friend friend = friendService.findFriendBy2UserId(userId, friendId);
        friendService.deleteFriend(friend);
    }

    public void agreeFriendRequest(FriendRequestDto friendRequest, Integer userId, Integer friendId){
        Friend friend = friendService.findFriendBy2UserId(userId, friendId);
        friend.setStatus("FRIEND");
        friend.setTime(new Date());
        friendRequest.setFullname(friend.getFriend().getFullname());
        friendRequest.setUserId(String.valueOf(friend.getFriend().getId()));
        friendRequest.setImage(friend.getFriend().getImage());
        friendRequest.setUsername(friend.getFriend().getUsername());
        friendService.saveFriend(friend);
        Notification notification = new Notification();
        notification.setUser(friend.getUser());
        notification.setStatus("ON");
        notification.setType("AGREE");
        notification.setTime(new Date());
        notification.setImage(friend.getFriend().getImageFirst());
        notification.setContent(friend.getFriend().getFullname() + " đã chấp nhận lời mời kết bạn.");
        notification.setTitle(friend.getFriend().getFullname());
        System.out.println(notification.toString());
        notificationService.saveOneNotification(notification);
    }
}
