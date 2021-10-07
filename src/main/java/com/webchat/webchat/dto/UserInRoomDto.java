package com.webchat.webchat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInRoomDto {
    private String username;
    private String fullname;
    private String image;
    private boolean isOnline;
    private String first;
    private String statusMessage;
    private boolean isFriend;
    private String email;
    private String phone;
    private String birthday;
    private boolean gender;
    private String description;
    private String lastOnline;
    private Integer id;
}
