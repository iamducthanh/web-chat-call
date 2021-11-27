package com.webchat.webchat.pojo;

import com.webchat.webchat.constant.UsersOnline;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
//@AllArgsConstructor
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
    private boolean status;


    public UserPojo(Integer id, String username, String fullName, String email, String image, Date lastonline, boolean gender, String role, Date birthDate, String friendStatus, boolean online, String phone, String descreption, String birthdayString, boolean status) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.image = image;
        this.lastonline = lastonline;
        this.gender = gender;
        this.role = role;
        this.birthDate = birthDate;
        this.friendStatus = friendStatus;
        this.online = online;
        this.phone = phone;
        this.descreption = descreption;
        this.birthdayString = birthdayString;
        this.status = status;
    }

    public UserPojo(Integer id, String username, String fullName, String email, String image, Date lastonline, boolean gender, String role, Date birthDate, String friendStatus, boolean online, String phone, String descreption, String birthdayString) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.image = image;
        this.lastonline = lastonline;
        this.gender = gender;
        this.role = role;
        this.birthDate = birthDate;
        this.friendStatus = friendStatus;
        this.online = online;
        this.phone = phone;
        this.descreption = descreption;
        this.birthdayString = birthdayString;
    }
}
