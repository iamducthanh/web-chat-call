package com.webchat.webchat.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webchat.webchat.constant.UsersOnline;
import com.webchat.webchat.dto.RoomDetailDto;
import com.webchat.webchat.dto.RoomGroupDetailDto;
import com.webchat.webchat.dto.UserDto;
import com.webchat.webchat.dto.UserInRoomDto;
import com.webchat.webchat.entities.Message;
import com.webchat.webchat.entities.Room;
import com.webchat.webchat.entities.RoomDetail;
import com.webchat.webchat.entities.User;
import com.webchat.webchat.pojo.RoomGroupPojo;
import com.webchat.webchat.pojo.UserDeleteGroupPojo;
import com.webchat.webchat.repository.MessageRepository;
import com.webchat.webchat.repository.RoomDetailRepositoty;
import com.webchat.webchat.repository.RoomRepositoty;
import com.webchat.webchat.service.IMessageService;
import com.webchat.webchat.service.IRoomService;
import com.webchat.webchat.service.IUserService;
import com.webchat.webchat.utils.SessionUtil;
import com.webchat.webchat.utils.SystemUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RoomService implements IRoomService {
    private final RoomRepositoty roomRepositoty;
    private final SystemUtil systemUtil;
    private final SessionUtil sessionUtil;
    private final RoomDetailRepositoty roomDetailRepo;
    private final MessageRepository messageRepo;
    private final IMessageService messageService;
    private final IUserService userService;
    private final ObjectMapper objectMapper;


    @Override
    public void saveRoom(Room room) {
        roomRepositoty.save(room);
    }

    @Override
    public void deleteRoom(Room room) {
        roomRepositoty.delete(room);
    }

    @Override
    public Room findRoomById(String id) {
        List<Room> list = roomRepositoty.findRoomById(id);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public RoomDetailDto getRoomDetail(String roomId) {
        User user = (User) sessionUtil.getObject("USER");
        List<User> friends = (List<User>) sessionUtil.getObject("FRIENDS");
        RoomDetailDto roomDetailDto = new RoomDetailDto();
        List<RoomDetail> roomDetails = roomDetailRepo.findRoomDetailByRoomId(roomId);
        String username = user.getUsername();
        User user1 = roomDetails.get(0).getUser();
        User user2 = roomDetails.get(1).getUser();

        // check first message
        Room room = roomDetails.get(0).getRoom();
        String first = "";
        if (!room.getUsername().isEmpty()) {
            first = "onFirst";
        }
        UserDto userDto;
        UserInRoomDto userInRoomDto;
        if (user1.getUsername().equals(username) || user2.getUsername().equals(username)) {
            Pageable pageable = PageRequest.of(0,1);
            List<Message> list = messageRepo.findMessageLast(roomId, pageable);
            Message message = list.isEmpty() ? null : list.get(0);
            String statusMessage = ""; // check trạng thái tin nhắn
            boolean isFriend = false; // check bạn bè hay không
            if (message != null && message.getUser().getUsername().equals(username)) {
                if (message.getStatus().equals("SEND")) {
                    System.out.println("đã gửi");
                    statusMessage = "SEND";
                } else if (message.getStatus().equals("READ")) {
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
            if (user1.getUsername().equals(username)) {
                isFriend = systemUtil.isFriend(user2, friends);
                System.out.println("là bạn bè " + isFriend);
                userDto = new UserDto(username, user1.getFullname(), user1.getImage(), user1.getId());
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
                System.out.println("là bạn bè " + isFriend);
                isFriend = systemUtil.isFriend(user1, friends);
                userDto = new UserDto(username, user2.getFullname(), user2.getImage(), user2.getId());
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

    @Override
    public RoomGroupDetailDto getRoomGroupDetail(String roomId) {
        int countOnline = 0;
        List<Room> list = roomRepositoty.findRoomById(roomId);
        Room room = list.isEmpty() ? null : list.get(0);
        User user = (User) sessionUtil.getObject("USER");
        List<User> friend = (List<User>) sessionUtil.getObject("FRIENDS");
        List<User> userInrooms = userService.findInRoom(user.getId(), roomId);
        RoomGroupDetailDto roomGroupDetail = new RoomGroupDetailDto();
        roomGroupDetail.setName(room.getName());
        roomGroupDetail.setUser(new UserDto(user.getUsername(), user.getFullname(), user.getImage(), user.getId()));
        roomGroupDetail.setRoomId(roomId);
        List<UserInRoomDto> userInRoomDtos = new ArrayList<>();
        for (User user1 : userInrooms) {
            if (user1.isOnline()) {
                countOnline++;
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
        roomGroupDetail.setImage(room.getImage());
        return roomGroupDetail;
    }

    @Override
    public String addMember(String roomId, String members) throws JsonProcessingException {
        List<String> memberNews;
        memberNews = Arrays.asList(objectMapper.readValue(members, String[].class));
        Room room = new Room();
        room.setId(roomId);
        List<RoomDetail> roomDetails = new ArrayList<>();
        for (String userId : memberNews) {
            User user = new User();
            user.setId(Integer.parseInt(userId));
            RoomDetail roomDetail = new RoomDetail();
            roomDetail.setUser(user);
            roomDetail.setRoom(room);
            roomDetails.add(roomDetail);
        }
        roomDetailRepo.saveAll(roomDetails);
        return "";
    }

    @Override
    public List<UserDeleteGroupPojo> deleteMember(String roomId, String userId) {
        List<UserDeleteGroupPojo> usernames = new ArrayList<>();
        List<RoomDetail> roomDetails = roomDetailRepo.findRoomDetailByRoomId(roomId);

        List<String> userConnect = UsersOnline.userConnectGroup.get(roomId);
        if (roomDetails.size() <= 3) {
            for (RoomDetail roomDetail1 : roomDetails) {
                String username = roomDetail1.getUser().getUsername();
                if (userConnect.contains(roomDetail1.getUser().getUsername())) {
                    usernames.add(new UserDeleteGroupPojo(username, true, systemUtil.isOnline(username)));
                } else {
                    usernames.add(new UserDeleteGroupPojo(username, false, systemUtil.isOnline(username)));
                }
            }
            List<Message> messages = messageService.findAllByRoom(roomId);
            messageService.deleteMessage(messages); // delete message in room
            roomDetailRepo.deleteAll(roomDetails); // delete roomDetail
            Room room = new Room();
            room.setId(roomId);
            roomRepositoty.delete(room); //delete room
        } else {
            List<RoomDetail> list = roomDetailRepo.findRoomDetailByUserAndRoom(Integer.parseInt(userId), roomId);
            RoomDetail roomDetail = list.isEmpty() ? null : list.get(0);
            roomDetailRepo.delete(roomDetail);
            String username = roomDetail.getUser().getUsername();
            if (userConnect.contains(roomDetail.getUser().getUsername())) {
                usernames.add(new UserDeleteGroupPojo(username, true, systemUtil.isOnline(username)));
            } else {
                usernames.add(new UserDeleteGroupPojo(username, false, systemUtil.isOnline(username)));
            }
        }

        return usernames;
    }

    @Override
    public RoomGroupPojo createRoomGroup(String users, String name, String image) throws JsonProcessingException {
        List<Integer> userIds = Arrays.asList(objectMapper.readValue(users, Integer[].class));
        User user = (User) sessionUtil.getObject("USER");
        String roomId = String.valueOf(UUID.randomUUID());
        Room room = new Room(roomId, image, name, "");

        List<User> userList = userService.findByGroupUserId(userIds);
        userList.add(0, user);
        roomRepositoty.save(room);
        List<RoomDetail> roomDetails = new ArrayList<>();
        for (User user1 : userList) {
            roomDetails.add(new RoomDetail(user1, room));
        }
        roomDetailRepo.saveAll(roomDetails);
        Message message = new Message();
        message.setRoom(room);
        message.setType("CREATE");
        message.setTime(new Date());
        message.setContent("Bắt đầu trò chuyện");
        message.setUser(user);
        message.setId(String.valueOf(UUID.randomUUID()));
        message.setStatus("CREATE");
        messageRepo.save(message);
        List<String> userOnlines = new ArrayList<>();
        List<String> imageGroup = new ArrayList<>();
        if (!image.isEmpty()) {
            imageGroup.add(room.getImage());
        }
        for (int i = 0; i < userList.size(); i++) {
            if (image.isEmpty()) {
                if (i < 3) {
                    imageGroup.add(userList.get(i).getImage());
                }
            }
            if (UsersOnline.usersOnline.get(userList.get(i).getUsername()) != null) {
                userOnlines.add(userList.get(i).getUsername());
            }
        }
        RoomGroupPojo roomGroupPojo = new RoomGroupPojo();
        roomGroupPojo.setUserOnline(userOnlines);
        roomGroupPojo.setNameGroup(name);
        roomGroupPojo.setImageGroup(imageGroup);
        roomGroupPojo.setTime(message.getTimeChat());
        roomGroupPojo.setRoomId(roomId);
        return roomGroupPojo;
    }
}
