package com.webchat.webchat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDeleteGroupDto {
    private String username;
    private String roomId;
    private boolean connect;
}
