package com.webchat.webchat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
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
}
