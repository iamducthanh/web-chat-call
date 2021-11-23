package com.webchat.webchat.controller.web;

import com.webchat.webchat.constant.UsersOnline;
import com.webchat.webchat.dto.UserDeleteGroupDto;
import com.webchat.webchat.entities.Location;
import com.webchat.webchat.pojo.*;
import com.webchat.webchat.service.impl.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class SystemController {
    @Autowired
    private SimpMessagingTemplate template;

    @GetMapping("/hello")
    @ResponseBody
    public String onlineE(){

        template.convertAndSend("/topic/system.adduser", new UserOnline("TEST", "FIre"));
        return "";
    }


    @Autowired
    LocationService locationService;

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

    @MessageMapping("/system.deleteRoomGroup/{username}")
    @SendTo("/topic/system.deleteRoomGroup/{username}")
    public UserDeleteGroupDto deleteRoomGroup(@Payload UserDeleteGroupDto userDeleteGroupDto) {
        return userDeleteGroupDto;
    }

    @MessageMapping("/system.location/{ip}")
    @SendTo("/topic/system.location/{ip}")
    public LocationPojo ipFunction(@Payload LocationPojo locationPojo) {
        System.out.println(locationPojo.toString());
        Location location = locationService.findByUserAndIp(locationPojo.getUsername(), locationPojo.getIp());
        if (location != null) {
            if (!location.getStatus().equals("BLOCK")) {
                location.setStatus("BLOCK");
                locationService.saveLocation(location);
            }
        }
        return locationPojo;
    }
}
