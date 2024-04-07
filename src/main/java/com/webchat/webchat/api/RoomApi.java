package com.webchat.webchat.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.webchat.webchat.dto.RoomDetailDto;
import com.webchat.webchat.dto.RoomGroupDetailDto;
import com.webchat.webchat.pojo.RoomGroupPojo;
import com.webchat.webchat.pojo.UserDeleteGroupPojo;
import com.webchat.webchat.service.IRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RoomApi {
    private final IRoomService roomService;

    @GetMapping("/api/room/message-direct")
    public RoomDetailDto getRoomDetail(String roomId) {
        return roomService.getRoomDetail(roomId);
    }

    @GetMapping("/api/room/message-group")
    public RoomGroupDetailDto getRoomGroupDetail(String roomId) {
        return roomService.getRoomGroupDetail(roomId);
    }

    @PostMapping("/api/room/add-member")
    public String addMember(String roomId, String members) throws JsonProcessingException {
        return roomService.addMember(roomId, members);
    }

    @PostMapping("/api/room/delete-member")
    public List<UserDeleteGroupPojo> deleteMember(String roomId, String userId) {
        return roomService.deleteMember(roomId, userId);
    }

    @PostMapping("/api/room/create-room-group")
    public RoomGroupPojo createRoomGroup(String users, String name, String image) throws JsonProcessingException {
        return roomService.createRoomGroup(users, name, image);
    }
}
