package com.webchat.webchat.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webchat.webchat.constant.UsersOnline;
import com.webchat.webchat.dto.RoomDetailDto;
import com.webchat.webchat.dto.RoomGroupDetailDto;
import com.webchat.webchat.dto.UserDto;
import com.webchat.webchat.dto.UserInRoomDto;
import com.webchat.webchat.entities.Message;
import com.webchat.webchat.entities.Room;
import com.webchat.webchat.entities.RoomDetail;
import com.webchat.webchat.entities.User;
import com.webchat.webchat.pojo.RoomGroupPojo;
import com.webchat.webchat.pojo.UserDeleteGroupPojo;
import com.webchat.webchat.service.IMessageService;
import com.webchat.webchat.service.IRoomDetailService;
import com.webchat.webchat.service.IRoomService;
import com.webchat.webchat.service.IUserService;
import com.webchat.webchat.utils.SessionUtil;
import com.webchat.webchat.utils.SystemUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class RoomApi {
    @Autowired
    private IRoomDetailService roomDetailService;

    @Autowired
    private IRoomService roomService;

    @Autowired
    private IUserService userService;

    @Autowired
    private SessionUtil sessionUtil;

    @Autowired
    private IMessageService messageService;

    @Autowired
    private SystemUtil systemUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/api/room/message-direct")
    @ResponseBody
    public RoomDetailDto getRoomDetail(String roomId) {
        return roomService.getRoomDetail(roomId);
    }

    @GetMapping("/api/room/message-group")
    @ResponseBody
    public RoomGroupDetailDto getRoomGroupDetail(String roomId) {
        return roomService.getRoomGroupDetail(roomId);
    }

    @PostMapping("/api/room/add-member")
    @ResponseBody
    public String addMember(String roomId, String members) throws JsonProcessingException {
        return roomService.addMember(roomId, members);
    }

    @PostMapping("/api/room/delete-member")
    @ResponseBody
    public List<UserDeleteGroupPojo> deleteMember(String roomId, String userId) {
        return roomService.deleteMember(roomId, userId);
    }

    @PostMapping("/api/room/create-room-group")
    @ResponseBody
    public RoomGroupPojo createRoomGroup(String users, String name, String image) throws JsonProcessingException {
        return roomService.createRoomGroup(users, name, image);
    }
}
