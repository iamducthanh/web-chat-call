package com.webchat.webchat.controller.acount;

import com.webchat.webchat.entities.Location;
import com.webchat.webchat.entities.User;
import com.webchat.webchat.service.ILocationService;
import com.webchat.webchat.service.impl.UserService;
import com.webchat.webchat.utils.CookieUtil;
import com.webchat.webchat.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SignInController {
    @Autowired
    private CookieUtil cookieUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest req;

    @Autowired
    private ILocationService locationService;

    @GetMapping("/signin")
    public String signinPage(Model model){
        String status = req.getParameter("status");
        if(status != null && status.equals("login_false")){
            req.setAttribute("errormessage","Thông tin tài khoản hoặc mật khẩu không chính xác!");
        }
        if(status != null && status.equals("logout")){
            cookieUtil.add("username","username", 0);
            cookieUtil.add("remember","on", 0);
            cookieUtil.add("password","password", 0);
        } else {
            String username = cookieUtil.get("username");
            String password = cookieUtil.get("password");
            String remember = cookieUtil.get("remember");
            User user = userService.findByUsername(username);
            BCryptPasswordEncoder pass = new BCryptPasswordEncoder();
            if(user != null){
                if(pass.matches(password, user.getPassword())){
                    if(remember != null && remember.equals("on")){
                        model.addAttribute("username", username);
                        model.addAttribute("password", password);
                        model.addAttribute("rememberOn", remember);
                    }
                }
            }
        }
        return "views/acount/signin";
    }

    @GetMapping("/signin_unlock")
    public String signinUnlockPage(Model model){
        cookieUtil.add("username","", 0);
        cookieUtil.add("remember","on", 0);
        cookieUtil.add("password","", 0);
        model.addAttribute("unlock", "on");
        req.setAttribute("errormessage","Chúng tôi nhận thấy phiên đăng nhập bất thường trên thiết bị của bạn, vui lòng đăng nhập lại!");
        return "views/acount/signin";
    }

    @GetMapping("/logoutSuccessful")
    public String signoutSuccess(){
        return "views/acount/signin";
    }

    @PostMapping("/signin")
    @ResponseBody
    public String checkSignin(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("remember") String remember,
            @RequestParam("unlock") String unlock,
            @RequestParam("ip") String ip
            ){
        System.out.println(unlock);
        System.out.println(ip);

        if(remember.equals("true")){
            int hour = 60 * 60 * 24 * 10;
            cookieUtil.add("username",username, hour);
            cookieUtil.add("remember","on", 0);
            cookieUtil.add("password",password, hour);
        } else {
            cookieUtil.add("username",username, 0);
            cookieUtil.add("remember","on", 0);
            cookieUtil.add("password",password, 0);
        }
        String message = "";
        User user = userService.findByUsername(username);

        if(user == null){
            message = "Thông tin tài khoản hoặc mật khẩu không chính xác!";
        } else {
            BCryptPasswordEncoder pass = new BCryptPasswordEncoder();
            if(!pass.matches(password, user.getPassword())){
                message = "Thông tin tài khoản hoặc mật khẩu không chính xác!";
            } else if(!user.isStatus()){
                message = "Tài khoản của bạn đã bị khóa!!";
            } else {
                if(unlock.equals("on")){
                    Location location = locationService.findByUserAndIp(user.getUsername(), ip);
                    if(location != null){
                        location.setStatus("ON");
                        locationService.saveLocation(location);
                    }
                }
            }
        }
        return message;
    }

}
