package com.webchat.webchat.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDeleteGroupPojo {
    String username;
    boolean connect;
    boolean online;
}
