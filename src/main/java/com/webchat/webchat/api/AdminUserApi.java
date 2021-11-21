package com.webchat.webchat.api;

import com.webchat.webchat.dto.CountMessageDto;
import com.webchat.webchat.dto.UserDto;
import com.webchat.webchat.entities.User;
import com.webchat.webchat.pojo.UserPojo;
import com.webchat.webchat.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author GMO_ThanhND
 * @version 1.0
 * @since 11/21/2021
 */
@RestController
@RequestMapping("/api/admin")
public class AdminUserApi {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserPojo>> getUsers(@RequestParam("username") String username){
        System.out.println(username);
        List<User> users = userService.findAll();
        List<UserPojo> userPojos = new ArrayList<>();
        users.forEach((user) -> {
            if(!user.getUsername().equals(username)){
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
                        user.getLastOnlineString(),
                        user.isOnline(),
                        user.getPhone(),
                        user.getDescription(),
                        user.getBirthDayString()
                ));
            }
        });
        return ResponseEntity.ok().body(userPojos);
    }
}
