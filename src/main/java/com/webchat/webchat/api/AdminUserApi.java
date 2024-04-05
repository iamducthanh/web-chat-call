package com.webchat.webchat.api;

import com.webchat.webchat.pojo.UserPojo;
import com.webchat.webchat.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author GMO_ThanhND
 * @version 1.0
 * @since 11/21/2021
 */
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminUserApi {
    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<Flux<UserPojo>> getUsers(@RequestParam("username") String username){
        return ResponseEntity.ok().body(userService.getUsers(username));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Mono<UserPojo>> getUser(@PathVariable("id") String id){
        return ResponseEntity.ok().body(userService.getUser(id));
    }

    @PostMapping("/user/save")
    public void saveUser(@RequestBody UserPojo userPojo){
        userService.saveUser(userPojo);
    }

}
