package com.webchat.webchat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendRequestDto {
    private String senderId;
    private String userId;
    private String fullname;
    private String image;
    private String type;
    private String time;
    private String username;

    public String getTimeString(){
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return fm.format(new Date());
    }
}
