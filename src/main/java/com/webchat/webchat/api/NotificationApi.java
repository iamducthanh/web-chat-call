package com.webchat.webchat.api;

import com.webchat.webchat.entities.Notification;
import com.webchat.webchat.entities.User;
import com.webchat.webchat.service.INotificationService;
import com.webchat.webchat.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class NotificationApi {
    @Autowired
    private INotificationService notificationService;
    @Autowired
    private SessionUtil sessionUtil;

    @PutMapping("api/notification/set-status")
    @ResponseBody
    public String setStatus(){
        User user = (User) sessionUtil.getObject("USER");
        List<Notification> notifications = notificationService.findByUserAndStatus(user.getUsername(), "ON");
        if(notifications != null){
            for(Notification notification : notifications){
                notification.setStatus("OFF");
            }
            notificationService.updateNotification(notifications);
        }
        return "";
    }
}
