package com.webchat.webchat.intercepter;

import com.webchat.webchat.constant.PropertiesConstant;
import com.webchat.webchat.entities.*;
import com.webchat.webchat.pojo.FriendRequestPojo;
import com.webchat.webchat.pojo.MessageUser;
import com.webchat.webchat.pojo.NotificationPojo;
import com.webchat.webchat.service.*;
import com.webchat.webchat.utils.SessionUtil;
import com.webchat.webchat.utils.SystemUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataIntercepter implements HandlerInterceptor {
    @Autowired
    private IRoomDetailService roomDetailService;
    @Autowired
    private IUserService userService;
    @Autowired
    private SystemUtil systemUtil;
    @Autowired
    private IMessageService messageService;
    @Autowired
    private IFriendService friendService;
    @Autowired
    private SessionUtil sessionUtil;
    @Autowired
    private INotificationService notificationService;
    @Autowired
    private ILocationService locationService;

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        User user = (User) sessionUtil.getObject("USER");
        System.out.println("run");
        req.setAttribute("user", user);
        if(user.getRole().equals("ROLE_ADMIN")){
            req.setAttribute("isAdmin", "on");
        }
        getUserOnline(req, user);
        getFriendUser(req, user);
        getNotification(req, user);
        getLocation(req, user);
        return true;
    }

    public void getUserOnline(HttpServletRequest req, User user) {
        req.setAttribute("userOnline", user.getUsername());
        req.setAttribute("userId", user.getId());
    }

    public void getMessageUser(HttpServletRequest req, User user, List<User> friends) {
        int countMessage = 0;
        List<MessageUser> messageUsers = new ArrayList<>();
        List<RoomDetail> roomDetails = roomDetailService.findByUser(user.getId());
        String name = "";
        if (roomDetails != null) {
            for (RoomDetail roomDetail : roomDetails) {
                boolean isFriend = false; // check có phải bạn bè không
                List<User> userInRoom = userService.findInRoom(user.getId(), roomDetail.getRoom().getId());
                if (userInRoom.size() == 1) {
                    name = userInRoom.get(0).getFullname();
                    isFriend = systemUtil.isFriend(userInRoom.get(0), friends); // check bạn bè

                } else {
                    name = roomDetail.getRoom().getName();
                }
                Message messageLast = messageService.findMessageLast(roomDetail.getRoom().getId());
                int status = 0;
                String time = "";
                int countMess = 0;
                if (messageLast != null) {
                    time = messageLast.getTimeChat();
                    countMess = messageService.countMessageSend(roomDetail.getRoom().getId(), user.getUsername() ,userInRoom.get(0).getUsername());
                    if (!messageLast.getUser().getUsername().equals(user.getUsername())) {
                        if (countMess != 0) {
                            countMessage++;
                            status = 1;
                        }
                    }
                    if (user.getUsername().equals(messageLast.getUser().getUsername())) {
                        messageLast.setContent("Bạn: " + messageLast.getContent());
                    }
                } else {
                    messageLast = new Message();
                    messageLast.setContent("Bắt đầu trò chuyện");
                }
                boolean active = false;
                String usernameCheck = roomDetail.getRoom().getUsername();
                if (usernameCheck.equals(user.getUsername()) || usernameCheck.equals("")) {
                    active = true;
                }
                messageUsers.add(new MessageUser(roomDetail, name, userInRoom, messageLast.getContent(), countMess, status, time, roomDetail.getRoom().getId(), active, messageLast.getTime(), isFriend));
                if(messageUsers != null){
                    messageUsers.sort((o1, o2) -> {
                        if(o2.getTimeDate() != null && o1.getTimeDate() != null){
                            return o2.getTimeDate().compareTo(o1.getTimeDate());
                        } else {
                            return 0;
                        }
                    });
                }
            }
        }
        req.setAttribute("messageUsers", messageUsers);
        req.setAttribute("countMessage", countMessage);
    }

    public void getFriendUser(HttpServletRequest req, User user) {
        List<User> friends = new ArrayList<>();
        List<Friend> listFriend = friendService.getFriendByUser(user.getUsername());
        List<FriendRequestPojo> friendRequestPojos = new ArrayList<>();
        List<Integer> idUserList = new ArrayList<>();
        if (listFriend != null) {
            for (Friend friend : listFriend) {
                if (friend.getStatus().equals("FRIEND")) {
                    if (friend.getUser().getUsername().equals(user.getUsername())) {
                        friends.add(friend.getFriend());
                        idUserList.add(friend.getFriend().getId());
                    } else {
                        friends.add(friend.getUser());
                        idUserList.add(friend.getUser().getId());
                    }
                } else if (friend.getStatus().equals("WAIT") && friend.getFriend().getUsername().equals(user.getUsername())) {
                    friendRequestPojos.add(new FriendRequestPojo(friend.getUser(), friend.getTime()));
                }
            }
            sessionUtil.addObject("FRIENDS", friends);
        }
        req.setAttribute("friend", friends);
        req.setAttribute("friendRequests", friendRequestPojos);
        getMessageUser(req, user, friends);
    }

    public void getNotification(HttpServletRequest req, User user){
        List<Notification> notifications = notificationService.findByUser(user.getUsername());
        List<NotificationPojo> notificationPojos = new ArrayList<>();
        int count = 0;
        if(notifications != null){
            for(Notification notification : notifications){
                if(notification.getStatus().equals("ON")){
                    count++;
                }
                notificationPojos.add(new NotificationPojo(
                        notification.getId(),
                        notification.getTitle(),
                        notification.getImage(),
                        notification.getContent(),
                        notification.getTimeString(),
                        notification.getStatus()
                ));
            }
        }
        req.setAttribute("notifications", notificationPojos);
        req.setAttribute("countNotification", count);

    }

    public void getLocation(HttpServletRequest req, User user){
        List<Location> locations = locationService.findByUser(user.getUsername());
        req.setAttribute("locations", locations);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
