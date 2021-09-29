package com.webchat.webchat.pojo;

import com.webchat.webchat.constant.UsersOnline;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPojo {
    private Integer id;
    private String username;
    private String fullName;
    private String email;
    private String image;
    private Date lastonline;
    private boolean gender;
    private String role;
    private Date birthDate;
    private String friendStatus;
    private boolean online;
    private String phone;
    private String descreption;
    private String birthdayString;

}
