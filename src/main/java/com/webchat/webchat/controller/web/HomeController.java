package com.webchat.webchat.controller.web;

import com.google.api.services.drive.Drive;
import com.webchat.webchat.dto.LocationDto;
import com.webchat.webchat.entities.Location;
import com.webchat.webchat.entities.User;
import com.webchat.webchat.service.impl.LocationService;
import com.webchat.webchat.service.impl.RoomDetailService;
import com.webchat.webchat.utils.SessionUtil;
import com.webchat.webchat.utils.SystemUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Controller
public class HomeController {

    @Autowired
    HttpServletRequest req;

    @Autowired
    private SystemUtil systemUtil;

    @Autowired
    private SessionUtil sessionUtil;

    @Autowired
    private LocationService locationService;

    @Autowired
    private RoomDetailService roomDetailService;

    @GetMapping(value = "/")
    @CrossOrigin(origins = "http://localhost:8080")
    public String beginPage() {
        return "redirect:/trang-chu";
    }

    @GetMapping(value = "/trang-chu")
    public String homePage(Model model) throws UnknownHostException {
        String ip = systemUtil.getIp();
        model.addAttribute("ip", ip);
        return "layout/weblayout";
    }

    @PostMapping("/log-ip")
    @ResponseBody
    public String logIp(LocationDto locationDto){
        System.out.println(locationDto.toString());
        User user = (User) sessionUtil.getObject("USER");
        Location location = locationService.findByUserAndIp(user.getUsername(), locationDto.getIp());
        if(location == null){
            location = new Location(
                    user,
                    locationDto.getIp(),
                    locationDto.getLongitude(),
                    locationDto.getLatitude(),
                    locationDto.getCity() + ", " + locationDto.getCountry(),
                    new Date()
            );
            locationService.saveLocation(location);
        }
        return "add";
    }

    @GetMapping(value = "/call")
    public String callvideo() {
        return "views/message/videocall";
    }


}
