package com.webchat.webchat.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoomGroupPojo {
    private List<String> userOnline;
    private String nameGroup;
    private List<String> imageGroup;
    private String time;
    private String roomId;
    private String user;
}
