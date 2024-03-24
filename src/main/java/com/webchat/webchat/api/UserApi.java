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

@Controller
public class UserApi {
    @Autowired
    private IUserService userService;

    @Autowired
    private SessionUtil sessionUtil;

    @Autowired
    private IFriendService friendService;

    @PostMapping("/user/search")
    @ResponseBody
    public List<UserPojo> getUserByKeyword(@RequestParam("keyword") String keyword) {
        List<UserPojo> userPojos = new ArrayList<>();
        User myUser = (User) sessionUtil.getObject("USER");
        if (!(keyword.trim().length() == 0)) {
            List<User> users = userService.findByKeyword(keyword, keyword);
            if (users != null) {
                for (User user : users) {
                    String statusFriend = "ON"; // bật gửi lời mời kết bạn
                    Friend friend = friendService.findFriendBy2User(myUser.getUsername(), user.getUsername());
                    if (friend != null && friend.getStatus().equals("WAIT") && friend.getUser().getUsername().equals(myUser.getUsername())) {
                        statusFriend = "WAIT"; // bật hủy lời mời kết bạn
                    } else if (friend != null && friend.getStatus().equals("WAIT") && friend.getUser().getUsername().equals(user.getUsername())) {
                        statusFriend = "AGREE"; // bật nút đồng ý kết bạn
                    } else if (friend != null) {
                        statusFriend = "OFF"; // tắt
                    }
                    userPojos.add(new UserPojo(
                            user.getId(),
                            user.getUsername(),
                            user.getFullname(),
                            user.getEmail(),
                            user.getImage(),
                            user.getLastonline(),
                            user.isGender(),
                            user.getRole(),
                            user.getBirthDate(),
                            statusFriend,
                            UsersOnline.usersOnline.get(user.getUsername()) != null ? true : false,
                            user.getPhone(),
                            user.getDescription(),
                            user.getBirthDayString()
                    ));
                }
            }
        }
        return userPojos;

    }

    @PutMapping("/user/update/image")
    @ResponseBody
    public String updateUser(@RequestParam("image") String image) {
        return userService.updateUser(image);
    }

    @PutMapping("/user/update/profile")
    @ResponseBody
    public List<ErrorPojo> updateProfile(@Validated UserProfileUpdateDto userProfileUpdateDto, BindingResult result) {
        return userService.updateProfile(userProfileUpdateDto, result);
    }

    @PutMapping("/user/change_password")
    @ResponseBody
    public List<ErrorPojo> changePassword(@Validated ChangePasswordDto changePasswordDto, BindingResult result) {
        return userService.changePassword(changePasswordDto, result);
    }

    @GetMapping("/user/user-online")
    @ResponseBody
    public List<String> getUserOnlineInRoom(String roomId) {
        return userService.getUserOnlineInRoom(roomId);

    }

    @GetMapping("/user/get-by-username")
    @ResponseBody
    public UserPojo getUserByUsername(String username) {
        User user = userService.findByUsername(username);
        UserPojo userPojo = new UserPojo(
                user.getId(),
                user.getUsername(),
                user.getFullname(),
                user.getEmail(),
                user.getImage(),
                user.getLastonline(),
                user.isGender(),
                user.getRole(),
                user.getBirthDate(),
                "",
                user.isOnline(),
                user.getPhone(),
                user.getDescription(),
                user.getBirthDayString()
        );

        return userPojo;

    }
}
