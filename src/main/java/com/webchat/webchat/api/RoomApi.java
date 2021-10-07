package com.webchat.webchat.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webchat.webchat.dto.RoomDetailDto;
import com.webchat.webchat.dto.RoomGroupDetailDto;
import com.webchat.webchat.dto.UserDto;
import com.webchat.webchat.dto.UserInRoomDto;
import com.webchat.webchat.entities.Message;
import com.webchat.webchat.entities.Room;
import com.webchat.webchat.entities.RoomDetail;
import com.webchat.webchat.entities.User;
import com.webchat.webchat.service.IMessageService;
import com.webchat.webchat.service.IRoomDetailService;
import com.webchat.webchat.service.IRoomService;
import com.webchat.webchat.service.IUserService;
import com.webchat.webchat.utils.SessionUtil;
import com.webchat.webchat.utils.SystemUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class RoomApi {
    @Autowired
    private IRoomDetailService roomDetailService;

    @Autowired
    private IRoomService roomService;

    @Autowired
    private IUserService userService;

    @Autowired
    private SessionUtil sessionUtil;

    @Autowired
    private IMessageService messageService;

    @Autowired
    private SystemUtil systemUtil;

    @GetMapping("/api/room/message-direct")
    @ResponseBody
    public RoomDetailDto getRoomDetail(String roomId){
        User user = (User) sessionUtil.getObject("USER");
        List<User> friends = (List<User>) sessionUtil.getObject("FRIENDS");
        System.out.println(roomId);
        RoomDetailDto roomDetailDto = new RoomDetailDto();
        List<RoomDetail> roomDetails = roomDetailService.findByRoomId(roomId);
        System.out.println(roomDetails.size());
        String username = user.getUsername();
        User user1 = roomDetails.get(0).getUser();
        User user2 = roomDetails.get(1).getUser();

        // check first message
        Room room = roomDetails.get(0).getRoom();
        String first = "";
        if(!room.getUsername().isEmpty()){
            first = "onFirst";
        }
        UserDto userDto = null;
        UserInRoomDto userInRoomDto = null;
        if(user1.getUsername().equals(username) || user2.getUsername().equals(username)){
            Message message = messageService.findMessageLast(roomId);
            String statusMessage = ""; // check trạng thái tin nhắn
            boolean isFriend = false; // check bạn bè hay không
            if(message != null && message.getUser().getUsername().equals(username)){
                if(message.getStatus().equals("SEND")){
                    System.out.println("đã gửi");
                    statusMessage = "SEND";
                } else if(message.getStatus().equals("READ")){
                    System.out.println(message.getStatus());
                    System.out.println("Đã xem");
                    statusMessage = "READ";
                }
            } else {
             //   if(message.getStatus().equals("SEND")){ // sửa thành tin nhắn đã xem
                    messageService.setStatusMessage(roomId, message.getUser().getUsername(), "SEND");
            //    }
                System.out.println("là của người khác gửi");
            }
            System.out.println(statusMessage);
            if(user1.getUsername().equals(username)){
                isFriend = systemUtil.isFriend(user2, friends);
                System.out.println("là bạn bè " + isFriend);
                userDto = new UserDto(username, user1.getFullname(), user1.getImage());
                userInRoomDto = new UserInRoomDto(
                        user2.getUsername(),
                        user2.getFullname(),
                        user2.getImage(),
                        user2.isOnline(),
                        first,
                        statusMessage,
                        isFriend,
                        user2.getEmail(),
                        user2.getPhone(),
                        user2.getBirthDayString(),
                        user2.isGender(),
                        user2.getDescription(),
                        user2.getLastOnlineString(),
                        user2.getId()
                );
            } else {
                System.out.println("là bạn bè "+ isFriend);
                isFriend = systemUtil.isFriend(user1, friends);
                userDto = new UserDto(username, user2.getFullname(), user2.getImage());
                userInRoomDto = new UserInRoomDto(
                        user1.getUsername(),
                        user1.getFullname(),
                        user1.getImage(),
                        user1.isOnline(),
                        first,
                        statusMessage,
                        isFriend,
                        user1.getEmail(),
                        user1.getPhone(),
                        user1.getBirthDayString(),
                        user1.isGender(),
                        user1.getDescription(),
                        user1.getLastOnlineString(),
                        user1.getId()
                );
            }
            roomDetailDto.setRoomId(roomId);
            roomDetailDto.setUser(userDto);
            roomDetailDto.setUserInRoom(userInRoomDto);
        }
        return roomDetailDto;
    }

    @GetMapping("/api/room/message-group")
    @ResponseBody
    public RoomGroupDetailDto getRoomGroupDetail(String roomId){
        int countOnline = 0;
        Room room = roomService.findRoomById(roomId);
        User user = (User) sessionUtil.getObject("USER");
        List<User> friend = (List<User>) sessionUtil.getObject("FRIENDS");
        List<User> userInrooms = userService.findInRoom(user.getId(), roomId);
        RoomGroupDetailDto roomGroupDetail = new RoomGroupDetailDto();
        roomGroupDetail.setName(room.getName());
        roomGroupDetail.setUser(new UserDto(user.getUsername(), user.getFullname(), user.getImage()));
        roomGroupDetail.setRoomId(roomId);
        List<UserInRoomDto> userInRoomDtos = new ArrayList<>();
        for(User user1 : userInrooms){
            if(user1.isOnline()){
                countOnline ++;
            }
            UserInRoomDto userInRoomDto = new UserInRoomDto(
                    user1.getUsername(),
                    user1.getFullname(),
                    user1.getImage(),
                    user1.isOnline(),
                    "",
                    "",
                    systemUtil.isFriend(user1, friend),
                    user1.getEmail(),
                    user1.getPhone(),
                    user1.getBirthDayString(),
                    user1.isGender(),
                    user1.getDescription(),
                    user1.getLastOnlineString(),
                    user1.getId()
            );
            userInRoomDtos.add(userInRoomDto);
        }
        roomGroupDetail.setUserInRooms(userInRoomDtos);
        roomGroupDetail.setCountOnline(countOnline);
        return roomGroupDetail;
    }

    @PostMapping("/api/room/add-member")
    @ResponseBody
    public String addMember(String roomId, String members) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> memberNews = new ArrayList<>();
        memberNews = Arrays.asList(objectMapper.readValue(members, String[].class));
        Room room = new Room();
        room.setId(roomId);
        List<RoomDetail> roomDetails = new ArrayList<>();
        for(String userId : memberNews){
            User user = new User();
            user.setId(Integer.parseInt(userId));
            RoomDetail roomDetail = new RoomDetail();
            roomDetail.setUser(user);
            roomDetail.setRoom(room);
            roomDetails.add(roomDetail);
        }
        roomDetailService.saveRoomDetail(roomDetails);
        return "";
    }

    @PostMapping("/api/room/delete-member")
    @ResponseBody
    public String deleteMember(String roomId, String userId) {
        System.out.println(roomId);
        System.out.println(userId);
        RoomDetail roomDetail = roomDetailService.findRoomDetailByUserAndRoom(Integer.parseInt(userId), roomId);
        roomDetailService.deleteRoomDetail(roomDetail);
        return "";
    }
}
