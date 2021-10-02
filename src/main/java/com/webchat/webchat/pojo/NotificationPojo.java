package com.webchat.webchat.pojo;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationPojo {
    private Integer id;
    private String title;
    private String image;
    private String content;
    private String time;
    private String status;
}
