package com.webchat.webchat.api;

import com.webchat.webchat.dto.ChangePasswordDto;
import com.webchat.webchat.dto.UserProfileUpdateDto;
import com.webchat.webchat.pojo.ErrorPojo;
import com.webchat.webchat.pojo.UserPojo;
import com.webchat.webchat.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserApi {
    private final IUserService userService;

    @PostMapping("/user/search")
    public Flux<UserPojo> getUserByKeyword(@RequestParam("keyword") String keyword) {
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
