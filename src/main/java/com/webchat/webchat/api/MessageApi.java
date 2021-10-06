package com.webchat.webchat.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webchat.webchat.constant.AttackFile;
import com.webchat.webchat.constant.UsersOnline;
import com.webchat.webchat.dto.FileAttackDto;
import com.webchat.webchat.dto.MessagePageDto;
import com.webchat.webchat.entities.*;
import com.webchat.webchat.pojo.MessagePojo;
import com.webchat.webchat.pojo.UserConnectPojo;
import com.webchat.webchat.service.impl.AttachService;
import com.webchat.webchat.service.impl.MessageService;
import com.webchat.webchat.service.impl.RoomDetailService;
import com.webchat.webchat.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class MessageApi {
    @Autowired
    private MessageService messageService;

    @Autowired
    private AttachService attachService;

    @Autowired
    private SessionUtil sessionUtil;

    @Autowired
    private RoomDetailService roomDetailService;

    @GetMapping("/api/message")
    @ResponseBody
    public List<MessagePojo> getMessage(MessagePageDto messagePageDto) {
        List<Message> messages = messageService.findByRoom(messagePageDto.getRoomId(), PageRequest.of(messagePageDto.getPage(), 10));
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

    @PostMapping("/message_direct/save")
    @ResponseBody
    public List<FileAttackDto> saveMessage(
            @RequestParam("content") String content,
            @RequestParam("room") String roomId,
            @RequestParam("attack") String attack) throws IOException {
        Message message = new Message();
        User user = (User) sessionUtil.getObject("USER");
        Room room = new Room(roomId, 0, "","");
        UUID uuid = UUID.randomUUID();
        message.setId(String.valueOf(uuid));
        message.setUser(user);
        message.setRoom(room);
        message.setType("CHAT");
        message.setTime(new Date());
        message.setContent(content);
        UserConnectPojo userConnectPojo = UsersOnline.userConnectPojo.get(roomId);
        if (userConnectPojo.getUser1() != null && userConnectPojo.getUser2() != null) {
            message.setStatus("READ");
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
                dataFile.add(new FileAttackDto(String.valueOf(uuid),id+type,mulFile));
                attaches.add(new Attach(message, id+type));
                AttackFile.messageAttackHashMap.get(user.getUsername()).getFilesAttack().remove(file);
                System.out.println("file attach: " + attaches.get(0).getFilename());
            }
            System.out.println("Lưu thành công");
        }
        if(dataFile.isEmpty()){
            dataFile.add(new FileAttackDto(String.valueOf(uuid),null,null));
        }
        messageService.saveMessage(message);
        if(!attack.equals("[]")){
            attachService.saveAttach(attaches);
        }
        return dataFile;
    }

    @GetMapping("/api/files")
    @ResponseBody
    public List<String> getFiles(
            @RequestParam("room") String roomId
            ){
        List<String> files = new ArrayList<>();
        User user = (User) sessionUtil.getObject("USER");
        RoomDetail roomDetail = roomDetailService.findRoomDetailByUserAndRoom(user.getId(), roomId);
        if(roomDetail != null){
            List<Attach> attaches = attachService.findByRoom(roomDetail.getRoom().getId());
            if(attaches != null){
                for(Attach attach : attaches){
                    files.add(attach.getFilename());
                }
            }
        }
        return files;
    }
}
