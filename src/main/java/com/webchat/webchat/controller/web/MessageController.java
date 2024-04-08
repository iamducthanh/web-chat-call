package com.webchat.webchat.controller.web;

import com.webchat.webchat.dto.MessageUserDto;
import com.webchat.webchat.entities.Room;
import com.webchat.webchat.service.impl.MessageService;
import com.webchat.webchat.service.impl.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MessageController {
    private final RoomService roomService;
    private final MessageService mesageService;

    @PostMapping("/message/create-room")
    @ResponseBody
    public MessageUserDto checkMessage(String userId) throws Exception {
        return mesageService.checkMessage(userId);
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
