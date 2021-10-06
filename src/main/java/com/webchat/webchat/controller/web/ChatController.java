package com.webchat.webchat.controller.web;

import com.webchat.webchat.constant.AttackFile;
import com.webchat.webchat.constant.UsersOnline;
import com.webchat.webchat.pojo.ChatMessagePojo;
import com.webchat.webchat.pojo.UserConnectPojo;
import com.webchat.webchat.utils.SystemUtil;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class ChatController {

    @MessageMapping("/chat.sendMessage/{room}")
    @SendTo("/topic/{room}")
    public ChatMessagePojo sendMessage(@Payload ChatMessagePojo chatMessagePojo) {
        Date now = new Date();
        chatMessagePojo.setTimeChat(now);
        UserConnectPojo userConnectPojo = UsersOnline.userConnectPojo.get(chatMessagePojo.getRoom());
        if(userConnectPojo != null){
            if (userConnectPojo.getUser1() != null && userConnectPojo.getUser2() != null) {
                chatMessagePojo.setStatusMessage("Đã xem");
            } else {
                chatMessagePojo.setStatusMessage("Đã gửi");
            }
        } else {
            chatMessagePojo.setStatusMessage("Đã gửi");
        }
        return chatMessagePojo;
    }

    @MessageMapping("/chat.addUser/{room}")
    @SendTo("/topic/{room}")
    public ChatMessagePojo addUser(@Payload ChatMessagePojo chatMessagePojo, SimpMessageHeaderAccessor headerAccessor) {
        UserConnectPojo userConnectPojo = UsersOnline.userConnectPojo.get(chatMessagePojo.getRoom());
        if (String.valueOf(chatMessagePojo.getType()).equals("JOINGROUP")) { // thêm vào connect group
            List<String> userGroup = UsersOnline.userConnectGroup.get(chatMessagePojo.getRoom());
            if(userGroup == null){
                userGroup = new ArrayList<>();
                userGroup.add(chatMessagePojo.getSender());
                UsersOnline.userConnectGroup.put(chatMessagePojo.getRoom(), userGroup);
            } else {
                userGroup.add(chatMessagePojo.getSender());
            }
            headerAccessor.getSessionAttributes().put("isGroup", true);
        } else {
            headerAccessor.getSessionAttributes().put("isGroup", false);

            // thêm vào connect chat direct
            if (userConnectPojo == null) {
                UsersOnline.userConnectPojo.put(chatMessagePojo.getRoom(), new UserConnectPojo(chatMessagePojo.getRoom(), chatMessagePojo.getSender(), null));
                System.out.println(UsersOnline.userConnectPojo.get(chatMessagePojo.getRoom()).toString());
            } else {
                if (userConnectPojo.getUser2() == null && userConnectPojo.getUser1() != null) {
                    if (!userConnectPojo.getUser1().equals(chatMessagePojo.getSender())) {
                        userConnectPojo.setUser2(chatMessagePojo.getSender());
                        System.out.println(userConnectPojo.toString());
                    }
                } else if (userConnectPojo.getUser2() != null && userConnectPojo.getUser1() == null) {
                    if (!userConnectPojo.getUser2().equals(chatMessagePojo.getSender())) {
                        userConnectPojo.setUser1(chatMessagePojo.getSender());
                        System.out.println(userConnectPojo.toString());
                    }
                }
            }
        }
        headerAccessor.getSessionAttributes().put("username", chatMessagePojo.getSender());
        headerAccessor.getSessionAttributes().put("room", chatMessagePojo.getRoom());
        return chatMessagePojo;
    }
}
