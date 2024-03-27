//package com.webchat.webchat.controller.web;
//
//import com.webchat.webchat.entities.Location;
//import com.webchat.webchat.repository.LocationRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import java.util.List;
//
//@Controller
//public class TestController {
//    @Autowired
//    LocationRepository repo;
//
//    @GetMapping("/test")
//    @ResponseBody
//    public String test(){
//        List<Location> list = repo.filter("o.user.username = ");
//        System.out.println(list.size());
//        return  "";
//    }
//}
