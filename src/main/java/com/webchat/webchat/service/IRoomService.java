package com.webchat.webchat.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.webchat.webchat.dto.RoomDetailDto;
import com.webchat.webchat.dto.RoomGroupDetailDto;
import com.webchat.webchat.entities.Room;
import com.webchat.webchat.pojo.RoomGroupPojo;
import com.webchat.webchat.pojo.UserDeleteGroupPojo;

import java.util.List;

public interface IRoomService {
    void saveRoom(Room room);
    void deleteRoom(Room room);
    Room findRoomById(String id);
    RoomDetailDto getRoomDetail(String roomId);
    RoomGroupDetailDto getRoomGroupDetail(String roomId);
    String addMember(String roomId, String members) throws JsonProcessingException;
    List<UserDeleteGroupPojo> deleteMember(String roomId, String userId);
    RoomGroupPojo createRoomGroup(String users, String name, String image) throws Exception;
}
