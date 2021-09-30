package com.webchat.webchat.pojo;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationPojo {
    private Integer id;
    private String fullname;
    private String image;
    private String time;
    private String status;
}
