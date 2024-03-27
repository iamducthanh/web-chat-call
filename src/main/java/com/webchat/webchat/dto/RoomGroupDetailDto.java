package com.webchat.webchat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomGroupDetailDto {
    private String roomId;
    private String name;
    private UserDto user;
    private List<UserInRoomDto> userInRooms;
    private int countOnline;
    private String image;
}
