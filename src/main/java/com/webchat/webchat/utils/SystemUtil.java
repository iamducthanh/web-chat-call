package com.webchat.webchat.utils;

import com.webchat.webchat.constant.UsersOnline;
import com.webchat.webchat.entities.User;

import java.util.List;

public class SystemUtil {
    public static boolean isFriend(User user, List<User> friends) {
        for (User user1 : friends) {
            if (user1.getUsername().equals(user.getUsername())) {
                return true;
            }
        }
        return false;
    }
}
