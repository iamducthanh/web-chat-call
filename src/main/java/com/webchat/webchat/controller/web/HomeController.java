package com.webchat.webchat.controller.web;

import com.google.api.services.drive.Drive;
import com.webchat.webchat.service.impl.RoomDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Controller
public class HomeController {

    @Autowired
    HttpServletRequest req;

    @Autowired
    private RoomDetailService roomDetailService;

    @GetMapping(value = "/")
    public String beginPage() {
        return "redirect:/trang-chu";
    }

    @GetMapping(value = "/trang-chu")
    public String homePage() {
        return "layout/weblayout";
    }

    @GetMapping(value = "/call")
    public String callvideo() {
        return "views/message/videocall";
    }



//    @RequestMapping(value = "/message_empty", method = RequestMethod.GET)
//    public String messageEmptyPage() {
//        return "views/message/message-empty";
//    }

    public static void main(String[] args) throws ParseException {
//        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date last = fm.parse("2021-09-29 16:17:20");
//        Date now= new Date();
//        System.out.println(last.toString());
//        System.out.println(now.toString());
//        long diff = now.getTime() - last.getTime();
//        System.out.println(now.getTime());
//        System.out.println(last.getTime());
//        System.out.println(diff);
//        long diffSeconds = diff / 1000 % 60;
//        long diffMinutes = diff / (60 * 1000) % 60;
//        long diffHours = diff / (60 * 60 * 1000) % 24;
//        long diffDays = diff / (24 * 60 * 60 * 1000);
//
//        System.out.print(diffDays + " days, ");
//        System.out.print(diffHours + " hours, ");
//        System.out.print(diffMinutes + " minutes, ");
//        System.out.print(diffSeconds + " seconds.");
    }
}
