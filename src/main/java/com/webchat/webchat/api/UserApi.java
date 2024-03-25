package com.webchat.webchat.api;

import com.webchat.webchat.constant.UsersOnline;
import com.webchat.webchat.dto.ChangePasswordDto;
import com.webchat.webchat.dto.UserDto;
import com.webchat.webchat.dto.UserProfileUpdateDto;
import com.webchat.webchat.entities.Friend;
import com.webchat.webchat.entities.User;
import com.webchat.webchat.pojo.ErrorPojo;
import com.webchat.webchat.pojo.UserPojo;
import com.webchat.webchat.service.IFriendService;
import com.webchat.webchat.service.IUserService;
import com.webchat.webchat.service.impl.FriendService;
import com.webchat.webchat.service.impl.UserService;
import com.webchat.webchat.utils.SessionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequiredArgsConstructor
public class UserApi {
    private final IUserService userService;

    @PostMapping("/user/search")
    public List<UserPojo> getUserByKeyword(@RequestParam("keyword") String keyword) {
        return userService.getUserByKeyword(keyword);
    }

    @PutMapping("/user/update/image")
    public String updateUser(@RequestParam("image") String image) {
        return userService.updateUser(image);
    }

    @PutMapping("/user/update/profile")
    public List<ErrorPojo> updateProfile(@Validated UserProfileUpdateDto userProfileUpdateDto, BindingResult result) {
        return userService.updateProfile(userProfileUpdateDto, result);
    }

    @PutMapping("/user/change_password")
    public List<ErrorPojo> changePassword(@Validated ChangePasswordDto changePasswordDto, BindingResult result) {
        return userService.changePassword(changePasswordDto, result);
    }

    @GetMapping("/user/user-online")
    public List<String> getUserOnlineInRoom(String roomId) {
        return userService.getUserOnlineInRoom(roomId);
    }

    @GetMapping("/user/get-by-username")
    public UserPojo getUserByUsername(String username) {
        return userService.getUserByUsername(username);
    }
}
