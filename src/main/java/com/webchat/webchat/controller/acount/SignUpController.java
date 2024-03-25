package com.webchat.webchat.controller.acount;

import com.webchat.webchat.constant.CodeComfirm;
import com.webchat.webchat.entities.User;
import com.webchat.webchat.pojo.ErrorPojo;
import com.webchat.webchat.pojo.MailPojo;
import com.webchat.webchat.pojo.UserRegisterPojo;
import com.webchat.webchat.service.impl.UserService;
import com.webchat.webchat.utils.MailerUtil;
import com.webchat.webchat.utils.UploadUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.MessagingException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Controller
@RequiredArgsConstructor
public class SignUpController {
    private final UserService userService;

    private final MailerUtil mailerUtil;

    @GetMapping("/signup")
    public String signupPage(){
        return "views/acount/signup";
    }

    @PostMapping("/signup")
    @ResponseBody
    public List<ErrorPojo> signup(@Validated UserRegisterPojo user, BindingResult result) {
        return userService.signup(user, result);
    }

    @PostMapping("/signup/get-code")
    @ResponseBody
    public String getCode(String email) throws MessagingException {
        String out = "";
        User userCheck = userService.findByEmail(email);
        if(userCheck != null){
            out = "exist";
        } else {
            int ranNum = ThreadLocalRandom.current().nextInt(100000,999999);
            Integer code = CodeComfirm.codeComfirm.get(email);
            MailPojo mailPojo = new MailPojo();
            mailPojo.setFrom("iamducthanh01@gmail.com");
            mailPojo.setSubject("Đăng kí tài khoản");
            mailPojo.setBody("Mã xác nhận để đăng kí tài khoản của bạn là: " + ranNum);
            mailPojo.setTo(email);
            mailerUtil.send(mailPojo);
            out = "success";
            if (code != null){
                CodeComfirm.codeComfirm.remove(email);
            }
            CodeComfirm.codeComfirm.put(email,ranNum);
            Thread thread = new Thread(){
                @SneakyThrows
                @Override
                public void run() {
                    Thread.sleep(300000);
                    CodeComfirm.codeComfirm.remove(email);
                    System.out.println("size " + CodeComfirm.codeComfirm.size());
                }
            };
            thread.start();
            System.out.println("size " + CodeComfirm.codeComfirm.size());
        }
        return out;
    }

    // public static void main(String[] args) {
    //     BCryptPasswordEncoder pass = new BCryptPasswordEncoder();
    //     String code = "123";
    //     String a = pass.encode(code);
    //     String b = pass.encode(code);
    //     System.out.println(a);
    //     System.out.println(b);
    //     System.out.println(pass.matches(code, b));
    // }

}
