package com.webchat.webchat.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webchat.webchat.constant.AttackFile;
import com.webchat.webchat.constant.PropertiesConstant;
import com.webchat.webchat.constant.UsersOnline;
import com.webchat.webchat.dto.FileAttackDto;
import com.webchat.webchat.dto.MessagePageDto;
import com.webchat.webchat.dto.MessageUserDto;
import com.webchat.webchat.entities.*;
import com.webchat.webchat.pojo.MessagePojo;
import com.webchat.webchat.pojo.UserConnectPojo;
import com.webchat.webchat.repository.*;
import com.webchat.webchat.service.IMessageService;
import com.webchat.webchat.utils.RSA2048Util;
import com.webchat.webchat.utils.SecretKeyUtil;
import com.webchat.webchat.utils.SessionUtil;
import com.webchat.webchat.utils.SystemUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MessageService implements IMessageService {
    private final MessageRepository messageRepo;
    private final SessionUtil sessionUtil;
    private final AttachRepository attachRepo;
    private final RoomDetailRepository roomDetailRepo;
    private final UserRepository userRepo;
    private final RoomRepository roomRepo;
    private final SystemUtil systemUtil;
    private final SecretKeyUtil secretKeyUtil;
    private final RSA2048Util rsa2048Util;

    @Override
    public List<Message> findByRoom(String roomId, Pageable pageable) {
        List<Message> list = messageRepo.findByRoom(roomId, pageable);
        return list.isEmpty() ? null : list;
    }

    @Override
    public List<Message> findAllByRoom(String roomId) {
        List<Message> list = messageRepo.findAllByRoom(roomId);
        return list.isEmpty() ? null : list;
    }


    @Override
    public void deleteMessage(List<Message> messages) {
        messageRepo.deleteAll(messages);
    }

    @Override
    public void setStatusMessage(String roomId, String username, String status) {
        List<Message> list = messageRepo.findByRoomAndUserAndStatus(roomId, username, status);
        System.out.println("Nhung tin nhan chua doc: " + list.size());
        if(list != null){
            for(Message message : list){
                message.setStatus(String.valueOf(PropertiesConstant.MessageStatus.READ));
            }
            messageRepo.saveAll(list);
        }
    }

    @Override
    public List<Message> findByRoomAndUserAndStatus(String roomId, String username, String status) {
        List<Message> list = messageRepo.findByRoomAndUserAndStatus(roomId, username, status);
        for(Message message : list){
            message.setStatus(String.valueOf(PropertiesConstant.MessageStatus.READ));
        }
        return list.isEmpty() ? null : list;
    }

    @Override
    public Message findMessageLast(String roomId) {
        Pageable pageable = PageRequest.of(0,1);
        List<Message> list = messageRepo.findMessageLast(roomId, pageable);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public int countMessageSend(String roomId, String myUsername, String username) {
        List<Message> list = messageRepo.countMessageSend(roomId, myUsername, username);
        return list.isEmpty() ? 0 : list.size();
    }

    @Override
    public List<MessagePojo> getMessage(MessagePageDto messagePageDto) {
        List<Message> messages = messageRepo.findByRoom(messagePageDto.getRoomId(), PageRequest.of(messagePageDto.getPage(), 10));
        List<MessagePojo> list = new ArrayList<>();
        if (messages != null) {
            for (int i = messages.size() - 1; i > -1; i--) {
                List<String> listFile = new ArrayList<>();
                List<String> contents = new ArrayList<>();

                if(i < messages.size()-1){
                    Message message = messages.get(i + 1);
                    if(message.getUser().getUsername().equals(messages.get(i).getUser().getUsername())){
                        long diff = messages.get(i).getTime().getTime() - message.getTime().getTime();
                        long diffMinutes = diff / (60 * 1000) % 60;
                        long diffHours = diff / (60 * 60 * 1000) % 24;
                        long diffDays = diff / (24 * 60 * 60 * 1000);
                        if(diffDays == 0 && diffHours == 0 && diffMinutes <=5){
                            list.get(list.size()-1).getContent().add(messages.get(i).getContent());
                            list.get(list.size()-1).setTime(messages.get(i).getTimeChat());
                            if(messages.get(i).getAttachList().size() != 0){
                                for(Attach attach:messages.get(i).getAttachList()){
                                    list.get(list.size()-1).getListFile().add(attach.getFilename());
                                }
                            }
                            continue;
                        }
                    }
                }

                contents.add(messages.get(i).getContent());

                if(messages.get(i).getAttachList().size() != 0){
                    listFile = new ArrayList<>();
                    for(Attach attach:messages.get(i).getAttachList()){
                        listFile.add(attach.getFilename());
                    }
                }
                list.add(new MessagePojo(
                        messages.get(i).getId(),
                        messages.get(i).getUser().getUsername(),
                        contents, messages.get(i).getTimeChat(),
                        messages.get(i).getUser().getImage(),
                        listFile));
            }
        }
        System.out.println(list.size());
        return list;
    }

    @Override
    public List<FileAttackDto> saveMessage(String content, String roomId, String attack) throws JsonProcessingException {
        Message message = new Message();
        User user = (User) sessionUtil.getObject("USER");
        Room room = new Room(roomId, "", "", "");
        UUID uuid = UUID.randomUUID();
        message.setId(String.valueOf(uuid));
        message.setUser(user);
        message.setRoom(room);
        message.setType("CHAT");
        message.setTime(new Date());
        message.setContent(content);
        UserConnectPojo userConnectPojo = UsersOnline.userConnectPojo.get(roomId);
        if (userConnectPojo != null) {
            if (userConnectPojo.getUser1() != null && userConnectPojo.getUser2() != null) {
                message.setStatus("READ");
            } else {
                message.setStatus("SEND");
            }
        } else {
            message.setStatus("SEND");
        }
        List<FileAttackDto> dataFile = new ArrayList<>();
        List<Attach> attaches = new ArrayList<>();
        if (!attack.equals("[]")) {
            message.setType("ATTACK");
            ObjectMapper objectMapper = new ObjectMapper();
            List<String> listAttack = new ArrayList<>();
            attack = attack.replace("'", "\"");
            System.out.println(attack.toString());
            System.out.println("size attack " + attack.length());
            listAttack = Arrays.asList(objectMapper.readValue(attack, String[].class));
            for (String file : listAttack) {
                System.out.println(listAttack.size());
                String type = file.substring(file.lastIndexOf("."), file.length());
                String id = String.valueOf(UUID.randomUUID());
                System.out.println(AttackFile.messageAttackHashMap.get(user.getUsername()).getFilesAttack().get("file"));
                String mulFile = AttackFile.messageAttackHashMap.get(user.getUsername()).getFilesAttack().get(file);
                dataFile.add(new FileAttackDto(String.valueOf(uuid), id + type, mulFile));
                attaches.add(new Attach(message, id + type));
                AttackFile.messageAttackHashMap.get(user.getUsername()).getFilesAttack().remove(file);
                System.out.println("file attach: " + attaches.get(0).getFilename());
            }
            System.out.println("Lưu thành công");
        }
        if (dataFile.isEmpty()) {
            dataFile.add(new FileAttackDto(String.valueOf(uuid), null, null));
        }
        messageRepo.save(message);
        if (!attack.equals("[]")) {
            attachRepo.saveAll(attaches);
        }
        return dataFile;
    }

    @Override
    public List<String> getFiles(String roomId) {
        List<String> files = new ArrayList<>();
        User user = (User) sessionUtil.getObject("USER");

        List<RoomDetail> list = roomDetailRepo.findRoomDetailByUserAndRoom(user.getId(), roomId);
        RoomDetail roomDetail = list.isEmpty() ? null : list.get(0);
        if (roomDetail != null) {
            List<Attach> attaches = attachRepo.findByRoom(roomId);
            if (attaches != null) {
                for (Attach attach : attaches) {
                    files.add(attach.getFilename());
                }
            }
        }
        return files;
    }

    @Override
    public MessageUserDto checkMessage(String userId) throws Exception {
        MessageUserDto messageUser = new MessageUserDto();
        User user = (User) sessionUtil.getObject("USER");
        List<User> friends = (List<User>) sessionUtil.getObject("FRIENDS");
        // tạo room
        UUID roomId = UUID.randomUUID();
        UUID messageId = UUID.randomUUID();

        KeyPair keyPair = rsa2048Util.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        String publicKeyString = rsa2048Util.keyToBase64(publicKey);
        String privateKeyString = rsa2048Util.keyToBase64(privateKey);

        Room room = Room.builder()
                .id(String.valueOf(roomId))
                .image("")
                .name("")
                .username(user.getUsername())
                .publicKey(secretKeyUtil.encrypt(publicKeyString))
                .privateKey(secretKeyUtil.encrypt(privateKeyString))
                .build();

        roomRepo.save(room);

        // tạo room detail
        List<User> list = userRepo.findUserById(Integer.parseInt(userId));
        User user2 =  list.isEmpty() ? null : list.get(0);

        List<RoomDetail> roomDetails = new ArrayList<>();
        roomDetails.add(new RoomDetail(user,room));
        roomDetails.add(new RoomDetail(user2,room));
        roomDetailRepo.saveAll(roomDetails);

        // tạo tin nhắn đầu tiên
        Message message = new Message();
        message.setRoom(room);
        message.setType("CREATE");
        message.setTime(new Date());
        message.setContent("Bắt đầu trò chuyện");
        message.setUser(user);
        message.setId(String.valueOf(messageId));
        message.setStatus("CREATE");
        messageRepo.save(message);

        messageUser.setName(user2.getFullname());
        messageUser.setUsername(user2.getUsername());
        messageUser.setMessageLast("Bắt đầu trò chuyện.");
        messageUser.setCountMess(0);
        messageUser.setStatus(1);
        messageUser.setTime(message.getTimeChat());
        messageUser.setRoomCode(room.getId());
        messageUser.setFriend(systemUtil.isFriend(user2, friends));
        messageUser.setImage(user2.getImage());
        return messageUser;    }
}
