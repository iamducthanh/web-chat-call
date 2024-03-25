package com.webchat.webchat.controller.acount;

import com.webchat.webchat.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class SignInController {
    private final UserService userService;

    @GetMapping("/signin")
    public String signinPage(Model model) {
        return userService.signinPage(model);
    }

    @GetMapping("/signin_unlock")
    public String signinUnlockPage(Model model) {
        return userService.signinUnlockPage(model);
    }

    @GetMapping("/logoutSuccessful")
    public String signoutSuccess() {
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
    ) {
        return userService.checkSignin(username, password, remember, unlock, ip);
    }

}
