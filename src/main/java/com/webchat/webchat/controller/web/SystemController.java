package com.webchat.webchat.controller.web;

import com.webchat.webchat.constant.UsersOnline;
import com.webchat.webchat.entities.User;
import com.webchat.webchat.pojo.MessageUserConnectPojo;
import com.webchat.webchat.pojo.MessageUserRealtime;
import com.webchat.webchat.pojo.RoomGroupPojo;
import com.webchat.webchat.pojo.UserOnline;
import com.webchat.webchat.service.impl.UserService;
import com.webchat.webchat.utils.SessionUtil;
import com.webchat.webchat.utils.SystemUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class SystemController {

    @MessageMapping("/system.adduser")
    @SendTo("/topic/system.adduser")
    public UserOnline addUser(@Payload UserOnline userOnline, SimpMessageHeaderAccessor headerAccessor) {
        System.out.println("da online " + userOnline.getUsername());
        UsersOnline.usersOnline.put(userOnline.getUsername(), userOnline.getUsername());
        headerAccessor.getSessionAttributes().put("userOnline", userOnline.getUsername());
        return userOnline;
    }

    @MessageMapping("/system.onmessage/{username}")
    @SendTo("/topic/system.onmessage/{username}")
    public MessageUserRealtime onMessage(@Payload MessageUserRealtime messageUserRealtime) {
        System.out.println(messageUserRealtime.toString());
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        String strDate = dateFormat.format(new Date());
        messageUserRealtime.setTime(strDate);
        return messageUserRealtime;
    }

    @MessageMapping("/system/{username}")
    @SendTo("/topic/system/{username}")
    public MessageUserConnectPojo onMessage(@Payload MessageUserConnectPojo messageUserConnectPojo) {
//        List<User> friends = (List<User>) sessionUtil.getObject("FRIENDS");
//        User sender = userService.findByUsername(messageUserConnectPojo.getSender());
        messageUserConnectPojo.setFriend(true);
        Date now = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        messageUserConnectPojo.setTime(dateFormat.format(now));
        System.out.println(messageUserConnectPojo.toString());
        return messageUserConnectPojo;
    }

    @MessageMapping("/system.onroomGroup/{username}")
    @SendTo("/topic/system.onroomGroup/{username}")
    public RoomGroupPojo onRoomGroup(@Payload RoomGroupPojo roomGroupPojo, @PathVariable("username") String username) {
        System.out.println("user: " + username);
        return roomGroupPojo;
    }
}
