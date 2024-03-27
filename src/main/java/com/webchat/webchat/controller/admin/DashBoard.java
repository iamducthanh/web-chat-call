//package com.webchat.webchat.controller.admin;
//
//import com.webchat.webchat.constant.UsersOnline;
//import com.webchat.webchat.entities.User;
////import com.webchat.webchat.service.ICountMessageService;
//import com.webchat.webchat.service.ICountMessageService;
//import com.webchat.webchat.service.IUserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import java.util.List;
//
//@Controller
//public class DashBoard {
//    @Autowired
//    private IUserService userService;
//
//    @Autowired
//    ICountMessageService countMessageService;
//
//
//    @GetMapping("/admin/trang-chu")
//    public String dashboard(Model model){
//        List<User> users = userService.findAll();
//        model.addAttribute("countUser", users.size());
//
//        for(int i=1;i<13;i++){
//            model.addAttribute("thang" + i, countMessageService.count(String.valueOf(i), "2021"));
//        }
//        model.addAttribute("useronline", UsersOnline.usersOnline.size());
//        model.addAttribute("useroffline", users.size() - UsersOnline.usersOnline.size());
//        return "views/admin/dashboard";
//    }
//}
