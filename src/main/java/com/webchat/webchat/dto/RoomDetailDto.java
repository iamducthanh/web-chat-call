package com.webchat.webchat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDetailDto {
    private String roomId;
    private UserDto user;
    private UserInRoomDto userInRoom;

}
