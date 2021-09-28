package com.webchat.webchat.dto;

import com.webchat.webchat.constant.UsersOnline;
import com.webchat.webchat.entities.RoomDetail;
import com.webchat.webchat.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageUserDto {
    private String name;
    private String username;
    private String messageLast;
    private int countMess;
    private int status;
    private String time;
    private String roomCode;
    private boolean active;
    private Date timeDate;
    private boolean isFriend;
    private String image;

    public boolean isOnline(){
        if(UsersOnline.usersOnline.get(this.username) != null){
            return true;
        } else {
            return false;
        }
    }
}
