package com.webchat.webchat.api;

import com.webchat.webchat.dto.CountMessageDto;
import com.webchat.webchat.dto.UserDto;
import com.webchat.webchat.entities.User;
import com.webchat.webchat.pojo.UserOnline;
import com.webchat.webchat.pojo.UserPojo;
import com.webchat.webchat.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private SimpMessagingTemplate template;

    @GetMapping("/users")
    public ResponseEntity<List<UserPojo>> getUsers(@RequestParam("username") String username){
        System.out.println(username);
        List<User> users = userService.findAll();
        List<UserPojo> userPojos = new ArrayList<>();
        users.forEach((user) -> {
            System.out.println(user.toString());
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
                        user.getBirthDayString(),
                        user.isStatus()
                ));
            }
        });
        return ResponseEntity.ok().body(userPojos);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserPojo> getUser(@PathVariable("id") String id){
        User user = userService.findById(Integer.parseInt(id));
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
                user.getLastOnlineString(),
                user.isOnline(),
                user.getPhone(),
                user.getDescription(),
                user.getBirthDayString(),
                user.isStatus()
        );
        return ResponseEntity.ok().body(userPojo);
    }

    @PostMapping("/user/save")
    public void saveUser(@RequestBody UserPojo userPojo){
        User user = userService.findById(userPojo.getId());
        user.setRole(userPojo.getRole());
        user.setStatus(userPojo.isStatus());

        if(!userPojo.isStatus()){
            template.convertAndSend("/topic/system.adduser", new UserOnline(userPojo.getUsername(), "BLOCK"));
        }
//        User user = new User(
//                userPojo.getId(),
//                userPojo.getRole(),
//                userPojo.isStatus()
//        );
        userService.saveUser(user);
        System.out.println(user .toString());
    }

}
