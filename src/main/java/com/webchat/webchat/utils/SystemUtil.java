package com.webchat.webchat.utils;

import com.webchat.webchat.constant.UsersOnline;
import com.webchat.webchat.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.List;

@Service
public class SystemUtil {
    @Autowired
    HttpServletRequest request;

    public boolean isFriend(User user, List<User> friends) {
        if(friends != null){
            for (User user1 : friends) {
                if (user1.getUsername().equals(user.getUsername())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isOnline(String username){
        if(UsersOnline.usersOnline.get(username) != null){
            return true;
        } else {
            return false;
        }
    }

    public String getIp() throws UnknownHostException {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("HTTP_X_FORWARDED");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("HTTP_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("HTTP_FORWARDED");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("HTTP_VIA");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("REMOTE_ADDR");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getRemoteAddr();
        }
//        if(ip.equals("0:0:0:0:0:0:0:1")){
//            ip = Inet4Address.getLocalHost().getHostAddress();
//        }
        return ip;
    }
}
