package com.webchat.webchat.pojo;

import com.webchat.webchat.constant.UsersOnline;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageUserConnectPojo {
    private String roomId;
    private String fullname;
    private String image;
    private String sender;
    private String content;
    private boolean isFriend;
    private String time;

    public boolean isOnline(){
        if(UsersOnline.usersOnline.get(this.sender) != null){
            return true;
        } else {
            return false;
        }
    }
}
