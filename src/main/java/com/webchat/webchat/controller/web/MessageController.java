package com.webchat.webchat.controller.web;

import com.webchat.webchat.dto.MessageUserDto;
import com.webchat.webchat.entities.*;
import com.webchat.webchat.pojo.MessageUser;
import com.webchat.webchat.service.impl.MessageService;
import com.webchat.webchat.service.impl.RoomDetailService;
import com.webchat.webchat.service.impl.RoomService;
import com.webchat.webchat.service.impl.UserService;
import com.webchat.webchat.utils.SessionUtil;
import com.webchat.webchat.utils.SystemUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
public class MessageController {
    @Autowired
    private SessionUtil sessionUtil;

    @Autowired
    private RoomService roomService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoomDetailService roomDetailService;

    @PostMapping("/message/create-room")
    @ResponseBody
    public MessageUserDto checkMessage(String userId){

        MessageUserDto messageUser = new MessageUserDto();
        User user = (User) sessionUtil.getObject("USER");
        List<User> friends = (List<User>) sessionUtil.getObject("FRIENDS");
        // tạo room
        UUID roomId = UUID.randomUUID();
        UUID messageId = UUID.randomUUID();
        Room room = new Room(String.valueOf(roomId), 0, "",user.getUsername());
//        roomService.saveRoom(room);

        // tạo room detail
        User user2 = userService.findById(Integer.parseInt(userId));
        List<RoomDetail> roomDetails = new ArrayList<>();
        roomDetails.add(new RoomDetail(user,room));
        roomDetails.add(new RoomDetail(user2,room));
//        roomDetailService.saveRoomDetail(roomDetails);

        // tạo tin nhắn đầu tiên
        Message message = new Message();
        message.setRoom(room);
        message.setType("CREATE");
        message.setTime(new Date());
        message.setContent("Bắt đầu trò chuyện");
        message.setUser(user);
        message.setId(String.valueOf(messageId));
        message.setStatus("CREATE");
//        messageService.saveMessage(message);

        messageUser.setName(user2.getFullname());
        messageUser.setUsername(user2.getUsername());
        messageUser.setMessageLast("Bắt đầu trò chuyện.");
        messageUser.setCountMess(0);
        messageUser.setStatus(1);
        messageUser.setTime(message.getTimeChat());
        messageUser.setRoomCode(room.getId());
        messageUser.setFriend(SystemUtil.isFriend(user2, friends));
        messageUser.setImage(user2.getImage());
        return messageUser;
    }

    @PostMapping("/message/change-status")
    @ResponseBody
    public String changeStatus(String roomId){
        Room room = roomService.findRoomById(roomId);
        if(room != null){
            room.setUsername("");
            roomService.saveRoom(room);
        }
        return "done";
    }

}
