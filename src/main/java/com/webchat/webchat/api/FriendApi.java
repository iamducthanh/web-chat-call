package com.webchat.webchat.api;

import com.webchat.webchat.entities.Friend;
import com.webchat.webchat.entities.User;
import com.webchat.webchat.pojo.UserPojo;
import com.webchat.webchat.service.IFriendService;
import com.webchat.webchat.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FriendApi {
    @Autowired
    private IFriendService friendService;
    @Autowired
    private SessionUtil sessionUtil;


    @PutMapping("/api/friend/delete")
    @ResponseBody
    public String deleteFriend(String userId, String friendId){
        System.out.println(userId);
        System.out.println(friendId);
        Friend friend = friendService.findFriendBy2UserId(Integer.parseInt(userId), Integer.parseInt(friendId));
        friendService.deleteFriend(friend);
        return "";
    }

    @GetMapping("/api/friend")
    @ResponseBody
    public List<UserPojo> getAllFriend(){
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
                        user.getBirthDayString()
                );
                friendOut.add(userPojo);
            }
        }
        return friendOut;
    }
}
