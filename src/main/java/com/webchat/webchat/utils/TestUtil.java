package com.webchat.webchat.utils;

import com.google.api.services.drive.Drive;
import com.webchat.webchat.entities.User;
import com.webchat.webchat.repository.UserRepository;
import com.webchat.webchat.service.impl.RoomDetailService;
import com.webchat.webchat.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.Inet4Address;
import java.security.InvalidKeyException;
import java.util.List;

public class TestUtil {
    @Autowired
    static RoomDetailService roomDetailService;

    @Autowired
    static UserService userService;

    @Autowired
    private Drive googleDrive;

    @Autowired
    static UserRepository userRepository;


    // public static void main(String[] args) throws InvalidKeyException, IOException, InterruptedException {
    //     List<User> list = userService.findAll();
    //     System.out.println(list.size());

    // }
}
